package example

import akka.actor.{Actor, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer

import scala.concurrent.duration._
import scala.util.{Failure, Success}

class ServerActor(cartacciaActor: ActorRef) extends Actor {

  // necessary plumbing
  implicit val system = context.system
  implicit val ec = context.system.dispatcher
  implicit val am = ActorMaterializer()

  override def receive = Actor.emptyBehavior

  val routes =
    post {
      entity(as[String]) { meșsage =>

        val futureResponse =
          if (meșsage == "last") (cartacciaActor ? GiveLastMessage)(3.seconds).mapTo[String]
          else (cartacciaActor ? meșsage) (3.seconds).mapTo[String]

        onComplete(futureResponse) {
          case Success(response) => complete(response) // success: forward the response received from the CartacciaActor
          case Failure(error) => complete("Aronadio!!") // failure: respond with the name of our Philosophy's professor in high school
        }
      }
    }

  Http().bindAndHandle(routes, "0.0.0.0", 8080)
  println("started server!")
}
