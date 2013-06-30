package scratch.webapp.spray

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import scratch.webapp.spray.controller.ScratchController
import scala.slick.driver.HsqldbDriver.simple._
import scratch.webapp.spray.data.User

object Boot extends App {

  Database.forURL("jdbc:hsqldb:mem:user", user ="sa", password="", driver = "org.hsqldb.jdbcDriver")
  .withSession { session:Session =>

    User.ddl.create(session)
  }

  // Start up the ActorSystem that the webapp will sun on.
  implicit val system = ActorSystem("on-spray-can")

  // Create and start the controller.
  val service = system.actorOf(Props[ScratchController], "scratch-controller")

  // Start the webapp on port 8080 with ScratchController as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}