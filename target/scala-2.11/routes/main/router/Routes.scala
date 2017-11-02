
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/zheny/Documents/studentqa4/conf/routes
// @DATE:Wed Oct 25 23:58:50 EDT 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_0: controllers.HomeController,
  // @LINE:8
  UserAPI_1: WebAPI.UserAPI,
  // @LINE:15
  StudentAPI_5: WebAPI.StudentAPI,
  // @LINE:23
  GraderAPI_2: WebAPI.GraderAPI,
  // @LINE:27
  Assets_3: controllers.Assets,
  // @LINE:29
  BoostInterface_4: WebAPI.BoostInterface,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:8
    UserAPI_1: WebAPI.UserAPI,
    // @LINE:15
    StudentAPI_5: WebAPI.StudentAPI,
    // @LINE:23
    GraderAPI_2: WebAPI.GraderAPI,
    // @LINE:27
    Assets_3: controllers.Assets,
    // @LINE:29
    BoostInterface_4: WebAPI.BoostInterface
  ) = this(errorHandler, HomeController_0, UserAPI_1, StudentAPI_5, GraderAPI_2, Assets_3, BoostInterface_4, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, UserAPI_1, StudentAPI_5, GraderAPI_2, Assets_3, BoostInterface_4, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Login/""", """WebAPI.UserAPI.VerifyUser()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """SignUP/""", """WebAPI.UserAPI.StudentSignUP()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Active/""" + "$" + """MAC<[^/]+>""", """WebAPI.UserAPI.ActivateAccount(MAC:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Personal_Inform/""", """WebAPI.UserAPI.IssuePassword()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Personal_Inform/""", """WebAPI.UserAPI.PersonalInform()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ForgetPassowrd/""", """WebAPI.UserAPI.ForgetPassword()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ClassList/""", """WebAPI.StudentAPI.GetClassList()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """TaskList/""", """WebAPI.StudentAPI.GetTaskList()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """QApairList/""", """WebAPI.StudentAPI.GetQAPairList()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """QAoperation/""", """WebAPI.StudentAPI.QAPairOperation()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ClassListDetail/""", """WebAPI.StudentAPI.GetClassWithDetail()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """EvaluationPairList/""", """WebAPI.StudentAPI.PEPairList()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """PEoperation/""", """WebAPI.StudentAPI.PEOperation()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """TeachesAssignment/""", """WebAPI.GraderAPI.GetCourseAssignments()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ViewStudentProcedure/""", """WebAPI.GraderAPI.GetStudentProcedure()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ViewStudentHomework/""", """WebAPI.GraderAPI.GetStudentHomework()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Boost/""", """WebAPI.BoostInterface.BoostApi()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      """ An example controller showing a sample home page""",
      this.prefix + """"""
    )
  )

  // @LINE:8
  private[this] lazy val WebAPI_UserAPI_VerifyUser1_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Login/")))
  )
  private[this] lazy val WebAPI_UserAPI_VerifyUser1_invoker = createInvoker(
    UserAPI_1.VerifyUser(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.UserAPI",
      "VerifyUser",
      Nil,
      "POST",
      """ the API is used to interaction with USER""",
      this.prefix + """Login/"""
    )
  )

  // @LINE:9
  private[this] lazy val WebAPI_UserAPI_StudentSignUP2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("SignUP/")))
  )
  private[this] lazy val WebAPI_UserAPI_StudentSignUP2_invoker = createInvoker(
    UserAPI_1.StudentSignUP(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.UserAPI",
      "StudentSignUP",
      Nil,
      "POST",
      """""",
      this.prefix + """SignUP/"""
    )
  )

  // @LINE:10
  private[this] lazy val WebAPI_UserAPI_ActivateAccount3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Active/"), DynamicPart("MAC", """[^/]+""",true)))
  )
  private[this] lazy val WebAPI_UserAPI_ActivateAccount3_invoker = createInvoker(
    UserAPI_1.ActivateAccount(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.UserAPI",
      "ActivateAccount",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """Active/""" + "$" + """MAC<[^/]+>"""
    )
  )

  // @LINE:11
  private[this] lazy val WebAPI_UserAPI_IssuePassword4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Personal_Inform/")))
  )
  private[this] lazy val WebAPI_UserAPI_IssuePassword4_invoker = createInvoker(
    UserAPI_1.IssuePassword(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.UserAPI",
      "IssuePassword",
      Nil,
      "POST",
      """""",
      this.prefix + """Personal_Inform/"""
    )
  )

  // @LINE:12
  private[this] lazy val WebAPI_UserAPI_PersonalInform5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Personal_Inform/")))
  )
  private[this] lazy val WebAPI_UserAPI_PersonalInform5_invoker = createInvoker(
    UserAPI_1.PersonalInform(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.UserAPI",
      "PersonalInform",
      Nil,
      "GET",
      """""",
      this.prefix + """Personal_Inform/"""
    )
  )

  // @LINE:13
  private[this] lazy val WebAPI_UserAPI_ForgetPassword6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ForgetPassowrd/")))
  )
  private[this] lazy val WebAPI_UserAPI_ForgetPassword6_invoker = createInvoker(
    UserAPI_1.ForgetPassword(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.UserAPI",
      "ForgetPassword",
      Nil,
      "POST",
      """""",
      this.prefix + """ForgetPassowrd/"""
    )
  )

  // @LINE:15
  private[this] lazy val WebAPI_StudentAPI_GetClassList7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ClassList/")))
  )
  private[this] lazy val WebAPI_StudentAPI_GetClassList7_invoker = createInvoker(
    StudentAPI_5.GetClassList(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.StudentAPI",
      "GetClassList",
      Nil,
      "GET",
      """ the following API is design for Student, which is a special kind of user """,
      this.prefix + """ClassList/"""
    )
  )

  // @LINE:16
  private[this] lazy val WebAPI_StudentAPI_GetTaskList8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("TaskList/")))
  )
  private[this] lazy val WebAPI_StudentAPI_GetTaskList8_invoker = createInvoker(
    StudentAPI_5.GetTaskList(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.StudentAPI",
      "GetTaskList",
      Nil,
      "POST",
      """""",
      this.prefix + """TaskList/"""
    )
  )

  // @LINE:17
  private[this] lazy val WebAPI_StudentAPI_GetQAPairList9_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("QApairList/")))
  )
  private[this] lazy val WebAPI_StudentAPI_GetQAPairList9_invoker = createInvoker(
    StudentAPI_5.GetQAPairList(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.StudentAPI",
      "GetQAPairList",
      Nil,
      "POST",
      """""",
      this.prefix + """QApairList/"""
    )
  )

  // @LINE:18
  private[this] lazy val WebAPI_StudentAPI_QAPairOperation10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("QAoperation/")))
  )
  private[this] lazy val WebAPI_StudentAPI_QAPairOperation10_invoker = createInvoker(
    StudentAPI_5.QAPairOperation(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.StudentAPI",
      "QAPairOperation",
      Nil,
      "POST",
      """""",
      this.prefix + """QAoperation/"""
    )
  )

  // @LINE:19
  private[this] lazy val WebAPI_StudentAPI_GetClassWithDetail11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ClassListDetail/")))
  )
  private[this] lazy val WebAPI_StudentAPI_GetClassWithDetail11_invoker = createInvoker(
    StudentAPI_5.GetClassWithDetail(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.StudentAPI",
      "GetClassWithDetail",
      Nil,
      "GET",
      """""",
      this.prefix + """ClassListDetail/"""
    )
  )

  // @LINE:20
  private[this] lazy val WebAPI_StudentAPI_PEPairList12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("EvaluationPairList/")))
  )
  private[this] lazy val WebAPI_StudentAPI_PEPairList12_invoker = createInvoker(
    StudentAPI_5.PEPairList(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.StudentAPI",
      "PEPairList",
      Nil,
      "POST",
      """""",
      this.prefix + """EvaluationPairList/"""
    )
  )

  // @LINE:21
  private[this] lazy val WebAPI_StudentAPI_PEOperation13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("PEoperation/")))
  )
  private[this] lazy val WebAPI_StudentAPI_PEOperation13_invoker = createInvoker(
    StudentAPI_5.PEOperation(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.StudentAPI",
      "PEOperation",
      Nil,
      "POST",
      """""",
      this.prefix + """PEoperation/"""
    )
  )

  // @LINE:23
  private[this] lazy val WebAPI_GraderAPI_GetCourseAssignments14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("TeachesAssignment/")))
  )
  private[this] lazy val WebAPI_GraderAPI_GetCourseAssignments14_invoker = createInvoker(
    GraderAPI_2.GetCourseAssignments(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.GraderAPI",
      "GetCourseAssignments",
      Nil,
      "POST",
      """ the following API is design for TA, which is a special kind of user""",
      this.prefix + """TeachesAssignment/"""
    )
  )

  // @LINE:24
  private[this] lazy val WebAPI_GraderAPI_GetStudentProcedure15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ViewStudentProcedure/")))
  )
  private[this] lazy val WebAPI_GraderAPI_GetStudentProcedure15_invoker = createInvoker(
    GraderAPI_2.GetStudentProcedure(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.GraderAPI",
      "GetStudentProcedure",
      Nil,
      "POST",
      """""",
      this.prefix + """ViewStudentProcedure/"""
    )
  )

  // @LINE:25
  private[this] lazy val WebAPI_GraderAPI_GetStudentHomework16_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ViewStudentHomework/")))
  )
  private[this] lazy val WebAPI_GraderAPI_GetStudentHomework16_invoker = createInvoker(
    GraderAPI_2.GetStudentHomework(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.GraderAPI",
      "GetStudentHomework",
      Nil,
      "POST",
      """""",
      this.prefix + """ViewStudentHomework/"""
    )
  )

  // @LINE:27
  private[this] lazy val controllers_Assets_versioned17_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned17_invoker = createInvoker(
    Assets_3.versioned(fakeValue[String], fakeValue[Asset]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      """ Map static resources from the /public folder to the /assets URL path""",
      this.prefix + """assets/""" + "$" + """file<.+>"""
    )
  )

  // @LINE:29
  private[this] lazy val WebAPI_BoostInterface_BoostApi18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Boost/")))
  )
  private[this] lazy val WebAPI_BoostInterface_BoostApi18_invoker = createInvoker(
    BoostInterface_4.BoostApi(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "WebAPI.BoostInterface",
      "BoostApi",
      Nil,
      "GET",
      """ for boost Debug""",
      this.prefix + """Boost/"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_index0_route(params) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_0.index)
      }
  
    // @LINE:8
    case WebAPI_UserAPI_VerifyUser1_route(params) =>
      call { 
        WebAPI_UserAPI_VerifyUser1_invoker.call(UserAPI_1.VerifyUser())
      }
  
    // @LINE:9
    case WebAPI_UserAPI_StudentSignUP2_route(params) =>
      call { 
        WebAPI_UserAPI_StudentSignUP2_invoker.call(UserAPI_1.StudentSignUP())
      }
  
    // @LINE:10
    case WebAPI_UserAPI_ActivateAccount3_route(params) =>
      call(params.fromPath[String]("MAC", None)) { (MAC) =>
        WebAPI_UserAPI_ActivateAccount3_invoker.call(UserAPI_1.ActivateAccount(MAC))
      }
  
    // @LINE:11
    case WebAPI_UserAPI_IssuePassword4_route(params) =>
      call { 
        WebAPI_UserAPI_IssuePassword4_invoker.call(UserAPI_1.IssuePassword())
      }
  
    // @LINE:12
    case WebAPI_UserAPI_PersonalInform5_route(params) =>
      call { 
        WebAPI_UserAPI_PersonalInform5_invoker.call(UserAPI_1.PersonalInform())
      }
  
    // @LINE:13
    case WebAPI_UserAPI_ForgetPassword6_route(params) =>
      call { 
        WebAPI_UserAPI_ForgetPassword6_invoker.call(UserAPI_1.ForgetPassword())
      }
  
    // @LINE:15
    case WebAPI_StudentAPI_GetClassList7_route(params) =>
      call { 
        WebAPI_StudentAPI_GetClassList7_invoker.call(StudentAPI_5.GetClassList())
      }
  
    // @LINE:16
    case WebAPI_StudentAPI_GetTaskList8_route(params) =>
      call { 
        WebAPI_StudentAPI_GetTaskList8_invoker.call(StudentAPI_5.GetTaskList())
      }
  
    // @LINE:17
    case WebAPI_StudentAPI_GetQAPairList9_route(params) =>
      call { 
        WebAPI_StudentAPI_GetQAPairList9_invoker.call(StudentAPI_5.GetQAPairList())
      }
  
    // @LINE:18
    case WebAPI_StudentAPI_QAPairOperation10_route(params) =>
      call { 
        WebAPI_StudentAPI_QAPairOperation10_invoker.call(StudentAPI_5.QAPairOperation())
      }
  
    // @LINE:19
    case WebAPI_StudentAPI_GetClassWithDetail11_route(params) =>
      call { 
        WebAPI_StudentAPI_GetClassWithDetail11_invoker.call(StudentAPI_5.GetClassWithDetail())
      }
  
    // @LINE:20
    case WebAPI_StudentAPI_PEPairList12_route(params) =>
      call { 
        WebAPI_StudentAPI_PEPairList12_invoker.call(StudentAPI_5.PEPairList())
      }
  
    // @LINE:21
    case WebAPI_StudentAPI_PEOperation13_route(params) =>
      call { 
        WebAPI_StudentAPI_PEOperation13_invoker.call(StudentAPI_5.PEOperation())
      }
  
    // @LINE:23
    case WebAPI_GraderAPI_GetCourseAssignments14_route(params) =>
      call { 
        WebAPI_GraderAPI_GetCourseAssignments14_invoker.call(GraderAPI_2.GetCourseAssignments())
      }
  
    // @LINE:24
    case WebAPI_GraderAPI_GetStudentProcedure15_route(params) =>
      call { 
        WebAPI_GraderAPI_GetStudentProcedure15_invoker.call(GraderAPI_2.GetStudentProcedure())
      }
  
    // @LINE:25
    case WebAPI_GraderAPI_GetStudentHomework16_route(params) =>
      call { 
        WebAPI_GraderAPI_GetStudentHomework16_invoker.call(GraderAPI_2.GetStudentHomework())
      }
  
    // @LINE:27
    case controllers_Assets_versioned17_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned17_invoker.call(Assets_3.versioned(path, file))
      }
  
    // @LINE:29
    case WebAPI_BoostInterface_BoostApi18_route(params) =>
      call { 
        WebAPI_BoostInterface_BoostApi18_invoker.call(BoostInterface_4.BoostApi())
      }
  }
}
