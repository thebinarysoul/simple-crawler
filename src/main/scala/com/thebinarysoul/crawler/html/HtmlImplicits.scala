package com.thebinarysoul.crawler.html

object HtmlImplicits {
  implicit class RawHtmlString(value: String) {
    def extractBy(tag: Tag): Option[String] = {
      if (value.contains(tag.open) && value.contains(tag.closed)) {
        val openIndex = value.indexOf(tag.open) + tag.open.length
        val closedIndex = value.indexOf(tag.closed)
        Some(value.substring(openIndex, closedIndex))
      } else None
    }
  }
}
