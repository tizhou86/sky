package io.tizo.sky.core.handler

import io.tizo.sky.protocol.MySubProtocol
import io.tizo.sky.routing.MyRoute.ServiceError

import scala.concurrent._

/**
 * Created by tiiizhou on 12/25/14.
 */
class MySubProtocolImpl()(implicit val ec: ExecutionContext)
  extends MySubProtocol[ServiceError] {

  //def a mock function here
  //this is temporary
  def mock(id: String) = {
    true
  }

  override def post(id: String, data: String): Future[Either[ServiceError, String]] = future {
    try {
      mock(id) match {
        case true => Right(s"$id")
        case false => Left(400, s"Failed to post with id ${id}")
      }
    } catch {
      case e: Exception => {
        Left(500, "")
      }
    }

  }

  override def get(id: String): Future[Either[ServiceError, String]] = future {
    try {
      mock(id) match {
        case true => Right(s"$id")
        case false => Left(400, s"Failed to post with id ${id}")
      }
    } catch {
      case e: Exception => {
        Left(500, "")
      }
    }

  }

}
