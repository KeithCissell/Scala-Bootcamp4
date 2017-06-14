// src/main/scala/modulework/Http-Client/HttpPractice.scala
import httpclient.HttpClient._

object HttpPractice {
  def main(args: Array[String]) {
    val myMap = Map("message" -> "hello", "from" -> "keith", "to" -> "world")
    val testAPI = new API
    val getR = testAPI.executeHttpGet("https://httpbin.org/get")
    val postR = testAPI.executeHttpPost("https://httpbin.org/post", myMap)
    println(getR.header + "\n" + getR.body + "\n" + getR.statusCode)
    println(postR.header + "\n" + postR.body + "\n" + postR.statusCode)
  }
}
