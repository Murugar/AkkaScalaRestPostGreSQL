package me.archdev.restapi.http

import akka.http.scaladsl.server.Directives._
import me.archdev.restapi.http.routes.{UsersServiceRoute}
import me.archdev.restapi.services.{UsersService}
import me.archdev.restapi.utils.CorsSupport

import scala.concurrent.ExecutionContext

class HttpService(usersService: UsersService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val usersRouter = new UsersServiceRoute(usersService)
 

  val routes =
    pathPrefix("v1") {
      corsHandler {
        usersRouter.route 
      }
    }

}
