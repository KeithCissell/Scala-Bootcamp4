// src/main/scala/milestoneproject/LookItUp.scala
package milestoneproject
import httpclient.DuckDuckGo._
import searchengine.SearchEngine._

object LookItUp {
  
  class LookItUp(userGroup: UserGroup = new UserGroup(Seq.empty))
        extends SearchEngine("Look It Up", userGroup) with DuckDuckGoAPI {
    // Handle User Search Request
    def userSearch(user: User, query: String): Unit = {
      if (!userGroup.contains(user)) error(s"User: '${user.name}' is not a registered user")
      else {
        val searchResult = searchDDG(query)
        user.searchHistory.create(searchResult)
        userGroup.update(user)
      }
    }
  }

}
