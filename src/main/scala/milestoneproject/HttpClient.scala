// src/main/scala/modulework/HTTP-Client/HttpClient.scala
package httpclient
import org.asynchttpclient._
import java.util.concurrent.Future
import scala.collection.JavaConversions._

object HttpClient{

  case class HttpResponse(header: Map[String,String], body: String, statusCode: Int)

  trait HttpClient {
    def executeHttpPost(url: String, body: Map[String,String]): HttpResponse
    def executeHttpGet(url: String): HttpResponse
  }

  trait API extends HttpClient {

    def executeHttpPost(url: String, body: Map[String,String]): HttpResponse = {
      // Make request
      val asyncHttpClient = new DefaultAsyncHttpClient()
      val req = asyncHttpClient.preparePost(s"$url")
      for ((n,v) <- body) req.addFormParam(n, v)
      val f = req.execute()
      val r = f.get()
      asyncHttpClient.close()

      // Build custom HttpResponse
      if (r.getStatusCode != 200) error(s"Failed to Post to: $url")
      else {
        val hMap: Map[String, String] = if (r.hasResponseHeaders()) {
          val names = r.getHeaders().names().toList
          val values = names.map(n => r.getHeaders().get(n))
          (names zip values).toMap
        } else Map()
        val b = if (r.hasResponseBody()) r.getResponseBody() else ""
        val s = if (r.hasResponseStatus()) r.getStatusCode() else 0
        HttpResponse(hMap, b, s)
      }
    }

    def executeHttpGet(url: String): HttpResponse = {
      // Make request
      val asyncHttpClient = new DefaultAsyncHttpClient()
      val f = asyncHttpClient.prepareGet(s"$url").execute()
      val r = f.get()
      asyncHttpClient.close()

      // Build custom HttpResponse
      if (r.getStatusCode != 200) error(s"Failed to Get: $url")
      else {
        val hMap: Map[String, String] = if (r.hasResponseHeaders()) {
          val names = r.getHeaders().names().toList
          val values = names.map(n => r.getHeaders().get(n))
          (names zip values).toMap
        } else Map()
        val b = if (r.hasResponseBody()) r.getResponseBody() else ""
        val s = if (r.hasResponseStatus()) r.getStatusCode() else 0
        HttpResponse(hMap, b, s)
      }
    }
  }

}
