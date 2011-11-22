package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import views._
import play.api.templates.Xml
import navigation._

object Application extends Controller {

  def index(path: String = "") = Action {
    Ok(html.index())
  }

  def menu = Action{
    Ok(Xml(Navigation.menu.map(_.menuNode).getOrElse(<fail/>).toString))
  }

}
