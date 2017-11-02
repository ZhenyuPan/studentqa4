
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/zheny/Documents/studentqa4/conf/routes
// @DATE:Wed Oct 25 23:58:50 EDT 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
