package com.test.snippet

import net.liftweb.http.SHtml
import net.liftweb.http.js.JE
import scala.xml.NodeSeq
import net.liftweb.common.Box
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds.Script

class JsonSnippet {
    val handleResize = SHtml.jsonCall(
        JE.JsObj("width" -> JE.JsVar("window", "screen", "width"),
    			 "height" -> JE.JsVar("window", "screen", "height")),
      obj => {
        val result = for {
          m <- (Box !! obj).asA[scala.collection.immutable.Map
            [String, Any]]
        } yield {
          // do stuff with the width and height
          println("Width" + m.get("width"))
          println("Height" + m.get("height"))
          // some JS command to send back to the browser
          JsCmds.Noop
        }

        result.openOr(/* JS command to send back to browser in case of errors */ JsCmds.Noop)
      })._2
	
	def render = {
      	Script(JsCmds.Function("handleResize", Nil, handleResize.cmd))
	}
}