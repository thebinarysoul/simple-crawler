package com.thebinarysoul.crawler.config

import pureconfig.ConfigReader.Result
import pureconfig.ConfigSource
import pureconfig._
import pureconfig.generic.auto._

final case class Config(server: ServerConfig)
final case class ServerConfig(host: String, port: Int)

object Config {
  def fromArg(args: Array[String]): Result[Config] = args
    .headOption
    .map(path => ConfigSource.file(path).load[Config])
    .getOrElse(ConfigSource.default.load[Config])
}