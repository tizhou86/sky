package io.tizo.sky.server

import akka.actor.{Props, ActorRef, ActorSystem}
import akka.io.IO
import io.tizo.sky.core.actor.MyServiceActor
import spray.can.Http
import akka.pattern.ask

/**
 * Created by tiiizhou on 12/25/14.
 */
class HttpServer(host: String, port: Int) {

  implicit val system = ActorSystem("on-spray-can")

  val myService: ActorRef = system.actorOf(Props(new MyServiceActor()), "myService")

  IO(Http) ! Http.Bind(myService, host, port)

}
