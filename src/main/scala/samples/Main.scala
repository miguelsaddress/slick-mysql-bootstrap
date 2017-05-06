package samples

import scala.util.Try
import slick.driver.{H2Driver, MySQLDriver}

import database.profile.DatabaseLayer

object Main {

  val mysqlLayer = new DatabaseLayer(MySQLDriver, "mysql-db-config") 
  val h2Layer = new DatabaseLayer(H2Driver, "h2-db-config") 

  // Let's go! ----------------------------------

  def main(args: Array[String]): Unit = {
    
    println("="*16)
    println("Running in Mysql")
    println("="*16)
    run(mysqlLayer)

    println("="*16)
    println("Running in H2")
    println("="*16)
    run(h2Layer)
  }

  def run(databaseLayer: DatabaseLayer[_]) = {
    import databaseLayer._
    Try(exec(dropUsersTable))
    
    val actions = 
      createUsersTable andThen
      createUser(User("user1", "email1")) andThen
      createUser(User("user2", "email2")) andThen
      createUser(User("user3", "email3")) andThen
      createUser(User("user4", "email4")) andThen
      findUserById(1)

    exec(actions).map(println)
    exec(selectAll).foreach(println)
  }

}
