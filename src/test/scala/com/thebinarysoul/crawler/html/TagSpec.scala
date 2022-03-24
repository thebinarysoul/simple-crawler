package com.thebinarysoul.crawler.html

import com.thebinarysoul.crawler.html.Tag
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TagSpec extends AnyFlatSpec with Matchers {
  "Tag.open" should "return open tag string" in {
    Tag.Title.open shouldBe "<title>"
  }

  "Tag.closed" should "return closed tag string" in {
    Tag.Title.closed shouldBe "</title>"
  }
}
