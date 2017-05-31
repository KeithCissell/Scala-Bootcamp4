// src/test/scala/SearchEngineSpecs.scala
package milestoneproject
import org.specs2.mutable.Specification

object ComputersSpecs extends Specification {
  import milestoneproject.SearchEngine._

  /*******************************************************
  ** Create data to test on
  *******************************************************/
  // Make some searches and fill them with results
  val weatherSearch = Search("Weather", List(
    Result("Springfield's Weather", "Local weather report for your area."),
    Result("National Weather Report", "Your up to date location for weather around the world."))
  )

  val cardinalsSearch = Search("Cardinals", List(
    Result("Cardinals Nation", "You're one stop for up to date Cardinal's score and news"),
    Result("MLB Network", "Cardinals vs. Orioles: Live Score Updates"))
  )

  // Create some users
  val Keith = new User("Keith", "StrongPassWord", List(weatherSearch, cardinalsSearch, cardinalsSearch))
  val Patrick = new User("Pat", "123456", List(cardinalsSearch, cardinalsSearch, weatherSearch))
  val Lewis = new User("LewCustom", "K7L")
  val Connor = new User("Conair", "wordPass")

  // Create a list of all the users
  val emptyUserSearches = List(Lewis, Connor)
  val searchEngineUsers = List(Keith, Patrick, Lewis)

  /*******************************************************
  ** Specs2 Tests
  *******************************************************/
  "userFrequentSearch takes a User and looks at their searchHistory field" should {

    "Properly handle User with no search history" in {
      userFrequentSearch(Lewis) === "No Search History"
    }

    "Find the User's most frequent search" in {
      userFrequentSearch(Keith) === "Cardinals"
    }
  }

  "engineFrequentSearch takes a list of Users and looks all their searchHistory fields" should {

    "Properly handle a list that yeilds no search history" in {
      engineFrequentSearch(emptyUserSearches) === "No Searches Found"
    }

    "Find the most frequent search across all Users" in {
      engineFrequentSearch(searchEngineUsers) === "Cardinals"
    }
  }
}
