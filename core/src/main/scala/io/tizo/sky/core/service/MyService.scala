package io.tizo.sky.core.service

import io.tizo.sky.core.handler.MySubProtocolImpl
import io.tizo.sky.protocol.MyProtocol
import io.tizo.sky.routing.MyRoute
import io.tizo.sky.routing.MyRoute.ServiceError
import spray.routing.HttpService

//This needs to be substituted with the customed one, still thinking of how to pass the parameter in.
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by tiiizhou on 12/25/14.
 */
trait MyService extends HttpService {

  lazy val route = MyRoute(new MyProtocol[ServiceError] {
    override lazy val mySubProtocol = new MySubProtocolImpl()
  }).v1Route

}
