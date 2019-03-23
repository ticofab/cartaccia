package example

import akka.actor.Actor

class CartacciaActor extends Actor {

  var lastMessage = "niente"

  override def receive = {

    case "fabio" =>

    case s: String =>
      lastMessage = s
      println("I received: " + s)
      val response = s + " è na cartaccia!"
      sender ! response

    case GiveLastMessage =>
      sender ! lastMessage

  }
}

case object GiveLastMessage
