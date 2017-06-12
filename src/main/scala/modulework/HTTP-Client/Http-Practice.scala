// src/main/scala/modulework/HTTP-Client/Http-Practice.scala
package httpclient
import org.asynchttpclient._
import java.util.concurrent.Future

case class HttpResponse(header: String, body: String, statusCode: Int)

trait HttpClient {
  def executeHttpPost(url: String, body: Map[String,String]): HttpResponse
  def executeHttpGet(url: String): HttpResponse
}

class API extends HttpClient {

  def executeHttpPost(url: String, body: Map[String,String]): HttpResponse = {
    val asyncHttpClient = new DefaultAsyncHttpClient()
    val f = asyncHttpClient.preparePost(s"$url").setBody(body.toString).execute()
    val r = f.get()
    asyncHttpClient.close()
    HttpResponse(r.getContentType(), r.getResponseBody(), r.getStatusCode())
  }

  def executeHttpGet(url: String): HttpResponse = {
    val asyncHttpClient = new DefaultAsyncHttpClient()
    val f = asyncHttpClient.prepareGet(s"$url").execute()
    val r = f.get()
    asyncHttpClient.close()
    HttpResponse(r.getContentType(), r.getResponseBody(), r.getStatusCode())
  }
}

object HttpPractice {
  def main(args: Array[String]) {
    val testAPI = new API
    val getResponse = testAPI.executeHttpGet("https://www.google.com/")
    val postResponse = testAPI.executeHttpPost("https://www.google.com/", Map("message" -> "hello world"))
    println(getResponse.statusCode)
    println(postResponse.statusCode)
  }
}
