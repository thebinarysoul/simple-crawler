package com.thebinarysoul.crawler.rest

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives.{complete, entity, onComplete, path, post}
import com.thebinarysoul.crawler.config.Config
import com.thebinarysoul.crawler.service.CrawlerService
import io.circe.Encoder
import io.circe.jawn.decode

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class AkkaCrawlerRest(implicit config: Config, crawlerService: CrawlerService[Future], ec: ExecutionContext) extends AkkaHttpServer(config.server.host, config.server.port, ec) {
  private val encoder = Encoder.encodeMap[String, Option[String]]

  override val routes = List(
    path("titles") {
      post {
        entity(Directives.as[String]) { request =>
          decode[List[String]](request) match {
            case Left(error) => complete(StatusCodes.BadRequest, error.toString)
            case Right(urls) => onComplete(crawlerService.getTitles(urls)) {
              case Failure(error) => complete(StatusCodes.InternalServerError, error.toString)
              case Success(value) => complete(StatusCodes.OK, encoder.apply(value).toString)
            }
          }
        }
      }
    }
  )
}
