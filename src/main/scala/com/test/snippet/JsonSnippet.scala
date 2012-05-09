package com.test.snippet

import net.liftweb.http.SHtml
import net.liftweb.http.js.JE
import scala.xml.NodeSeq
import net.liftweb.common.Box
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds.Script
import net.liftweb.json._
import net.liftweb.common._

case class Thing(width: Int, height: Int)

class JsonSnippet {
 import net.liftweb.json.Serialization.{read, write}
 implicit val formats = Serialization.formats(NoTypeHints)
  
    val handleResize = SHtml.jsonCall(
    	JE.JsObj("width" -> JE.JsVar("Param1"),
    			 "height" -> JE.JsVar("Param2")),
      (obj: JValue) => {
        val result = for {
          m <- obj.extractOpt[Thing]
        } yield {
          // do stuff with the width and height
          println("Width" + m.width)
          println("Height" + m.height)
          // some JS command to send back to the browser
          JsCmds.Noop
        }

        result.getOrElse(/* JS command to send back to browser in case of errors */ JsCmds.Noop)
      })._2

	def render = {
      	Script(JsCmds.Function("toScala", List("Param1", "Param2"), handleResize.cmd))
	}
}