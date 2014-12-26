package io.tizo.sky.routing

import io.tizo.sky.protocol.MyProtocol
import spray.http.StatusCode
import spray.httpx.marshalling.Marshaller
import spray.routing.Directives._
import spray.routing.PathMatchers.Segment

import scala.concurrent.{ExecutionContext, Future}

/**
 * Created by tiiizhou on 12/25/14.
 */
object MyRoute {

  type ServiceError = (StatusCode, String)

  type Response[T] = Future[Either[ServiceError, T]]

  def apply(protocolHandler: MyProtocol[ServiceError]) = new MyRoute {
    override val service = protocolHandler
  }

}

trait MyRoute {
  import MyRoute._

  private val extractUri = extract(_.request.uri)

  protected lazy val easPostRedirectGet = true

  def service: MyProtocol[ServiceError]

  protected def post_[T](id: String, data: String, acc: (String, String) ⇒ Response[T])(implicit m: Marshaller[T], ec: ExecutionContext) = {
    onSuccess(acc(id, data)) {
      case Right(item) ⇒ complete(item)
      case Left((error, explain)) ⇒ complete(error, explain)
    }
  }

  protected def get_[T](id: String, acc: String ⇒ Response[T])(implicit m: Marshaller[T], ec: ExecutionContext) = {
    onSuccess(acc(id)) {
      case Right(item) ⇒ complete(item)
      case Left((error, explain)) ⇒ complete(error, explain)
    }
  }

  protected def mySubRoute(implicit ec: ExecutionContext) = pathPrefix("sub") {
    pathPrefix(Segment) { id =>
      pathEndOrSingleSlash {
        post {
          entity(as[String]) {
            data => {
              post_[String](id, data, service.mySubProtocol.post)
            }
          }
        } ~ get {
          get_[String](id, service.mySubProtocol.get)
        }
      }
    }
  }


  private def unmatched = unmatchedPath { remaining ⇒
    reject()
  }


  def v1Route(implicit ec: ExecutionContext) =
    pathPrefix("v1") { mySubRoute }

}
