package example

import akka.actor.{ActorSystem, Props}

object Boot extends App {
  val actorSystem = ActorSystem("test")
  val cartacciaActor = actorSystem.actorOf(Props[CartacciaActor])
  val serverActor = actorSystem.actorOf(Props(new ServerActor(cartacciaActor)))
}
