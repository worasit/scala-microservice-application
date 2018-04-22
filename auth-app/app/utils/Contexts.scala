package utils

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class Contexts @Inject()(actorSystem: ActorSystem, config: Configuration) {
  implicit val dbLookup = actorSystem.dispatchers.lookup("contexts.db-lookups")
  implicit val cpuLookup = actorSystem.dispatchers.lookup("contexts.cpu-operations")

  val tokenTTL = config.get[Long]("token.ttl")
}
