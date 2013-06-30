package scratch.webapp.spray.data

/**
 * A simple user case class that contains an email, first, and last names.
 *
 * @author Karl Bennett
 */
case class User (id: Option[Long], email: String, firstName: String, lastName: String)
