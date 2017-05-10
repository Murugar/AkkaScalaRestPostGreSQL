package me.archdev

import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.CirceSupport
import me.archdev.restapi.http.HttpService
import me.archdev.restapi.models.UserEntity
import me.archdev.restapi.services.{UsersService}
import me.archdev.restapi.utils.DatabaseService
import me.archdev.utils.InMemoryPostgresStorage._
import org.scalatest._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.Random

trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with CirceSupport {

  dbProcess.getProcessId

  private val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)

  val usersService = new UsersService(databaseService)
  val httpService = new HttpService(usersService)

  def provisionUsersList(size: Int): Seq[UserEntity] = {
    val savedUsers = (1 to size).map { _ =>
      UserEntity(Some(Random.nextLong()), Random.nextString(10), Random.nextString(10))
    }.map(usersService.createUser)

    Await.result(Future.sequence(savedUsers), 10.seconds)
  }



}
