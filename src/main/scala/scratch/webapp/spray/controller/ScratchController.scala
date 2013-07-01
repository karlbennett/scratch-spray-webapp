package scratch.webapp.spray.controller

import spray.routing.HttpService
import akka.actor.Actor
import spray.http.MediaTypes.`application/json`
import spray.http.MediaTypes.`text/plain`
import spray.json.DefaultJsonProtocol
import spray.httpx.unmarshalling._
import spray.httpx.unmarshalling.pimpHttpEntity


class ScratchController extends Actor with ScratchService {

  def receive = runRoute(scratchRoot)

  def actorRefFactory = context
}

trait ScratchService extends HttpService {

  import scratch.webapp.spray.data.User

  object UserJsonProtocol extends DefaultJsonProtocol {
    implicit val UserFormat = jsonFormat4(User.apply)
  }

  import UserJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  // Define the roots for the "/scratch-spray-webapp/scratch" URL.
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
      post { // Create a user.
        entity(as[User]) { user =>
          respondWithMediaType(`application/json`) {
            complete {
              user.create
            }
          }
        }
      } ~
      get { // Retrieve all users
        respondWithMediaType(`application/json`) {
          complete {
            User.findAll
          }
        }
      }
    } ~
    path("users" / LongNumber) { id =>
      get { // Retrieve a user by it's id.
        respondWithMediaType(`application/json`) {
          complete {
            User.findById(id)
          }
        }
      } ~
      put { // Update a user.
        entity(as[User]) { user =>
          respondWithMediaType(`application/json`) {
            complete {
              User(Some(id), user.email, user.firstName, user.lastName).update
            }
          }
        }
      } ~
      delete { // Delete a user.
        respondWithMediaType(`application/json`) {
          complete {
            User.findById(id).map(_.delete).get
          }
        }
      }
    }
  }
}
