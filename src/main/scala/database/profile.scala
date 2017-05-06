package database.profile 

// Scala Futures
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

// Slick
import slick.driver.JdbcProfile

import model.users.UsersModule

trait DatabaseProfile {
  val profile: JdbcProfile
  import profile.api._
}

class DatabaseLayer[A <: JdbcProfile](val profile: JdbcProfile, val config: String) 
extends DatabaseProfile with UsersModule {
    import profile.api._

    val db = Database.forConfig(config)

    def exec[T](action: DBIO[T]): T = {
        Await.result(db.run(action), 2 seconds)
    }
}