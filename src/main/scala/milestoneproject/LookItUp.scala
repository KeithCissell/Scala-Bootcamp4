// src/main/scala/milestoneproject/LookItUp.scala
package milestoneproject
import httpclient.DuckDuckGo._
import searchengine.SearchEngine._

object LookItUp {

  class LookItUp(userGroup: UserGroup = new UserGroup(Seq.empty))
        extends SearchEngine("Look It Up", userGroup) with DuckDuckGoAPI {
    // Handle User Search Request
    def userSearch(userName: String, query: String): Unit = {
      if (!userGroup.contains(userName)) {
        error(s"User '${userName}' is not a registered user")
      } else {
        val user = userGroup.get(userName).getOrElse(error(s"User not found: '${userName}'"))
        val searchResult = searchDDG(query)
        user.searchHistory.create(searchResult)
        userGroup.update(user)
      }
    }
  }

}
