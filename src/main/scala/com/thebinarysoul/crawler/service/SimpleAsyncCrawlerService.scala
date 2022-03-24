package com.thebinarysoul.crawler.service

import com.thebinarysoul.crawler.html.Tag
import com.typesafe.scalalogging.LazyLogging

import scala.collection.immutable.ListMap
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Try}
import scala.util.chaining.scalaUtilChainingOps
import com.thebinarysoul.crawler.html.HtmlImplicits._

class SimpleAsyncCrawlerService(implicit ec: ExecutionContext, contentService: ContentService[Try, String, String]) extends CrawlerService[Future] with LazyLogging {
  private def getContent(url: String): Future[Option[String]] = Future {
    contentService
      .contentBy(url)
      .recoverWith {
        case ex: Throwable =>
          logger.warn(s"Can't get content by $url 'cause:", ex)
          Failure(ex)
      }
      .toOption
  }

  private def getTitle(maybeContent: Option[String]): Option[String] = maybeContent.flatMap(_.extractBy(Tag.Title))

  override def getTitles(urls: List[String]): Future[Map[String, Option[String]]] = urls
    .map(getContent)
    .pipe(Future.sequence(_))
    .map { found => ListMap.from(urls zip found.map(getTitle)) }
}
