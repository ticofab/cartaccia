package example

import akka.actor.{Actor, ActorRef}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer

import scala.concurrent.duration._
import scala.util.{Failure, Success}

class ServerActor(cartacciaActor: ActorRef) extends Actor {

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
          case Success(response) => complete(response)
          case Failure(error) => complete("Aronadio!!")
        }
      }
    }

  Http().bindAndHandle(routes, "0.0.0.0", 8080)
  println("started server!")
}
