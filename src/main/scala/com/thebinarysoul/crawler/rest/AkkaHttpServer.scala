package com.thebinarysoul.crawler.rest

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext

abstract class AkkaHttpServer(val host: String, val port: Int, implicit val ec: ExecutionContext) extends HttpServer[Route] with LazyLogging {
  implicit private val system = ActorSystem(Behaviors.empty, "default")

  override def start(): Unit = {
    logger.info("Starting server...")
    Http()
      .newServerAt(host, port)
      .bind(concat(routes:_*))
      .foreach(_ => logger.info(s"Started server on $host:$port"))
  }

  sys.addShutdownHook(stop())

  override def stop(): Unit = {
    logger.info("Try to stop server...")
    system.terminate()
    logger.info("Server has been stopped")
  }
}
