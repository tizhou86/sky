package io.tizo.sky.core.actor

import akka.actor.{Actor, Props}
import io.tizo.sky.core.service.MyService

/**
 * Created by tiiizhou on 12/25/14.
 */
class MyServiceActor extends Actor with MyService {

  def actorRefFactory = context

  def receive = runRoute(route)
}
