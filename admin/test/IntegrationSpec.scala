package test

import org.specs2.mutable.*

import com.lucidchart.piezo.admin.PiezoAdminApplicationLoader

import play.api.test.*
//import play.api.test.Helpers.*

/**
 * add your integration spec here. An integration test will fire up a whole play application in a real (or headless)
 * browser
 */
class IntegrationSpec extends Specification {

  "Application" should {

    "work from within a browser" in new WithApplicationLoader(new PiezoAdminApplicationLoader) {
      override def running(): Unit = {
        // Getting selenium exception
        // https://groups.google.com/forum/#!msg/play-framework/ueXtbcG1oIo/Gc9yKQ4gd10J
        // browser.goTo("http://localhost:3333/")
        // browser.pageSource must contain("was created by")

        success
      }
    }

  }

}
