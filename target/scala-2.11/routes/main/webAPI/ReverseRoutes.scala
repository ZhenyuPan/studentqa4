
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/zheny/Documents/studentqa4/conf/routes
// @DATE:Wed Oct 25 23:58:50 EDT 2017

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:8
package WebAPI {

  // @LINE:29
  class ReverseBoostInterface(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:29
    def BoostApi(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "Boost/")
    }
  
  }

  // @LINE:15
  class ReverseStudentAPI(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:19
    def GetClassWithDetail(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "ClassListDetail/")
    }
  
    // @LINE:18
    def QAPairOperation(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "QAoperation/")
    }
  
    // @LINE:17
    def GetQAPairList(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "QApairList/")
    }
  
    // @LINE:21
    def PEOperation(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "PEoperation/")
    }
  
    // @LINE:20
    def PEPairList(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "EvaluationPairList/")
    }
  
    // @LINE:15
    def GetClassList(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "ClassList/")
    }
  
    // @LINE:16
    def GetTaskList(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "TaskList/")
    }
  
  }

  // @LINE:8
  class ReverseUserAPI(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:11
    def IssuePassword(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "Personal_Inform/")
    }
  
    // @LINE:13
    def ForgetPassword(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "ForgetPassowrd/")
    }
  
    // @LINE:12
    def PersonalInform(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "Personal_Inform/")
    }
  
    // @LINE:8
    def VerifyUser(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "Login/")
    }
  
    // @LINE:9
    def StudentSignUP(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "SignUP/")
    }
  
    // @LINE:10
    def ActivateAccount(MAC:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "Active/" + implicitly[PathBindable[String]].unbind("MAC", dynamicString(MAC)))
    }
  
  }

  // @LINE:23
  class ReverseGraderAPI(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:25
    def GetStudentHomework(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "ViewStudentHomework/")
    }
  
    // @LINE:23
    def GetCourseAssignments(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "TeachesAssignment/")
    }
  
    // @LINE:24
    def GetStudentProcedure(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "ViewStudentProcedure/")
    }
  
  }


}
