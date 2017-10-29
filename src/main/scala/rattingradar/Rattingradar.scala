package rattingradar

import scala.scalajs.js.JSApp
import outwatch.dom._
import fr.hmil.roshttp.HttpRequest
import monix.execution.Scheduler.Implicits.global
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import io.circe.syntax._

import scala.util.{Failure, Success, Try}
import fr.hmil.roshttp.response.SimpleHttpResponse
import rxscalajs.Observable

import scala.io.Source

object Rattingradar extends JSApp {
  case class Kills(npc_kills: Int, pod_kills: Int, ship_kills: Int, system_id: Int) {
    def toDiv = div(
      `class` := "row card-panel teal lighten-2 scale-transition scale-in",
      div(
        `class` := "col s6",
        s"System: $system_id"
      ),
      div(
        `class` := "col s6",
        s"NPC kills: $npc_kills"
      )
    )
  }



  def main(): Unit = {
    val search = createStringHandler()

    

    val systems = Observable.from(HttpRequest("systems.json").send()).map { x => parse(x.body).toOption.map{_.as[(String,String,String,String,String,String,String)].toOption.map(Map.System.fromTuple).get}}
    systems.subscribe(s => println(s))

    val request = HttpRequest("https://esi.tech.ccp.is/v2/universe/system_kills/")
    val req = Observable.from(request.send()).map {x => (x.headers, parse(x.body).toOption.flatMap(_.as[List[Kills]].toOption))}

    val header = nav(
      div(
        `class` := "nav-wrapper teal",
        form(
          div(
            `class` := "input-field",
            input(
              id := "search",
              `type` := "search",
              required,
              placeholder := "System, Constellation, Region...",
              inputString --> search
            ),
            label(
              `class` := "label-icon",
              `for` := "search",
              i(
                `class` := "material-icons",
                "search"
              )
            ),
            i(
              `class` := "material-icons",
              "close"
            )
          )
        )
      )
    )

    val node = div(
      header,
      div(
        `class` := "container",
        h2("Hello ", child <-- search.map(_.reverse)),
        div(
          `class` := "container",
          children <-- req.map(_._2.getOrElse(List.empty).map(_.toDiv))
        )
      )
      //p(placeholder := "content", child <-- body)
    )



    OutWatch.render("#app", node)
  }
}
