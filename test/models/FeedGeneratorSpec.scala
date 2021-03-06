package models

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import models.dao._
import org.specs2.specification.Scope

class FeedGeneratorSpec extends Specification with Mockito {
  "the feed generator" should {
    "generate a feed with the right amount of entries" in new FeedScope {
      val feed = feedGen.allItemsFeed("http://localhost:9000")
      (feed \\ "entry").size must_== 2
      (feed \\ "entry" \ "title")(0).text must_== "Zelda 64"
      (feed \\ "entry" \ "id")(1).text must_== "http://localhost:9000/items/2"
    }
  }

  trait FeedScope extends Scope with Mockito {
    val cat = Category(1, "Games", "games")

    val itemDAO = mock[ItemDAO]
    itemDAO.all(false) returns List(
      Item(1, "Zelda 64", "The Legend of Zelda 64", 0, None, cat),
      Item(2, "Mario 64", "Super Mario 64", 0, None, cat)
    )

    val feedGen = new FeedGenerator(itemDAO)
  }
}
