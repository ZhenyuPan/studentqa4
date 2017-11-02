
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/zheny/Documents/studentqa4/conf/routes
// @DATE:Wed Oct 25 23:58:50 EDT 2017

package WebAPI;

import router.RoutesPrefix;

public class routes {
  
  public static final WebAPI.ReverseBoostInterface BoostInterface = new WebAPI.ReverseBoostInterface(RoutesPrefix.byNamePrefix());
  public static final WebAPI.ReverseStudentAPI StudentAPI = new WebAPI.ReverseStudentAPI(RoutesPrefix.byNamePrefix());
  public static final WebAPI.ReverseUserAPI UserAPI = new WebAPI.ReverseUserAPI(RoutesPrefix.byNamePrefix());
  public static final WebAPI.ReverseGraderAPI GraderAPI = new WebAPI.ReverseGraderAPI(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final WebAPI.javascript.ReverseBoostInterface BoostInterface = new WebAPI.javascript.ReverseBoostInterface(RoutesPrefix.byNamePrefix());
    public static final WebAPI.javascript.ReverseStudentAPI StudentAPI = new WebAPI.javascript.ReverseStudentAPI(RoutesPrefix.byNamePrefix());
    public static final WebAPI.javascript.ReverseUserAPI UserAPI = new WebAPI.javascript.ReverseUserAPI(RoutesPrefix.byNamePrefix());
    public static final WebAPI.javascript.ReverseGraderAPI GraderAPI = new WebAPI.javascript.ReverseGraderAPI(RoutesPrefix.byNamePrefix());
  }

}
