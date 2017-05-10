package com.iqmsoft.akka.restapi.http

import akka.http.scaladsl.server.Directives._
import com.iqmsoft.akka.restapi.http.routes.{UsersServiceRoute}
import com.iqmsoft.akka.restapi.services.{UsersService}
import com.iqmsoft.akka.restapi.utils.CorsSupport

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
