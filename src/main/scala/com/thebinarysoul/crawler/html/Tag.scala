package com.thebinarysoul.crawler.html

sealed abstract class Tag(val name: String) {
    val open: String = s"<$name>"
    val closed: String = s"</$name>"
}

object Tag {
  case object Title extends Tag("title")
}