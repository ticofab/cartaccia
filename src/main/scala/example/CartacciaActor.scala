package example

import akka.actor.Actor

class CartacciaActor extends Actor {

  // actor's internal state
  var lastMessage = "niente" // this initial value means "nothing" in Italian

  override def receive = {

    case "fabio" =>
      // do nothing so that a TimeoutException will be triggered somewhere else

    case s: String =>
      lastMessage = s // update internal state with the last received message
      println("I received: " + s)
      val response = s + " è na cartaccia!" // dialect from the mid-90s in Rome, Italy
      sender ! response

    case GiveLastMessage =>
      // this is the only way to extract the internal state of the actor: ask for it
      sender ! lastMessage

  }
}

case object GiveLastMessage
