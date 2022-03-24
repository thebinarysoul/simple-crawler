package com.thebinarysoul.crawler.service

import com.thebinarysoul.crawler.service.URLContentService.{Content, URL}

import scala.io.Source
import scala.util.{Try, Using}

trait ContentService[F[_], -A, T] {
  def contentBy(value: A): F[T]
}

class URLContentService extends ContentService[Try, URL, Content] {
  override def contentBy(url: URL): Try[Content] = Try {
     Using.resource(Source.fromURL(url))(_.mkString)
  }
}

object URLContentService {
  type URL = String
  type Content = String
}
