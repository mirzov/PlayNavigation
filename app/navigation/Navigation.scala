package navigation

import play.api._
import play.api.mvc._
import play.api.data._
import Play._
import scala.xml.XML
import scala.xml.Node

class MenuItem(val path: String, val caption: String, val children: Seq[MenuItem]){
	override def toString = {
	   "Path: %s\nCaption: %s".format(path, caption) +
	   (if(children.isEmpty) "" else "\n" +  children.mkString("\n"))
	}
}

class Navigation(val menuNode: Node){
  
	val menuItems: Seq[MenuItem] = menuNode \ "menuitem" flatMap getMenuItem
	
	menuItems foreach println
	
	private def getMenuItem(itemXml: Node): Option[MenuItem] = {
	  
		val pathOption = (itemXml \ "path").headOption.map{_.text.trim}
		val captionOption = (itemXml \ "caption").headOption.map{_.text.trim}
		
		val subMenus = (itemXml \ "menuitem").flatMap(getMenuItem)
		
		for(
		    path <- pathOption;
			caption <- captionOption
		) yield new MenuItem(path, caption, subMenus)
	}
	
}

object Navigation{
  
	lazy val menu: Option[Navigation] = 
		Play.getExistingFile("/conf/navigation.xml")
			.map(XML.loadFile(_))
			.map(new Navigation(_))
			
}