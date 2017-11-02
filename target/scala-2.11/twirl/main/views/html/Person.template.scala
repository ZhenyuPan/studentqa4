
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object Person_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class Person extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(emailaddress :String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.24*/("""
"""),format.raw/*2.1*/("""<html>
  <head>
      <title>
            <p><em>"""),_display_(/*5.21*/emailaddress),format.raw/*5.33*/("""</em></p>
      </title>
  </head>
  <script>
		function POSTQA()
		"""),format.raw/*10.3*/("""{"""),format.raw/*10.4*/("""
			"""),format.raw/*11.4*/("""var url= "/IssuePassword"
			var password = document.getElementById("password").value;
			var xhr      = new XMLHttpRequest();
			xhr.onreadystatechange = function()
			"""),format.raw/*15.4*/("""{"""),format.raw/*15.5*/("""
				"""),format.raw/*16.5*/("""if (xhr.readyState == 4) """),format.raw/*16.30*/("""{"""),format.raw/*16.31*/("""
					"""),format.raw/*17.6*/("""alter("your account have been active");
			 	"""),format.raw/*18.6*/("""}"""),format.raw/*18.7*/("""
		    """),format.raw/*19.7*/("""}"""),format.raw/*19.8*/("""
			"""),format.raw/*20.4*/("""xhr.open("POST",url,true);
			xhr.setRequestHeader("Content-type",'application/json; charset=UTF-8');
			var params= """),format.raw/*22.16*/("""{"""),format.raw/*22.17*/(""""password":"""""),format.raw/*22.30*/("""}"""),format.raw/*22.31*/(""";
			params.password=password;
			xhr.send(params);
		"""),format.raw/*25.3*/("""}"""),format.raw/*25.4*/("""
  """),format.raw/*26.3*/("""</script>
  <body>
      <p>please enter your password to activate your account:</p>
      <textarea id="password"></textarea>
  </body>
</html>"""))
      }
    }
  }

  def render(emailaddress:String): play.twirl.api.HtmlFormat.Appendable = apply(emailaddress)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (emailaddress) => apply(emailaddress)

  def ref: this.type = this

}


}

/**/
object Person extends Person_Scope0.Person
              /*
                  -- GENERATED --
                  DATE: Sun Sep 24 21:08:00 EDT 2017
                  SOURCE: C:/Users/zheny/Documents/studentqa4/app/views/Person.scala.html
                  HASH: a2e7581a9ff3482f06b647b6aad379c5f3832fa3
                  MATRIX: 747->1|864->23|892->25|971->78|1003->90|1103->163|1131->164|1163->169|1363->342|1391->343|1424->349|1477->374|1506->375|1540->382|1613->428|1641->429|1676->437|1704->438|1736->443|1883->562|1912->563|1953->576|1982->577|2066->634|2094->635|2125->639
                  LINES: 27->1|32->1|33->2|36->5|36->5|41->10|41->10|42->11|46->15|46->15|47->16|47->16|47->16|48->17|49->18|49->18|50->19|50->19|51->20|53->22|53->22|53->22|53->22|56->25|56->25|57->26
                  -- GENERATED --
              */
          