package scratch.webapp.spray.controller

import spray.routing.HttpService
import akka.actor.Actor
import spray.http.MediaTypes.`application/json`
import spray.http.MediaTypes.`text/plain`
import spray.json.DefaultJsonProtocol
import scratch.webapp.spray.data.User
import spray.httpx.unmarshalling._
import spray.httpx.unmarshalling.pimpHttpEntity


/**
 * A simple controller with a request mapping to the the path "/scratch.
 *
 * @author Karl Bennett
 */
class ScratchController extends Actor with ScratchService {

  // Register the scratchRoot as the receiver for this Actor.
  def receive = runRoute(scratchRoot)

  // Register the context for this Actor.
  def actorRefFactory = context
}

/**
 * This is the service trait that will supply the routes for the ScratchController.
 */
trait ScratchService extends HttpService {

  object UserJsonProtocol extends DefaultJsonProtocol {
    implicit val UserFormat = jsonFormat4(User)
  }

  import UserJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  // Define the roots for the "/scratch" URL.
  def scratchRoot = pathPrefix("scratch-spray-webapp" / "scratch") {
    path("") {
      get {
        respondWithMediaType(`text/plain`) {
          complete {
            "scratched"
          }
        }
      }
    } ~
    path("users") {
      post {
        entity(as[User]) { user =>
          respondWithMediaType(`application/json`) {
            complete {
              user
            }
          }
        }
      } ~
      get {
        respondWithMediaType(`application/json`) {
          complete {
            List(User(Some(1), "karl@benne.tt", "Karl", "Bennett"), User(Some(1), "isaac@benne.tt", "Isaac", "Bennett"), User(Some(1), "kirstey@benne.tt", "Kirstey", "Bennet"))
          }
        }
      }
    } ~
    path("users" / LongNumber) { id =>
      get {
        respondWithMediaType(`application/json`) {
          complete {
            User(Some(id), "karl@benne.tt", "Karl", "Bennett")
          }
        }
      } ~
      put {
        entity(as[User]) { user =>
          respondWithMediaType(`application/json`) {
            complete {
              user
            }
          }
        }
      } ~
      delete {
        respondWithMediaType(`application/json`) {
          complete {
            User(Some(id), "karl@benne.tt", "Karl", "Deleted")
          }
        }
      }
    }
  }
}
