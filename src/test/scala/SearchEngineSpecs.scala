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
  val testSearchUpdate = Search("test", List(
    Result("test2", "this is used to check if update works")))

  // Create Users
  val Keith = new User("Keith", "StrongPassWord", SearchHistory(Seq(weatherSearch, cardinalsSearch, cardinalsSearch)))
  val Patrick = new User("Pat", "123456", SearchHistory(Seq(cardinalsSearch, cardinalsSearch, weatherSearch)))
  val Lewis = new User("LewCustom", "K7L")
  val Connor = new User("Conair", "wordPass")
  val ConnorUpdate = new User("Conair", "newWordPass", SearchHistory(Seq(weatherSearch)))

  // Create UserGroups
  val allUsers = new UserGroup(Seq(Keith, Patrick, Lewis, Connor))
  val emptyGroup = new UserGroup(Seq())

  // Create SearchEngines
  val unpopularSearchEngine = new SearchEngine("Unpopular Engine", new UserGroup(Seq(Lewis, Connor)))
  val popularSearchEngine = new SearchEngine("Popular Engine", allUsers)

  /*******************************************************
  ** Specs2 Tests
  *******************************************************/

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
    step(Keith.searchHistory.update(testSearchUpdate))
    "Update Searches in the history" in {
      (!Keith.searchHistory.contains(testSearch)) && (Keith.searchHistory.contains(testSearchUpdate))
    }
    step(Keith.searchHistory.delete(testSearchUpdate))
    "Delete searches from the history" in {
      !Keith.searchHistory.contains(testSearchUpdate)
    }
  }

  // User Tests
  "User.frequentSearch" should {

    "Properly handle User with no search history" in {
      Lewis.mostFrequentSearch === "No Search History"
    }
    "Find the User's most frequent search" in {
      Keith.mostFrequentSearch === "Cardinals"
    }
  }

  // UserGroup Tests
  "UserGroup is a Repository of Users that" should {

    "Check if empty" in {
      (!allUsers.isEmpty) && (emptyGroup.isEmpty)
    }
    "Check if group contains a User" in {
      allUsers.contains(Keith)
    }
    "Return a Seq of all User elements" in {
      allUsers.getAll == Seq(Keith, Patrick, Lewis, Connor)
    }
    "Get a User by their name" in {
      (allUsers.get("Keith") == Some(Keith)) && (emptyGroup.get("Keith") == None)
    }
    step(emptyGroup.create(Connor))
    "Add a new User to the group" in {
      emptyGroup.getAll == Seq(Connor)
    }
    step(emptyGroup.update(ConnorUpdate))
    "Update User in the group" in {
      !(emptyGroup.getAll == Seq(Connor)) && (emptyGroup.getAll == Seq(ConnorUpdate))
    }
    step(emptyGroup.delete(ConnorUpdate))
    "Delete User from the group" in {
      !emptyGroup.contains(ConnorUpdate)
    }
  }

  // SearchEngine Tests
  "engineFrequentSearch takes a list of Users and looks all their searchHistory fields" should {

    "Properly handle a list that yeilds no search history" in {
      unpopularSearchEngine.mostFrequentSearch === "No Searches Found"
    }
    "Find the most frequent search across all Users" in {
      popularSearchEngine.mostFrequentSearch === "Cardinals"
    }
  }
}
