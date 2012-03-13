package controllers

import play.api._
import play.api.mvc._
import navigation._
import play.api.templates.Xml
import play.api.templates.Html

object Application extends Controller {

  def index() = Action {
    Ok(views.html.index())
  }

  def menu = Action{
    Ok(Html(Navigation.menu.map(_.xhtml("cv")).getOrElse(<p>Fail!</p>).toString))
  }

}
