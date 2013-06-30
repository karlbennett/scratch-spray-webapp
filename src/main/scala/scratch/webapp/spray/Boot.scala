package scratch.webapp.spray

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import scratch.webapp.spray.controller.ScratchController

object Boot extends App {

  // Start up the ActorSystem that the webapp will sun on.
  implicit val system = ActorSystem("on-spray-can")

  // Create and start the controller.
  val service = system.actorOf(Props[ScratchController], "scratch-controller")

  // Start the webapp on port 8080 with ScratchController as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}