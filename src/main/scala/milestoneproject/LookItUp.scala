// src/main/scala/milestoneproject/LookItUp.scala
import milestoneproject.SearchEngine._

object LookItUp {
  /******************************************************
  **      'Look It Up' Search Engine
  ******************************************************/

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

  val badSearch = Search("asdfffffff", Nil)

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
  val LookItUp = new SearchEngine("Look It Up", new UserGroup(
      Seq(Keith, Connor, Curly, Moe, Larry, Tessa, Patrick, Lewis, Tommy, Mark)))

  /******************************************************
  **                      MAIN
  ******************************************************/
  def main(args: Array[String]) {
    // Print out info on all the users
    //for (user <- LookItUp.users.getAll) println(user)

    // Find each user's most frequent search
    for (user <- LookItUp.users.getAll) println(s"${user.name}'s most frequent search: ${user.mostFrequentSearch}")

    // Find the most frequent search on the engine
    println(s"The most frequent search on this engine: ${LookItUp.mostFrequentSearch}")
  }
}
