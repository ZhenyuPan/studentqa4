package WebAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;

import org.json.simple.JSONObject;

import JavaAES128MAC.MacKey;
import Physic_Structure.DatabaseHandle;
import akka.stream.actor.ActorPublisherMessage.Request;
import play.mvc.*;
import Memory_Structure.Email_Helper;
public class UserAPI extends  Controller
{
	static Memory_Structure.Email_Helper email = null;
	/**
	 * URL         : /Login/
	 * Method      : Post
	 * Request Body: 
	 *      JSON object with following segment 
	 *               EmailAddress: the email address of user
	 *               Password    : the password given by user
	 * Responds Body:
	 *      JSON object with following segment
	 *               Result      : true if user passed the verify
	 *               id          : the system id of user if user passed the verify       
	 * */
	public Result VerifyUser()
	{
		String EmailAddress= request().body().asJson().get("EmailAddress").asText();
		String Password    = request().body().asJson().get("Password").asText();
		String Query = "Select id,Activated from Userlist where EmailAddress like ? and UKey like ?";
		DatabaseHandle DH = new DatabaseHandle();
		JSONObject result = new JSONObject();
		result.put("Result", false);
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setString(1, EmailAddress);
			pstmt.setString(2, Password);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next() )
			{
				int SystemID = rs.getInt(1);
				int Activated= rs.getInt(2);
				if( Activated == 0)
				{// if the user account have not been locked 
					result.put("Result", true);
					result.put("Exception-Code","000");
					session("SystemID",""+SystemID);
					return ok(result.toJSONString());
				}
				else
				{// if the user account have been locked 
					result.put("Result", "");
					result.put("Exception-Code", "016");
					return ok(result.toJSONString());
				}
			}
			else
			{
				result.put("Result", "");
				result.put("Exception-Code", "003");
				//003 means we cannot find such user in database 
				return ok(result.toJSONString());
			}
		}catch(Exception E)
		{
			result.put("Exception-code", "017");
			//017 means some undefined bug
			E.printStackTrace();
			return ok(result.toJSONString());
		}
	}
    /**
     * this function is used to get the maximum id of user
     * */
	private int GetMaximumID()
	{
		String Query="select max(id) from Userlist";
		DatabaseHandle DH = new DatabaseHandle();
		try{
			Statement Stmt = DH.Conn.createStatement();
			ResultSet rs   = Stmt.executeQuery(Query);
			if(rs.next())
			{
				return rs.getInt(1);
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		return 0;
	}
	/**
	 * this function is used to email the new user his activate url
	 * @param id: the new user id
	 * @param ActivateCode: the 5 decimal random number to generate the one time url
	 * @param EmailAddress: the email address of new user
	 * @return true if the email have been deliver success
	 */
	private boolean SendActivateURL(int id, int ActivateCode, String EmailAddress)
	{
		if (this.email == null )
		{
			this.email = new Memory_Structure.Email_Helper("zhenyupanumlstudent@gmail.com", "UID01394889");
		}
		String MC = JavaAES128MAC.MacKey.generateMacKey(id, ActivateCode);
		String URL= "https://129.63.16.28/Active/"+MC;
	    return this.email.Sending_Message(EmailAddress,"welcome to use Student QA system", "please visit following url to active your account\n"+URL);
	}
	/**
	 * URL         : /SignUP/
	 * Method      : Post
	 * Request Body: 
	 *      JSON object with following segment :
	 *             EmailAddress: the email address of user 
	 * Responds Body:
	 *      JSON object:
	 *             Result: true   if the user with such email address is not exist
	 *                     false  if such email address have been used by others 
	 * */
	public Result StudentSignUP()
	{
		java.util.Random Random = new java.util.Random();
		String EmailAddress=request().body().asJson().get("EmailAddress").asText();
	    int    ActiveCode  = Random.nextInt(10000)+1;
	    int    NewID       = this.GetMaximumID()+1;
	    do {
	    	if( this.FindUser(MacKey.generateMacKey(NewID, ActiveCode))== null )
	    	{// check if there has another user use such active code ;
	    		break;
	    	}
	    	else
	    	{// if there has then regenerate the code 
	    		ActiveCode = Random.nextInt(10000)+1;
	    	}
	    }while(true);
	    //check if there MAC code have duplication 
	    String Insert = "Insert into Userlist(id,EmailAddress,Activated) values(?,?,?)";
	    DatabaseHandle DH = new DatabaseHandle();
	    JSONObject result = new JSONObject();
	    result.put("Result", false);
	    try{
	    	PreparedStatement pstmt = DH.Conn.prepareStatement(Insert);
	    	pstmt.setInt(1, NewID);
	    	pstmt.setString(2, EmailAddress);
	    	pstmt.setInt(3, ActiveCode);
	    	pstmt.executeUpdate();
	    	// add new user into the system 
	    	if(	SendActivateURL(NewID,ActiveCode,EmailAddress) )
	    	{	// send the user one times url
	    		result.put("Result", true);
	    		result.put("Exception-Code", "000");
	    		//have send one time link 
	    		return ok(result.toJSONString());
	    	}else
	    	{
	    		result.put("Result", "");
	    		result.put("Exception-Code", "002");
	    		//such email address cannot send email
	    		return ok(result.toJSONString());
	    	}
	    }catch(Exception E)
	    {
	    	result.put("Result", "");
	    	result.put("Exception-Code", "001");
	    	//such email address have been used 
	    	return ok(result.toJSONString());
	    }
	}
	/**
	 * URL        : /Active/#MAC
	 * Method 	  : GET
	 * responds   : 
	 *          if the user's request url is correct, then redirect to the user's personal page to let him modify his information
	 *          otherwise return 404 not found
	 */
	public Result ActivateAccount(String MAC)
	{
		String SignUPPage="";
		String Query="Select id,Activated,EmailAddress from Userlist where Activated !=0";
		DatabaseHandle DH = new DatabaseHandle();
		try{
			Statement Stmt=DH.Conn.createStatement();
			ResultSet rs  =Stmt.executeQuery(Query);
			while( rs.next())
			{
				int id= rs.getInt(1);
				int Act= rs.getInt(2);
				String CurrentMac=MacKey.generateMacKey(id, Act);
				if( CurrentMac.equals(MAC))
				{
					String EmailAddress = rs.getString(3);
					System.out.println("<"+id+","+EmailAddress+">");
					session("EmailAddress",EmailAddress);
					session("SystemID",""+id);
					return redirect("/Personal_Inform/");
				}
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		return notFound();
	}
	/**
	 * display the interfact to allow user interaction with the personal information
	 * */
	public Result PersonalInform()
	{
		String EmailAddress = session().get("EmailAddress");
		if( EmailAddress==null || EmailAddress.length() == 0)
		{
			return notFound();
		}
		String HTML="<html>\n"
				+" <head>\n"
				+"      <title>\n"
				+"            Activate your accout\n"
				+"      </title>\n"
				+"  </head>\n"
				+"  <script>\n"
				+"		function POST()\n"
				+"		{\n"
				+"			var url= \"/Personal_Inform/\"\n"
				+"			var password = document.getElementById(\"password\").value;\n"
				+"          var CampusID = document.getElementById(\"CampusID\").value\n"
				+"			var xhr      = new XMLHttpRequest();\n"
				+"			xhr.onreadystatechange = function()\n"
				+"			{\n"
				+"				if (xhr.readyState == 4) {\n"
				+"					window.location.replace(\"/\");\n"
				+"			 	}\n"
				+"		    }\n"
				+"			xhr.open(\"POST\",url,true);\n"
				+"			xhr.setRequestHeader(\"Content-type\",'application/json; charset=UTF-8');\n"
				+"			var params= {\"password\":\"\",\"CampusID\":\"\"};\n"
				+"			params.password=password;\n"
				+"			params.CampusID=CampusID;\n"
				+"			xhr.send(JSON.stringify(params))\n"
				+"		}\n"
				+"  </script>\n"
				+"  <body>\n"
				+"      <p>user:"+EmailAddress+"\n"
				+"      <p>please enter your password to activate your account:</p>\n"
				+"      <p><textarea id=\"password\"></textarea></p>\n"
				+"      <p>please enter your Campus ID</p>\n"
				+"      <p><textarea id=\"CampusID\"></textarea></p>\n"
				+"      <button onclick=\"POST();\">Issue Password</button>\n"
				+"  </body>\n"
				+"</html>";
		return ok(HTML).as("text/html");
	}
	/**
	 * URL        : /Personal_Inform/
	 * Method     : POST
	 * request    :
	 *           JSON object with following segment:
	 *                Password: the new password for user;
	 *                CampusID: the new Campus id for user;
	 * responds body:
	 *            Result: true if user success update his password;
	 *            false : if run into some trouble;  
	 */
	public Result IssuePassword()
	{
		String StudentID= session().get("SystemID");
		JSONObject result=new JSONObject();
		result.put("Result", false);
		if( StudentID == null || StudentID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-Code", "005");
			//user directly call this page without verified 
			return ok(result.toJSONString());
		}
		DatabaseHandle DH = new DatabaseHandle();
		int ID= Integer.parseInt(StudentID);
		int Campusid = 0;
		com.fasterxml.jackson.databind.JsonNode CampJson=request().body().asJson().get("CampusID");
		if( CampJson == null || CampJson.asText().length() ==0)
		{
			result.put("Result", "");
			result.put("Exception-Code", "004");
			//user input campus ID is null
			return ok(result.toJSONString());			
		}
		Campusid = CampJson.asInt();
		com.fasterxml.jackson.databind.JsonNode PasswordJson=request().body().asJson().get("password");
		if( PasswordJson == null || PasswordJson.asText().length() == 0)
		{
			result.put("Result", "");
			result.put("Exception-Code", "004");
			return ok(result.toJSONString());
		}
		String Password = request().body().asJson().get("password").asText();
		String Insert   = "Update Userlist set Activated=0,UKey=?,CampusID=? where id="+ID;
		try{
				PreparedStatement pstmt = DH.Conn.prepareStatement(Insert);
				pstmt.setString(1, Password);
				pstmt.setInt(2, Campusid);
				pstmt.executeUpdate();
				session().clear();
				result.put("Result", true);
				result.put("Exception-Code", "000");
				return ok(result.toJSONString());
		}catch(Exception E)
		{
				E.printStackTrace();
				result.put("Result", "");
				result.put("Exception-Code", "018");
		}
		return ok(result.toJSONString());
	}
	/**
	 * URL         : /ForgetPassowrd/
	 * Method      : POST
	 * Request Body: 
	 *      JSON Object with following segment:
	 *              EmailAddress: the email address of user
	 * Responds Body:
	 *      JSON Object with following segment:
	 *              Result :  true  if the user with such email address is exist
	 *                        false if such email address not exist
	 * Function    :
	 *       it will lock the user account, and sent a one times url to user's email
	 */
	public Result ForgetPassword()
	{
		JSONObject result = new JSONObject();
		result.put("Result", false);
		java.util.Random Random = new java.util.Random();
		String EmailAddress=request().body().asJson().get("EmailAddress").asText();
		String QueryID = "select id from Userlist where EmailAddress like ?";
		DatabaseHandle DH = new DatabaseHandle();
		int  UserID=-1;
		try{
			PreparedStatement pstmt =DH.Conn.prepareStatement(QueryID);
			pstmt.setString(1, EmailAddress);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				UserID= rs.getInt(1);
			}
			else
			{
				result.put("Result", "");
				result.put("Exception-Code", "003");
				return ok(result.toJSONString());
				//such user not exist
			}
		}catch( Exception E)
		{
			E.printStackTrace();
			return ok(result.toJSONString());
		}
		//find the user id with given 
		int    ActiveCode  = Random.nextInt(1000)+1;
		do{
		    if( this.FindUser(MacKey.generateMacKey(UserID, ActiveCode))== null )
		    {
		    	break;
		    }
		    else
		    {
		    	ActiveCode = Random.nextInt(10000)+1;
		    }
		}while(true);
		String LockAccout="update Userlist set Activated=? where id=?";
		try{
			PreparedStatement pstmt=DH.Conn.prepareStatement(LockAccout);
			pstmt.setInt(1, ActiveCode);
			pstmt.setInt(2, UserID);
			pstmt.executeUpdate();
	    	SendActivateURL(UserID,ActiveCode,EmailAddress);
	    	// send the user one times url
			result.put("Result", true);
			result.put("Exception-Code", "000");
			return ok(result.toJSONString());
		}catch(Exception E)
		{
			E.printStackTrace();
			result.put("Result", true);
			result.put("Exception-Code", "018");
			return ok(result.toJSONString());
		}
	}
	/**
	 * @function 	: this function is used to find the user who match the given mac code 
	 * @return 		: null		if we couldn't find such user 
	 * 				  the system id list of user who have the MAC code 
	 */
	private int[] FindUser(String MAC)
	{
		List<Integer> Candidate= new ArrayList<Integer>();
		DatabaseHandle DH = new DatabaseHandle();
		String Query="Select id,Activated from Userlist where Activated !=0" ;
		try{
			Statement Stmt=DH.Conn.createStatement();
			ResultSet rs  =Stmt.executeQuery(Query);
			while( rs.next())
			{
				int id= rs.getInt(1);
				int Act= rs.getInt(2);
				String CurrentMac=MacKey.generateMacKey(id, Act);
				if(CurrentMac.equals(MAC))
				{
					Candidate.add(id);
				}
			}
			if( Candidate.size() == 0)
			{
				return null;
			}
			else
			{
				int[] Result = new int[Candidate.size()];
				for( int i= 0  ; i < Candidate.size() ; i++ )
				{
					Result[i] = Candidate.get(i);
				}
				return Result;
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		return null;
	}

}
