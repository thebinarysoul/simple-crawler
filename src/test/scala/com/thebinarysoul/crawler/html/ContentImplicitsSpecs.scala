package com.thebinarysoul.crawler.html

import com.thebinarysoul.crawler.html.HtmlImplicits._
import com.thebinarysoul.crawler.html.Tag
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class ContentImplicitsSpecs extends AnyFlatSpec with Matchers {
   "extractBy" should "extract nothing from empty string" in {
      "".extractBy(Tag.Title) shouldBe None
   }

   "extractBy" should "extract nothing from corrupted string" in {
      "<title>abc".extractBy(Tag.Title) shouldBe None
   }

   "extractBy" should "extract content" in {
      "<title>abc</title>".extractBy(Tag.Title) shouldBe Some("abc")
   }
}
