package com.test.comet

import net.liftweb.http.CometActor
import net.liftweb._
import common.Full
import http.js.JE.Str
import http.js.JsCmds.SetHtml
import net.liftweb.util._
import net.liftweb.util.Helpers._
import net.liftweb.actor._
import org.joda.time.DateTime
import xml.Text

class RemoteTime extends CometActor {

  def render = "#time *" #> "Waiting for Updates"

  Schedule(() => this ! Tic, 1.seconds)

  override def lowPriority: PartialFunction[Any, Unit] = {
    case Tic => {
      //println("### Log Time Tick at: " + timeNow)
      partialUpdate(SetHtml("time", Text(timeNow.toString)))
      Schedule(() => this ! Tic, 1.seconds)
    } //case Tic
  } //lowPriority

} //RemoteTime


case object Tic