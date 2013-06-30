package scratch.webapp.spray.data

import scala.slick.driver.HsqldbDriver._

/**
 * A simple user case class that contains an email, first, and last names.
 *
 * @author Karl Bennett
 */
case class User (id: Option[Long], email: String, firstName: String, lastName: String)

/**
 * The Slick table mapping for the [[scratch.webapp.spray.data.User]] case class.
 */
object User extends Table[User]("users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def * = id.? ~ email ~ firstName ~ lastName <> (User.apply _, User.unapply _)
  def forInsert = email ~ firstName ~ lastName <> ({ t => User(None, t._1, t._2, t._3)}, { (u: User) => Some((u.email, u.firstName, u.lastName))})
}

