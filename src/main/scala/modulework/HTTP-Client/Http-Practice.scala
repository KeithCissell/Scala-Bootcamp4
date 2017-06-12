// src/main/scala/modulework/HTTP-Client/Http-Practice.scala
package httpclient
import org.asynchttpclient._

object HttpPractice {

  case class HttpResponse(header: String, body: String, statusCode: Int)

  trait HttpClient {
    def executeHttpPost(url: String, body: Map[String,String]): HttpResponse
    def executeHttpGet(url: String): HttpResponse
  }



  def main(args: Array[String]) {

  }
}
