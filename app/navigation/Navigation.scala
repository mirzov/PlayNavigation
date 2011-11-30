package navigation

import play.api._
import play.api.mvc._
import play.api.data._
import Play._
import scala.xml.XML
import scala.xml.Node
import scala.xml.Text

class MenuItem(val path: String, val caption: String, val children: Seq[MenuItem]){
	
	def xhtml(pagePath: String): Node = {
	  
		val childrenXhtml: Node = 
			if(children.isEmpty) Text("")
			else <ul>{children.map(_.xhtml(pagePath))}</ul>

		if(path == pagePath) 
			<li>{caption}{childrenXhtml}</li>
		else
			<li><a href={"/" + path}>{caption}</a>{childrenXhtml}</li>
	}
  
	override def toString = {
	   "Path: %s\nCaption: %s".format(path, caption) +
	   (if(children.isEmpty) "" else "\n" +  children.mkString("\n"))
	}
}

class Navigation(val menuNode: Node){
  
	val menuItems: Seq[MenuItem] = menuNode \ "menuitem" flatMap getMenuItem
	
	private def getMenuItem(itemXml: Node): Option[MenuItem] = {
	  
		val pathOption = (itemXml \ "path").headOption.map{_.text.trim}
		val captionOption = (itemXml \ "caption").headOption.map{_.text.trim}
		
		val subMenus = (itemXml \ "menuitem").flatMap(getMenuItem)
		
		for(
		    path <- pathOption;
			caption <- captionOption
		) yield new MenuItem(path, caption, subMenus)
	}
	
	def xhtml(pagePath: String): Node = <div class="sitemenu"><ul>{
		menuItems.map(_.xhtml(pagePath))
	}</ul></div>
	
}

object Navigation{
  
	lazy val menu: Option[Navigation] = 
		Play.getExistingFile("/conf/navigation.xml")
			.map(XML.loadFile(_))
			.map(new Navigation(_))
	
	def xhtml(pagePath: String = "cv"): Node = menu.map(_.xhtml(pagePath)).getOrElse(<p>Fail!</p>)
}