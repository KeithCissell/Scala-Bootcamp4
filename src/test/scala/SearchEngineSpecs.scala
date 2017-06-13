// src/test/scala/SearchEngineSpecs.scala
package milestoneproject
import milestoneproject.SearchEngine._
import org.specs2.mutable.Specification
import org.specs2.specification._

object SearchEngineSpecs extends Specification {
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

  val testSearch = Search("test", List(
    Result("test1", "this search is added, updated and then removed during testing")))
  val testSearch2 = Search("test", List(
    Result("test2", "this is used to check if update works")))

  // Create some users
  val Keith = new User("Keith", "StrongPassWord", SearchHistory(Seq(weatherSearch, cardinalsSearch, cardinalsSearch)))
  val Patrick = new User("Pat", "123456", SearchHistory(Seq(cardinalsSearch, cardinalsSearch, weatherSearch)))
  val Lewis = new User("LewCustom", "K7L")
  val Connor = new User("Conair", "wordPass")

  // Create a list of all the users
  val emptyUserSearches = List(Lewis, Connor)
  val searchEngineUsers = List(Keith, Patrick, Lewis)

  /*******************************************************
  ** Specs2 Tests
  *******************************************************/
  // Result Tests

  // Search Tests
  "SearchHistory is a Repository of Searchs that" should {

    "Check if empty" in {
      (!Keith.searchHistory.isEmpty) && (Lewis.searchHistory.isEmpty)
    }
    "Check if history contains a Search" in {
      Keith.searchHistory.contains(cardinalsSearch)
    }
    "Return a Seq of all Search elements" in {
      Keith.searchHistory.getAll == Seq(weatherSearch, cardinalsSearch, cardinalsSearch)
    }
    "Get a Search at the indicated index" in {
      (Keith.searchHistory.get(2) == Some(cardinalsSearch)) && (Keith.searchHistory.get(4) == None)
    }
    step(Keith.searchHistory.create(testSearch))
    "Add a new Search to the history" in {
      Keith.searchHistory.getAll == Seq(weatherSearch, cardinalsSearch, cardinalsSearch, testSearch)
    }
    step(Keith.searchHistory.update(testSearch2))
    "Update Searches in the history" in {
      (!Keith.searchHistory.contains(testSearch)) && (Keith.searchHistory.contains(testSearch2))
    }
    step(Keith.searchHistory.delete(testSearch2))
    "Delete searches from the history" in {
      !Keith.searchHistory.contains(testSearch2)
    }
  }

  // User Tests
  "User.frequentSearch" should {
    "Properly handle User with no search history" in {
      Lewis.frequentSearch === "No Search History"
    }
    "Find the User's most frequent search" in {
      Keith.frequentSearch === "Cardinals"
    }
  }

// SearchEngine Tests
  "engineFrequentSearch takes a list of Users and looks all their searchHistory fields" should {

    "Properly handle a list that yeilds no search history" in {
      engineFrequentSearch(emptyUserSearches) === "No Searches Found"
    }

    "Find the most frequent search across all Users" in {
      engineFrequentSearch(searchEngineUsers) === "Cardinals"
    }
  }
}
