
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/zheny/Documents/studentqa4/conf/routes
// @DATE:Wed Oct 25 23:58:50 EDT 2017

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:8
package WebAPI.javascript {
  import ReverseRouteContext.empty

  // @LINE:29
  class ReverseBoostInterface(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:29
    def BoostApi: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.BoostInterface.BoostApi",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "Boost/"})
        }
      """
    )
  
  }

  // @LINE:15
  class ReverseStudentAPI(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:19
    def GetClassWithDetail: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.StudentAPI.GetClassWithDetail",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "ClassListDetail/"})
        }
      """
    )
  
    // @LINE:18
    def QAPairOperation: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.StudentAPI.QAPairOperation",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "QAoperation/"})
        }
      """
    )
  
    // @LINE:17
    def GetQAPairList: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.StudentAPI.GetQAPairList",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "QApairList/"})
        }
      """
    )
  
    // @LINE:21
    def PEOperation: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.StudentAPI.PEOperation",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "PEoperation/"})
        }
      """
    )
  
    // @LINE:20
    def PEPairList: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.StudentAPI.PEPairList",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "EvaluationPairList/"})
        }
      """
    )
  
    // @LINE:15
    def GetClassList: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.StudentAPI.GetClassList",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "ClassList/"})
        }
      """
    )
  
    // @LINE:16
    def GetTaskList: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.StudentAPI.GetTaskList",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "TaskList/"})
        }
      """
    )
  
  }

  // @LINE:8
  class ReverseUserAPI(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:11
    def IssuePassword: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.UserAPI.IssuePassword",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "Personal_Inform/"})
        }
      """
    )
  
    // @LINE:13
    def ForgetPassword: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.UserAPI.ForgetPassword",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "ForgetPassowrd/"})
        }
      """
    )
  
    // @LINE:12
    def PersonalInform: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.UserAPI.PersonalInform",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "Personal_Inform/"})
        }
      """
    )
  
    // @LINE:8
    def VerifyUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.UserAPI.VerifyUser",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "Login/"})
        }
      """
    )
  
    // @LINE:9
    def StudentSignUP: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.UserAPI.StudentSignUP",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "SignUP/"})
        }
      """
    )
  
    // @LINE:10
    def ActivateAccount: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.UserAPI.ActivateAccount",
      """
        function(MAC0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "Active/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("MAC", encodeURIComponent(MAC0))})
        }
      """
    )
  
  }

  // @LINE:23
  class ReverseGraderAPI(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:25
    def GetStudentHomework: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.GraderAPI.GetStudentHomework",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "ViewStudentHomework/"})
        }
      """
    )
  
    // @LINE:23
    def GetCourseAssignments: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.GraderAPI.GetCourseAssignments",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "TeachesAssignment/"})
        }
      """
    )
  
    // @LINE:24
    def GetStudentProcedure: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "WebAPI.GraderAPI.GetStudentProcedure",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "ViewStudentProcedure/"})
        }
      """
    )
  
  }


}
