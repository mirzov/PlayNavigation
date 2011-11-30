package controllers

import play.api._
import play.api.mvc._
import navigation._
import play.api.templates.Xml

object Application extends Controller {

  def index() = Action {
    Ok(views.html.index())
  }

//  def menu = Action{
//    Ok(Navigation.menu.map(_.xhtml("cv")).getOrElse(<p>Fail!<p/>))
//  }

}
