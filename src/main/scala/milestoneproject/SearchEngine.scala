// src/main/scala/milestoneproject/SearchEngine.scala
package milestoneproject
import httpclient._

object SearchEngine {
  /******************************************************
  **             Search Engine Classes
  ******************************************************/
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
  class SearchEngine(val name: String, var users: UserGroup) {
    def mostFrequentSearch: String = {
      val engineHistory = (for (usr <- users.getAll) yield usr.searchHistory.getAll).flatten
      if (engineHistory.isEmpty) {
        return "No Searches Found"
      } else {
        val frequencies = for (s <- engineHistory) yield {
          s -> engineHistory.count(_ == s)
        }
        val mostFrequent = frequencies.maxBy(_._2)
        return mostFrequent._1.value
      }
    }
  }

  /******************************************************
  **                      MAIN
  ******************************************************/
  def main(args: Array[String]) {
    // Make some searches and fill them with results
    val weatherSearch = Search("Weather", List(
      Result("Springfield's Weather", "Local weather report for your area."),
      Result("National Weather Report", "Your up to date location for weather around the world."))
    )

    val catVideoSearch = Search("Cat Videos", List(
      Result("Cat Fights Off Bear", "This little cat thinks it's king of the jungle!"),
      Result("Nyan Cat Needs to Stop", "\"It was never funny. It's still not funny\" ~everyone"),
      Result("Cat Videos United", "Just a website to host cat vidoes and nothing else. You're welcome."))
    )

    val cardinalsSearch = Search("Cardinals", List(
      Result("Cardinals Nation", "You're one stop for up to date Cardinal's score and news"),
      Result("MLB Network", "Cardinals vs. Orioles: Live Score Updates"))
    )

    val pieSearch = Search("pie", List(
      Result("Apple Pie Recipe", "Famous homemade apple pie recipe"),
      Result("Pie Eating Contest", "United Way is hosting a pie eating contest as a fund raiser"),
      Result("Dave's Deli", "Freshly made pies, muffins, donuts...."))
    )

    val badSearch = Search("asdfffffff", List())

    // Create some users
    val Keith = new User("Keith", "StrongPassWord", SearchHistory(Seq(cardinalsSearch, weatherSearch, cardinalsSearch, cardinalsSearch)))
    val Connor = new User("Connor", "SecretPhrase", SearchHistory(Seq(weatherSearch, catVideoSearch, badSearch, catVideoSearch)))
    val Curly = new User("Curly", "Password123", SearchHistory(Seq()))
    val Moe = new User("Moe", "Wordpass321", SearchHistory(Seq(pieSearch)))
    val Larry = new User("Larry", "NyakNyakNyak", SearchHistory(Seq(pieSearch, weatherSearch)))
    val Tessa = new User("Tessa", "Bookw0rm", SearchHistory(Seq(weatherSearch, pieSearch, weatherSearch)))
    val Patrick = new User("Pat", "123456", SearchHistory(Seq(cardinalsSearch, badSearch, cardinalsSearch, weatherSearch)))
    val Lewis = new User("LewCustom", "K7L", SearchHistory(Seq(weatherSearch, pieSearch, cardinalsSearch)))
    val Tommy = new User("TomCatBolls", "art4life", SearchHistory(Seq(badSearch, weatherSearch, weatherSearch)))
    val Mark = new User("Mark", "riffraff", SearchHistory(Seq(cardinalsSearch, weatherSearch, pieSearch)))

    // Create a SearchEngine
    val lookItUp = new SearchEngine("Look It Up", new UserGroup(
        Seq(Keith, Connor, Curly, Moe, Larry, Tessa, Patrick, Lewis, Tommy, Mark)))

    // Print out info on all the users
    //for (user <- lookItUp.users.getAll) println(user)

    // Find each user's most frequent search
    for (user <- lookItUp.users.getAll) println(s"${user.name}'s most frequent search: ${user.mostFrequentSearch}")

    // Find the most frequent search on the engine
    println(s"The most frequent search on this engine: ${lookItUp.mostFrequentSearch}")
  }
}
