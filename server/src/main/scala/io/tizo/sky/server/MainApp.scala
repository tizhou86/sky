package io.tizo.sky.server

import io.tizo.sky.server.HttpServer

/**
 * Created by tiiizhou on 12/25/14.
 */

object MainApp extends App {

  val httpServer = new HttpServer("0.0.0.0", 48080)

}
