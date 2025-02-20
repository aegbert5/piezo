package com.lucidchart.piezo.admin.controllers

import org.quartz.{JobBuilder, Scheduler, SimpleScheduleBuilder, TriggerBuilder}
import com.lucidchart.piezo.jobs.monitoring.HeartBeat
import java.util.Date

/**
 */
object TestUtil {
  val jobGroup = "testJobGroup"
  val jobName = "testJobName"
  val triggerGroup = "testTriggerGroup"
  val triggerName = "testTriggerName"

  def createJob(scheduler: Scheduler): Date = {
    val jobDetail = JobBuilder
      .newJob(classOf[HeartBeat])
      .withIdentity(jobName, jobGroup)
      .withDescription("test job description")
      .build()
    val trigger = TriggerBuilder.newTrigger
      .withIdentity(triggerName, triggerGroup)
      .withDescription("test trigger description")
      .withSchedule(
        SimpleScheduleBuilder.simpleSchedule
          .withIntervalInSeconds(5)
          .withRepeatCount(1),
      )
      .withDescription("test schedule description")
      .build()
    scheduler.deleteJob(jobDetail.getKey())
    scheduler.scheduleJob(jobDetail, trigger)
  }
}
