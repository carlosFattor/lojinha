package models

import models.dao.ItemDAO
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import scala.xml.NodeSeq

class FeedGenerator(itemDAO: ItemDAO) {

  def allItemsFeed(baseURL: String): NodeSeq = {
    val items = itemDAO.all(false)

    <feed xmlns="http://www.w3.org/2005/Atom">
      <title>Feed da Lojinha</title>
      <link href={baseURL}/>
      <updated>{new DateTime().toString(ISODateTimeFormat.dateTime())}</updated>
      <author>
        <name>jcranky</name>
      </author>
      <id>{baseURL + "/feed"}</id>

      {items.map { item =>
          <entry>
            <title>{item.name}</title>
            <link href={baseURL + "/items/1"}/>
            <id>{baseURL + "/items/1"}</id>
            <updated>2013-01-24T17:13:21Z</updated>
            <summary>{item.description}</summary>
          </entry>
        }}
    </feed>
  }
}