package scratch.webapp.spray.data

import scala.slick.driver.HsqldbDriver.simple._
import Database.threadLocalSession

private object UserDataBase {

  val dataBase = Database.forURL("jdbc:hsqldb:mem:user", user = "sa", password = "", driver = "org.hsqldb.jdbcDriver")
}

case class User(id: Option[Long], email: String, firstName: String, lastName: String) {

  import UserDataBase._

  def create: User = {

    dataBase withSession {

      val id = User.forInsert returning User.id insert this

      User(Some(id), this.email, this.firstName, this.lastName)
    }
  }

  def update: User = {

    dataBase withSession {

      this.id.foreach(User.queryById(_).update(this))
    }

    this
  }

  def delete: User = {

    dataBase withSession {

      this.id.foreach(User.queryById(_).delete)
    }

    this
  }
}

object User extends Table[User]("users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def email = column[String]("email")

  def firstName = column[String]("first_name")

  def lastName = column[String]("last_name")

  def * = id.? ~ email ~ firstName ~ lastName <>(User.apply _, User.unapply _)

  def forInsert = email ~ firstName ~ lastName <>( {
    t => User(None, t._1, t._2, t._3)
  }, {
    (u: User) => Some((u.email, u.firstName, u.lastName))
  })

  def queryById(id: Long)(implicit session: Session): Query[User.type, User] = {

    for {user <- User if user.id === id} yield user
  }

  def findById(userId: Long): Option[User] = {

    UserDataBase.dataBase withSession {

      val query = for {
        u <- User if u.id === userId
      } yield u

      query.firstOption
    }
  }

  def findAll: List[User] = {

    UserDataBase.dataBase withSession {

      Query(User).list
    }
  }
}

