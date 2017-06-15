// src/main/scala/modulework/Http-Client/DuckDuckGo.scala
package httpclient
import httpclient.HttpClient._
import searchengine.SearchEngine._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.util.matching.Regex

object DuckDuckGo {

  trait DuckDuckGoAPI extends API {
    // Retrieve a page

    // Make a search
    def searchDDG(query: String): Search = {
      val requestBody = Map("q" -> s"$query", "format" -> "json")
      val response = executeHttpPost("https://duckduckgo.com", requestBody)
      val results = extractResults(response.body)
      Search(query, results)
    }

    // Search Result Formatting
    def extractResults(json: String): Seq[Result] = {
      val obj = parse(json)
      for {
        JObject(field) <- obj
        JField("Result", JString(result)) <- field
      } yield formatResult(result)
    }
    def formatResult(result: String): Result = {
      val split = result.split(">")
      val title = split(1).dropRight(3)
      val descirption = split(2)
      Result(title, descirption)
    }
  }

}
