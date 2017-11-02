package WebAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Memory_Structure.ClassAspect;
import Memory_Structure.Student;
import Memory_Structure.TA;
import Physic_Structure.DatabaseHandle;
import play.mvc.Controller;
import play.mvc.Result;
public class GraderAPI extends Controller
{
	/**
	 * URL: 
	 * 		/TeachesAssignment/
	 * Method: 
	 * 		POST
	 * Request Body:
	 * 		Class-id	the system id of Class that current TA teaches
	 * Response Body:
	 * 		Task-List	JSONArray of Task that belong to given class with following segment
	 * 			Task-id			the system id of Task 
	 * 			Task-Title		the Title of such task 
	 * 			Task-Des		the Description of such Task
	 * 			Task-DueDate	the DueDate of such Task
	 * 		Exception-Code
	 * 			000				success
	 * 			005				user not login before using the system
	 * 			019				request input error 
	 * 			021				the user is not TA for given class
	 * */
	public Result GetCourseAssignments()
	{
		String UserID = session().get("SystemID");
		JSONObject result = new JSONObject();
		if( UserID== null || UserID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-code", "005");
			return ok(result.toJSONString());
			// the user not login before he use the system
		}
		String ClassID = request().body().asJson().get("Class-id").asText();
		if( ClassID==null ||ClassID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-code", "019");
			return ok(result.toJSONString());
			// the Class ID is null;
		}
		int TA_SystemID;
		int Teach_ClassID;
		try{
			TA_SystemID = Integer.parseInt(UserID);
			Teach_ClassID=Integer.parseInt(ClassID);
			TA ta = new TA();
			if( !ta.IsTeachSuchClass(TA_SystemID, Teach_ClassID))
			{
				result.put("Result", "");
				result.put("Exception-code", "020");
				return ok(result.toJSONString());
			}
			JSONArray TaskList= ta.ListTeachTask(TA_SystemID, Teach_ClassID);
			result.put("Task-list", TaskList);
			result.put("Exception-code", "000");
			return ok(result.toJSONString());
		}catch(Exception E)
		{
			E.printStackTrace();
			result.put("Result", "");
			result.put("Exception-code", "018");
			return ok(result.toJSONString());
		}
	}
	/**
	 * URL: 	/ViewStudentProcedure/
	 * Method: 	POST
	 * RequestBody
	 * 			Task-id		the system id of Task current TA works on 
	 * Respondes 
	 * 			Stduent-list	the json-array to show the student's working procedure on given task 
	 * 			Exception-Code	
	 * 			000				success
	 * 			005				user not login before using the system
	 * 			019				request input error 
	 * 			021				the user is not TA for given class
	 * */
	public Result GetStudentProcedure()
	{
		String UserID = session().get("SystemID");
		JSONObject result = new JSONObject();
		if( UserID== null || UserID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-code", "005");
			return ok(result.toJSONString());
			// the user not login before he use the system
		}
		String TaskID = request().body().asJson().get("Task-id").asText();
		if( TaskID == null|| TaskID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-code", "019");
			return ok(result.toJSONString());
		}
		TA ta = new TA();
		try{
			int TA_SystemID= Integer.parseInt(UserID);
			int Task_SystemID= Integer.parseInt(TaskID);
			if( !ta.IsGradeSuchTask(TA_SystemID, Task_SystemID))
			{
				result.put("Result", "");
				result.put("Exception-code", "021");
				return ok(result.toJSONString());
			}
			JSONArray Studentlist = ta.ListStudentProcedure(TA_SystemID, Task_SystemID);
			result.put("Student-list", Studentlist);
			result.put("Exception-code", "000");
			return ok(result.toJSONString());
		}catch(Exception E)
		{
			E.printStackTrace();
			result.put("Result", "");
			result.put("Exception-code", "018");
			return ok(result.toJSONString());
		}
	}
	/**
	 * URL	 :		/ViewStudentHomework/
	 * Method:		POST
	 * RequestBody
	 * 			Task-id		the system id of Task current TA works on 
	 * 			Student-id	the Campus id of student current TA grade on 
	 * Respondes
	 * 			QA-list			the json-array to show the QA list current student working on 
	 * 			Exception-Code
	 * 			005				user not login before using the system
	 * 			019				request input error 
	 * 			021				the user is not TA for given class
	 */
	public Result GetStudentHomework()
	{
		String UserID = session().get("SystemID");
		JSONObject result = new JSONObject();
		if( UserID== null || UserID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-code", "005");
			return ok(result.toJSONString());
			// the user not login before he use the system
		}
		String TaskID = request().body().asJson().get("Task-id").asText();
		if( TaskID == null|| TaskID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-code", "019");
			return ok(result.toJSONString());
		}
		String StudentID=request().body().asJson().get("Student-id").asText();
		if( StudentID == null || StudentID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-code", "019");
			return ok(result.toJSONString());			
		}	
		TA ta = new TA();
		try{
			int TA_SystemID= Integer.parseInt(UserID);
			int Task_SystemID= Integer.parseInt(TaskID);
			int Student_CampusID= Integer.parseInt(StudentID);
			if( !ta.IsGradeSuchTask(TA_SystemID, Task_SystemID))
			{
				result.put("Result", "");
				result.put("Exception-code", "021");
				return ok(result.toJSONString());
			}
			JSONArray QA_list = ta.CheckStudentProcedure(Task_SystemID, TA_SystemID, Student_CampusID);
			result.put("QA-list", QA_list);
			result.put("Exception-code", "000");
			return ok(result.toJSONString());
		}catch(Exception E)
		{
			E.printStackTrace();
			result.put("Result", "");
			result.put("Exception-code", "018");
			return ok(result.toJSONString());
		}
	}
}
