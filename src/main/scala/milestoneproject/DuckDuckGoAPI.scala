// src/main/scala/modulework/Http-Client/DuckDuckGo.scala
package httpclient
import httpclient.HttpClient._
import searchengine.SearchEngine._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.util.matching.Regex

object DuckDuckGo {

  trait DuckDuckGoAPI extends API {
    // Make a search through DuckDuckGo's Instant Response API
    def searchDDG(query: String): Search = {
      val requestBody = Map("q" -> s"$query", "format" -> "json")
      val response = executeHttpPost("https://duckduckgo.com", requestBody)
      val results = extractResults(response.body)
      Search(query, results)
    }

    // Extract results from the returned Json data
    def extractResults(json: String): Seq[Result] = {
      val obj = parse(json)
      for {
        JObject(field) <- obj
        JField("Result", JString(result)) <- field
      } yield formatResult(result)
    }

    // Format result string into proper Result instance
    def formatResult(result: String): Result = {
      val split = result.split(">")
      val title = split(1).dropRight(3)
      val descirption = split(2)
      Result(title, descirption)
    }
  }

}
