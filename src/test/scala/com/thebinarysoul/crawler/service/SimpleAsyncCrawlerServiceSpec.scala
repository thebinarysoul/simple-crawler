package com.thebinarysoul.crawler.service

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.NoSuchElementException
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.chaining.scalaUtilChainingOps
import scala.util.{Failure, Success, Try}

class SimpleAsyncCrawlerServiceSpec extends AnyFlatSpec with Matchers {
  private implicit val ec: ExecutionContext = new ExecutionContext {
    override def execute(runnable: Runnable): Unit = runnable.run()

    override def reportFailure(cause: Throwable): Unit = throw cause
  }

  private val scalaHtml =
    """
      |<html>
      |   <head>
      |       <title>Scala</title>
      |   </head>
      |   <body>
      |       <p>Something great is coming</p>
      |   </body>
      |</html>
      |""".stripMargin

  private val contentMap = Map(
    "http://scala.com" -> scalaHtml,
    "https://title.com" -> "<title>Title</title>",
    "http://abc.abc" -> "abc"
  )

  private implicit val contentService: ContentService[Try, String, String] = (value: String) => {
    contentMap
      .get(value)
      .map(Success.apply)
      .getOrElse(Failure(new NoSuchElementException(s"Can't find $value")))
  }

  val service = new SimpleAsyncCrawlerService()

  "getTitles" should "return empty map" in {
      val result = service
        .getTitles(Nil)
        .pipe(getResult)

      result shouldBe Map.empty[String, Option[String]]
  }

  "getTitles" should "return one pair with result" in {
      val result = service
        .getTitles("http://scala.com" :: Nil)
        .pipe(getResult)

      result shouldBe Map("http://scala.com" -> Some("Scala"))
  }

  "getTitles" should "return one pair without result" in {
    val result = service
      .getTitles("cba" :: Nil)
      .pipe(getResult)

    result shouldBe Map("cba" -> None)
  }

  private def getResult[T](future: Future[T]) = Await.result(future, Duration.Inf)
}
