package com.iqmsoft.akka.restapi.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import de.heikoseeberger.akkahttpcirce.CirceSupport
import com.iqmsoft.akka.restapi.models.UserEntityUpdate
import com.iqmsoft.akka.restapi.services.{UsersService}
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class UsersServiceRoute(val usersService: UsersService
                       )(implicit executionContext: ExecutionContext) 
                       extends CirceSupport  {

  import StatusCodes._
  import usersService._

  val route = pathPrefix("users") {
    pathEndOrSingleSlash {
      get {
        complete(getUsers().map(_.asJson))
      }
    } ~
    pathPrefix(IntNumber) { id =>
        pathEndOrSingleSlash {
          get {
            complete(getUserById(id).map(_.asJson))
          } ~
            post {
              entity(as[UserEntityUpdate]) { userUpdate =>
                complete(updateUser(id, userUpdate).map(_.asJson))
              }
            } ~
            delete {
              onSuccess(deleteUser(id)) { ignored =>
                complete(NoContent)
              }
            }
        }
      }
  }

}
