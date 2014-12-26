package io.tizo.sky

/**
 * Created by tiiizhou on 12/25/14.
 */
import scala.concurrent.Future

package protocol {

  case class Filter(name: String, op: String, value: String)
  object Filter {
    def apply(string: String): Option[Filter] = {
      Seq("=", "%contains%").find{ op => string.indexOf(op) match {
        case x if (x <= 0) => false
        case x if (x + op.length >= string.length) => false
        case _ => true
      }
      }.map { op =>
        val p = string.indexOf(op)
        Filter(string.substring(0, p), op, string.substring(p + op.length()))
      }
    }
  }

  object unimplemented {
    def apply() = null
  }

  trait GetAndPost[Type, Error] {
    def get(id: String): Future[Either[Error, Type]] = unimplemented()
    def post(id: String, data: Type): Future[Either[Error, String]] = unimplemented()
  }

  //Sub Protocol
  trait MySubProtocol[Error] extends GetAndPost[String, Error]


  trait MyProtocol[Error] {
    def mySubProtocol: MySubProtocol[Error] = unimplemented()
  }

}
