package com.thebinarysoul.crawler.service

trait CrawlerService[F[_]] {
    def getTitles(urls: List[String]): F[Map[String, Option[String]]]
}
