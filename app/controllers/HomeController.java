package controllers;

import java.io.FileInputStream;

import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
	String LoadDebugModule()
	{
		String Result="";
		try{
		     FileInputStream in= new FileInputStream("C:/Users/zheny/Documents/studentqa4/TestObject.html");
		     do{
		    	 int b= in.read();
		    	 if( b == -1 ){ break;}
		    	 Result += (char) b;
		     }while(true);
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		return Result;
	}
    public Result index() {
    	String HTML = LoadDebugModule();
    	return ok(HTML).as("text/html");
    }

}
