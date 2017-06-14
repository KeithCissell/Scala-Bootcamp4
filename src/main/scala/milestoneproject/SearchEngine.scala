// src/main/scala/milestoneproject/SearchEngine.scala
package milestoneproject
import httpclient.HttpClient._

object SearchEngine {
  // A general list of methods to be used by a repository
  trait Repository[A, I] {
    def isEmpty: Boolean
    def contains(x: A): Boolean
    def getAll: Seq[A]
    def get(id: I): Option[A]
    def create(x: A): Unit
    def update(x: A): Unit
    def delete(x: A): Unit
  }

  // Holds the title and brief description of an search result
  case class Result(title: String, description: String)

  // Holds the term searched and a list of results returned
  case class Search(value: String, results: List[Result] = Nil)

  // A list of searches that can be viewed and manipulated
  case class SearchHistory(private var history: Seq[Search]) extends Repository[Search,Int] {
    def isEmpty: Boolean = history.isEmpty
    def contains(s: Search): Boolean = history.contains(s)
    def getAll: Seq[Search] = history
    def get(id: Int): Option[Search] = if (id < history.length) Some(history(id)) else None
    def create(s: Search): Unit = history = history :+ s
    def update(s: Search): Unit = {
      for (i <- 0 to (history.length - 1)) if (history(i).value == s.value) {
        history = history.updated(i, s)
      }
    }
    def delete(s: Search): Unit = history = history.filter(_ != s)
  }

  // A search engine user that holds their name, password and search history
  class User(val name: String, val password: String,
      var searchHistory: SearchHistory = SearchHistory(Seq.empty)) {
    def mostFrequentSearch: String = {
      if (!searchHistory.isEmpty) {
        val frequencies = for (s <- searchHistory.getAll) yield {
          s -> searchHistory.getAll.count(_ == s)
        }
        frequencies.maxBy(_._2)._1.value
      } else "No Search History"
    }
    override def toString: String = {
      return s"${name}'s Search History\n$searchHistory"
    }
  }

  // A group of search engine users
  class UserGroup(private var users: Map[String,User]) extends Repository[User,String] {
    // Allows UserGroup to be constructed with a Seq of users
    def this(users: Seq[User]) {
      this((users.map(u => u.name) zip users).toMap)
    }
    def isEmpty: Boolean = users.isEmpty
    def contains(u: User): Boolean = users.contains(u.name)
    def getAll: Seq[User] = users.values.toSeq
    def get(id: String): Option[User] = if (users.contains(id)) Some(users(id)) else None
    def create(u: User): Unit = {
      if (users.contains(u.name)) error(s"User already exists: $u.name")
      else users = users + (u.name -> u)
    }
    def update(u: User): Unit = users = users + (u.name -> u)
    def delete(u: User): Unit = if (users.contains(u.name)) users = users - u.name
  }

  // A search engine that holds a UserGroup and allows them to make searches
  class SearchEngine(val name: String, var users: UserGroup) extends API {
    def search: HttpResponse = executeHttpGet("https://httpbin.org/get")
    def engineSearchHistory: Seq[Search] = {
      (for (usr <- users.getAll) yield usr.searchHistory.getAll).flatten
    }
    def mostFrequentSearch: String = {
      val history = engineSearchHistory
      if (!history.isEmpty) {
        val frequencies = for (s <- history) yield {
          s -> history.count(_ == s)
        }
        frequencies.maxBy(_._2)._1.value
      } else "No Searches Found"
    }
  }

}
