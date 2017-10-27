package rattingradar

import scalax.collection.Graph
import scalax.collection.GraphPredef._

object Map {

  case class System(id: Int, name: String, constellation: String, region: String, x: Long, y: Long, z: Long)
  object System {
    def fromTuple(t: (String, String, String, String, String, String, String)): System = {
      System(t._1.toInt, t._2, t._3, t._4, t._5.toLong, t._6.toLong, t._7.toLong)
    }
  }


}
