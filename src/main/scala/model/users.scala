package model.users
import database.profile._

trait UsersModule {  self: DatabaseProfile =>

    import profile.api._
    
    case class User(username: String, email:String, id: Long = 0L)

    // Table
    class UserTable(tag: Tag) extends Table[User](tag, "users") {
      def username  = column[String]("username", O.Length(255, varying=true))
      def email     = column[String]("email", O.Length(255, varying=true))
      def id        = column[Long]("id", O.PrimaryKey, O.AutoInc)

      def uniqueUserName  = index("unique_username" , username  , unique=true)
      def uniqueEmail     = index("unique_email"    , email     , unique=true)

      def * = (username, email, id) <> (User.tupled, User.unapply)
    }

    lazy val UsersTable = TableQuery[UserTable]

    def dropUsersTable = UsersTable.schema.drop
    def createUsersTable = UsersTable.schema.create
    def createUser(user: User) = UsersTable += user
    def selectAll = UsersTable.result
    def findUserById(id: Long) = UsersTable.filter(_.id === id).result
}
