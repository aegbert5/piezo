package com.lucidchart.piezo.jobs.cleanup

import org.quartz.{Job, JobExecutionContext}
import org.slf4j.LoggerFactory
import com.lucidchart.piezo.{JobHistoryModel, TriggerHistoryModel, WorkerSchedulerFactory}
import java.util.Date

object JobHistoryCleanup {
  val schedulerFactory: WorkerSchedulerFactory = new WorkerSchedulerFactory
  val scheduler = schedulerFactory.getScheduler
  val props = schedulerFactory.props

  val triggerHistoryModel = new TriggerHistoryModel(props)
  val jobHistoryModel = new JobHistoryModel(props)
}

class JobHistoryCleanup extends Job {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def execute(context: JobExecutionContext): Unit = {
    val maxAge = context.getMergedJobDataMap.getLong("maxAgeDays") * 24L * 3600L * 1000L
    val minStart = System.currentTimeMillis() - maxAge
    deleteTriggerHistories(minStart)
    deleteJobHistories(minStart)
  }

  private[this] def deleteTriggerHistories(minStart: Long): Unit = {
    logger.info("Deleting triggers older than " + new Date(minStart))
    val numDeleted = JobHistoryCleanup.triggerHistoryModel.deleteTriggers(minStart)
    logger.info("Deleted " + numDeleted + " trigger histories")
  }

  private[this] def deleteJobHistories(minStart: Long): Unit = {
    logger.info("Deleting jobs older than " + new Date(minStart))
    val numDeleted = JobHistoryCleanup.jobHistoryModel.deleteJobs(minStart)
    logger.info("Deleted " + numDeleted + " job histories")
  }
}
