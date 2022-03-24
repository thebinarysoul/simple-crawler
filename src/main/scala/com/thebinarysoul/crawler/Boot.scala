package com.thebinarysoul.crawler

import com.thebinarysoul.crawler.config.Config
import com.thebinarysoul.crawler.rest.AkkaCrawlerRest
import com.thebinarysoul.crawler.service.{SimpleAsyncCrawlerService, URLContentService}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global

object Boot extends App with LazyLogging {
  Config.fromArg(args) match {
    case Left(value) =>
      logger.error(value.prettyPrint())
    case Right(config: Config) =>
      logger.info(s"Staring app with conf: $config")

      implicit val contentService: URLContentService = new URLContentService
      implicit val conf: Config = config
      implicit val service: SimpleAsyncCrawlerService = new SimpleAsyncCrawlerService()

      new AkkaCrawlerRest().start()
  }
}
