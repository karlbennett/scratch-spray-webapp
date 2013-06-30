scratch-spray-webapp
==============

A very simple webapp that can be used to quickly try out code within the Spray.io framework.

The webapp can be run in SBT with the following command:
    $ sbt
    > re-start

This will start the server which can be accessed at [http://localhost:8080/scratch-spray-webapp/scratch/](http://localhost:8080/scratch-spray-webapp/scratch/ "scratch-spray-webapp")

It is also possible to carry out CRUD operation on simple users:

###### Create
    $ curl -XPOST -H "Content-Type:text/html" http://localhost:8080/scratch-spray-webapp/scratch/users -d '{
        "email": "some.one@there.com",
        "firstName": "Some",
        "lastName": "One",
    }'

###### Retrieve
    $ curl -XGET http://localhost:8080/scratch-spray-webapp/scratch/users
    $ curl -XGET http://localhost:8080/scratch-spray-webapp/scratch/users/1

###### Update
    $ curl -XPUT -H "Content-Type:text/html" http://localhost:8080/scratch-spray-webapp/scratch/users/1 -d '{
        "email": "some.one@there.com",
        "firstName": "Some",
        "lastName": "Two",
    }'

###### Delete
    $ curl -XDELETE http://localhost:8080/scratch-spray-webapp/scratch/users/1


The webapp only contains two classes:

The controller class that handles the `/scratch-spray-webapp/scratch/`, `/scratch-spray-webapp/scratch/users`, and
`/scratch-spray-webapp/scratch/users/{id}` request mappings.

[`scratch.webapp.spray.controller.ScratchController`](https://github.com/karlbennett/scratch-spray-webapp/blob/master/src/main/scala/scratch/webapp/spray/controller/ScratchController.scala "ScratchController")

The the domain case class that can be persisted into an in memory database using the CRUD endpoints.

[`scratch.webapp.spray.data.User`](https://github.com/karlbennett/scratch-spray-webapp/blob/master/src/main/scala/scratch/webapp/spray/data/User.scala "User")

There is also a bootstrap file:

The [`Boot.scala`](https://github.com/karlbennett/scratch-spray-webapp/blob/master/src/main/scala/scratch/webapp/spray/Boot.scala "Boot.xml")
file, this contains the server initialisation and handler registering.

And lastly a configuration file:

The [`application.conf`](https://github.com/karlbennett/scratch-spray-webapp/blob/master/src/main/resources/application.conf "application.conf")
file that defines log level and request timeout.

That is the entire project, have fun :)
