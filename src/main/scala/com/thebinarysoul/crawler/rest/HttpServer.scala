package com.thebinarysoul.crawler.rest

trait HttpServer[+R] {
  def host: String
  def port: Int
  def routes: List[R]
  def start(): Unit
  def stop(): Unit
}
