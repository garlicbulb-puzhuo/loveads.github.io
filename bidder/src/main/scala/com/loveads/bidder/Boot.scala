package com.loveads.bidder

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.duration.DurationInt
import scala.util.Success

import org.slf4j.LoggerFactory

import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http

object Boot extends App {
  private val logger = LoggerFactory.getLogger(getClass)

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[HealthServiceActor], "health-service")

  implicit val timeout = Timeout(5.seconds)

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080) onComplete {
    case Success(Http.Bound(endpoint)) =>
      logger.info(s"Started REST API: uri=$endpoint")
    case _ =>
      logger.info(s"Failed to start REST API")
  }

  def stop() {
    Await.result(IO(Http) ? Http.CloseAll, Duration(15, "seconds"))
    logger.info(s"Stopped REST API")
    system.stop(IO(Http))
    system.stop(service)
  }

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() = {
      Boot.stop()
    }
  })
}
