package WebAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Memory_Structure.ClassAspect;
import Memory_Structure.Student;
import Physic_Structure.DatabaseHandle;
import play.mvc.Controller;
import play.mvc.Result;
public class StudentAPI extends Controller
{
	/**
	 * URL:		/ClassListDetail/
	 * Method: 	GET
	 * Function :
	 * 		find the class that associcated with current user,
	 *  	alone with the assignment that associated with user 
	 * @return
	 * 		Enroll: 	a JSONArray which list all the active class current user enrolled
	 * 			Each Element is a JSON object with {"Class-ID":"","Class-Title":"","Task":[]}
	 * 		Grade:		a JSONArray which list all the active class current user works as Grader
	 * 			Each Element is a JSON object with {"Class-ID":"","Class-Title":"","Task":[]}
	 * 		Teach:		a JSONArray which list all the active class current user works as TA
	 * 			Each Element is a JSON object with {"Class-ID":"","Class-Title":"","Task":[]}
	 * 					a JSONArray Which list all the active class current user works as Instructor
	 * 		Exception-code:
	 * 			the execute code of the request 
	 */
	public Result GetClassWithDetail()
	{
		String UserID = session().get("SystemID");
		Student CurrentStudent= new Student();
		JSONObject result = new JSONObject();
		if( UserID== null || UserID.length() == 0 )
		{
			result.put("Enroll"	,	"");
			result.put("Grade"	, 	"");
			result.put("Teach"	, 	"");
			result.put("Exception-code", "005");
			return ok(result.toJSONString());
		}
		JSONArray Enrolled = CurrentStudent.GetClassList(Integer.parseInt(UserID),true, ClassAspect.Enroll);
		JSONArray Grade    = CurrentStudent.GetClassList(Integer.parseInt(UserID),true, ClassAspect.Grade);
		JSONArray Teach	   = CurrentStudent.GetClassList(Integer.parseInt(UserID),true, ClassAspect.Teach);
		if( Enrolled != null)
		{
			result.put("Enroll", Enrolled);
		}else
		{
			result.put("Enroll", "");
		}
		if( Grade != null)
		{
			result.put("Grader", Grade);
		}else
		{
			result.put("Grader", "");
		}
		if(Teach != null)
		{
			result.put("Teach",  Teach);
		}else
		{
			result.put("Teach", "");
		}
		result.put("Exception-code", "000");
		return ok(result.toJSONString());
	}
	/**
	 * URL:/ClassList/
	 * Method: GET
	 * Function:
	 * 		find the class that associated with current user
	 * Return:
	 * 		Enroll: 	a JSONArray which list all the active class current user enrolled
	 * 			Each Element is a JSON object with {"Class-ID":"","Class-Title":""}
	 * 		Grade:		a JSONArray which list all the active class current user works as Grader
	 * 			Each Element is a JSON object with {"Class-ID":"","Class-Title":""}
	 * 		Teach:		a JSONArray which list all the active class current user works as TA
	 * 			Each Element is a JSON object with {"Class-ID":"","Class-Title":""}
	 * 					a JSONArray Which list all the active class current user works as Instructor
	 * 		Exception-code:
	 * 			the execute code of the request 
	 * */
	public Result GetClassList()
	{
		String UserID = session().get("SystemID");
		Student CurrentStudent= new Student();
		JSONObject result = new JSONObject();
		if( UserID== null || UserID.length() == 0 )
		{
			result.put("Enroll"	,	"");
			result.put("Grade"	, 	"");
			result.put("Teach"	, 	"");
			result.put("Exception-code", "005");
			return ok(result.toJSONString());
		}
		JSONArray Enrolled = CurrentStudent.GetClassList(Integer.parseInt(UserID),false, ClassAspect.Enroll);
		JSONArray Grade    = CurrentStudent.GetClassList(Integer.parseInt(UserID),false, ClassAspect.Grade);
		JSONArray Teach	   = CurrentStudent.GetClassList(Integer.parseInt(UserID), false, ClassAspect.Teach);
		if( Enrolled != null)
		{
			result.put("Enroll", Enrolled);
		}else
		{
			result.put("Enroll", "");
		}
		if( Grade != null)
		{
			result.put("Grader", Grade);
		}else
		{
			result.put("Grader", "");
		}
		if(Teach != null)
		{
			result.put("Teach",  Teach);
		}else
		{
			result.put("Teach", "");
		}
		result.put("Exception-code", "000");
		return ok(result.toJSONString());
	}
	/**
	 * URL :/TaskList/
	 * Method: POST
	 * Input:
	 * 		Class-id
	 * Function:
	 * 		list all the assignment that assigned to current user and belong to given class 
	 * @return
	 * 		Result: a json Array contain element with following segment
	 * 			[Task-id,Task-Title,Task-Des,Task-DueDate,Task-Submitted,Task-Type,QA-Task-Volumn,PE-Task-Volumn]
	 * 		Exception-Code:
	 * 			000: 	success 
	 * 			005:	user not login before using the system
	 * 			019:	the Class-id is null
	 * 			007:	the student not enrolled in such class	
	 */
	public Result GetTaskList(){
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
		Student S = new Student();
		if( !S.CheckEnroll(Integer.parseInt(ClassID), Integer.parseInt(UserID)))
		{
			result.put("Result", "");
			result.put("Exception-code", "007");
			return ok(result.toJSONString());
			// the student not enrolled in given class;
		}
		JSONArray TaskList = S.GetAssignmentList(Integer.parseInt(ClassID), Integer.parseInt(UserID));
		result.put("Result", TaskList);
		result.put("Exception-code", "000");
		return ok(result.toJSONString());
	}
	/**
	 * URL:/QApariList/
	 * Method: POST
	 * Input:
	 * 		Task-id
	 * @return
	 * 		Result: a json array made by QA pair that created by user for given task
	 * 		Exception-Code:
	 * 			000:	success
	 * 			005:	user not login before using the system 			
	 * 			008:	the user have not been assigned such task
	 * 			011: 	the given task is not a QA task
	 * 			019:	lack input parameter Error
	 */
	public Result GetQAPairList()
	{
		JSONObject result = new JSONObject();
		String TaskID = request().body().asJson().get("Task-id").asText();
		if( TaskID == null || TaskID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-Code", "019");
			return ok(result.toJSONString());
		}
		String UserID = session().get("SystemID");
		if( UserID == null || UserID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-Code", "005");
			return ok(result.toJSONString());
		}
		Student S= new Student();
		if( !S.CheckAssign( Integer.parseInt(TaskID), Integer.parseInt(UserID) ) )
		{//such task are not assign to user
			result.put("Result", "");
			result.put("Exception-Code", "009");
			return ok(result.toJSONString());
		}
		if( !S.CheckQAType(Integer.parseInt(TaskID)))
		{//such task is not QA task
			result.put("Result", "");
			result.put("Exception-Code", "011");
			return ok(result.toJSONString());
		}
		JSONArray QAList = S.GetQAPairList( Integer.parseInt(TaskID), Integer.parseInt(UserID) );
		result.put("Result", QAList);
		result.put("Exception-Code","000");
		return ok(result.toJSONString());
	}
	/**
	 * URL:/QAoperation/
	 * Method:POST
	 * Input:
	 * 		QAID		the ID of QAPair				check in Delete and Update
	 * 		Question	the Question 					check in Create and Update
	 * 		Answer		the Answer						check in Create and Update
	 * 		Operation	Create | Delete |Update 		always check
	 * 		Task-id		the system id of task			check in create 
	 * @return
	 * 		Result		if the Operation have been performed 
	 * 		QAID		the new created Question Answer pair ID 
	 * 		Exception-Code:
	 * 			000:	success
	 * 			005:	user not login before using the system 			
	 * 			008:	the user have not been assigned such task
	 * 			009:	if the task have passed the due and been submitted 
	 * 			012:	the QApair is not belong to user
	 * 			019:	input parameter Error

	 */	
	public Result QAPairOperation()
	{
		try{
			String SystemID = session().get("SystemID");
			JSONObject result = new JSONObject();
			String QAPairID = request().body().asJson().get("QAID").asText();
			String Question = request().body().asJson().get("Question").asText();
			String Answer   = request().body().asJson().get("Answer").asText();
			String Operation= request().body().asJson().get("Operation").asText();
			String TaskID	= request().body().asJson().get("Task-id").asText();
			if( SystemID == null || SystemID.length() == 0)
			{
				result.put("Result", "");
				result.put("QAID", "");
				result.put("Exception-Code", "005");
				return ok(result.toJSONString());
			}
			if( Operation== null || Operation.length() == 0 )
			{
				result.put("Result", "");
				result.put("QAID", "");
				result.put("Exception-Code", "019");
				return ok(result.toJSONString());
			}
			if(Operation.equals("Create"))
			{
				if( Question == null || Question.length() == 0 )
				{
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "019");
					return ok(result.toJSONString());
				}
				if( Answer == null || Answer.length() == 0 )
				{
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "019");
					return ok(result.toJSONString());
				}
				if( TaskID == null || TaskID.length() == 0 )
				{
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "019");
					return ok(result.toJSONString());
				}
				Student S = new Student();
				if( !S.CheckAssign(Integer.parseInt(TaskID), Integer.parseInt(SystemID)))
				{// if the user not been assigned such task
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "008");
					return ok(result.toJSONString());
				}
				if( S.CheckPassDueDate(Integer.parseInt(TaskID)) && S.CheckNotSubmit(Integer.parseInt(TaskID), Integer.parseInt(SystemID)))
				{// if the task have passed the due and been submitted 
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "009");
					return ok(result.toJSONString());
				}
				int QAID=S.CreateQAPair(Integer.parseInt(TaskID), Integer.parseInt(SystemID), Question, Answer);
				result.put("Result", "true");
				result.put("QAID", QAID);
				result.put("Exception-Code","000");
				return ok(result.toJSONString());
			}
			if( Operation.equals("Delete"))
			{
				if(QAPairID==null || QAPairID.length() == 0 )
				{
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "019");
					return ok(result.toJSONString());
				}
				Student S = new Student();
				int AssignmentID = S.GetQATASKID(Integer.parseInt(QAPairID));
				// get the task that the QApair belong to 
				if( S.CheckPassDueDate(AssignmentID) && S.CheckNotSubmit(AssignmentID, Integer.parseInt(SystemID)))
				{// if such QA task have been passed the due date 
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "009");
					return ok(result.toJSONString());
				}
				if( !S.CheckQAOwer(Integer.parseInt(QAPairID), Integer.parseInt(SystemID)) )
				{// if the user not create such QA task 
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "012");
				}
				S.DeleteQAPair(Integer.parseInt(QAPairID), Integer.parseInt(SystemID));
				result.put("Result", "true");
				result.put("QAID", "");
				result.put("Exception-Code", "000");
				return ok(result.toJSONString());
			}
			if( Operation.equals("Update"))
			{
				if(QAPairID==null || QAPairID.length() == 0 )
				{
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "019");
					return ok(result.toJSONString());
				}
				if( Question == null || Question.length() == 0 )
				{
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "019");
					return ok(result.toJSONString());
				}
				if( Answer == null || Answer.length() == 0 )
				{
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "019");
					return ok(result.toJSONString());
				}
				Student S = new Student();
				int AssignmentID = S.GetQATASKID(Integer.parseInt(QAPairID));
				// get the task that the QApair belong to 
				if( S.CheckPassDueDate(AssignmentID) && S.CheckNotSubmit(AssignmentID, Integer.parseInt(SystemID)))
				{// if such QA task have been passed the due date 
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "009");
					return ok(result.toJSONString());
				}
				if( !S.CheckQAOwer(Integer.parseInt(QAPairID), Integer.parseInt(SystemID)) )
				{// if the user not create such QA task 
					result.put("Result", "");
					result.put("QAID", "");
					result.put("Exception-Code", "012");
				}
				S.ModifyQAPair(Integer.parseInt(QAPairID), Integer.parseInt(SystemID), Question, Answer);
				result.put("Result", "true");
				result.put("QAID", "");
				result.put("Exception-Code", "000");
				return ok(result.toJSONString());
			}
			result.put("Result", "");
			result.put("QAID", "");
			result.put("Exception-Code", "019");
			return ok(result.toJSONString());
		}catch(Exception E)
		{
			JSONObject result = new JSONObject();
			result.put("Result", "");
			result.put("QAID", "");
			result.put("Exception-Code", "018");
			E.printStackTrace();
			return ok(result.toJSONString());
		}
	}
	/**
	 * URL: 	/SubmitTask/
	 * Method: 	POST
	 * Input:	Task-id
	 * @RETURN 
	 * 		Result 		if the Operation have been performed 
	 * 		Exception-Code:
	 * 			000:	success
	 * 			005:	user not login before using the system 
	 * 			008:	user have not been assigned such task
	 * 	  		009:	if the task have passed the due and been submitted
	 * 			019:	input parameter Error 
	 * */
	public Result SubmitTask()
	{
		JSONObject result= new JSONObject();
		Student S = new Student();
		String SystemID = session().get("SystemID");
		String TaskID	= request().body().asJson().get("Task-id").asText();
		if( SystemID == null || SystemID.length() == 0 )
		{//user not login before using the system 
			result.put("Result", "");
			result.put("Exception-Code", "005");
			return ok(result.toJSONString());
		}
		if( TaskID == null || TaskID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-Code", "019");
			return ok(result.toJSONString());
		}
		int TID = 0;
		int SID = 0;
		try{
			TID = Integer.parseInt(TaskID);
			SID = Integer.parseInt(SystemID);
		}catch(Exception E)
		{
			result.put("Result", "Parsing Integer Error");
			result.put("Exception-Code", "018");
			return ok(result.toJSONString());
		}
		//1. check if the task have been assigned to user 
		if( !S.CheckAssign(TID, SID))
		{
			result.put("Result", "");
			result.put("Exception-Code", "008");
			return ok( result.toJSONString());
		}
		//2. check if it have passed the due date and user submitted the assignment
		try{
			if( S.CheckPassDueDate(TID) && S.CheckNotSubmit(TID, SID))
			{
				result.put("Result","");
				result.put("Exception-Code", "009");
				return ok(result.toJSONString());
			}
			S.SubmitTask(TID, SID);
			result.put("Result", "reture");
			result.put("Exception-Code", "000");
			return ok(result.toJSONString());
		}catch( Exception E)
		{
			E.printStackTrace();
			result.put("Result", "");
			result.put("Exception-Code", "018");
			return ok( result.toJSONString());
		}
	}
	/**
	 * URL:		/EvaluationPairList/
	 * Method:	POSt
	 * Input:	Task-id
	 * @return
	 * 		Amount	:	the number of QA pair that have been evaluated 
	 * 		pairlist:	a json array with following 
	 * 		Exception-Code:
	 * 		000:	success
	 * 		005:	user not login before using the system
	 * 		008:	user have not been assigned such task
	 * 		010:	the task is not a peer evaluation task 
	 * 		019:	input Parameter Error
	 */
	public Result PEPairList()
	{
		Student S = new Student();
		JSONObject result = new JSONObject();
		String SystemID = session().get("SystemID");
		String TaskID	= request().body().asJson().get("Task-id").asText();
		if( SystemID == null || SystemID.length() == 0 )
		{
			result.put("Amount", "");
			result.put("pairlist", "");
			result.put("Exception-Code", "005");
			return ok(result.toJSONString());
		}
		if( TaskID == null || TaskID.length() == 0 )
		{
			result.put("Amount", "");
			result.put("pairlist", "");
			result.put("Exception-Code", "019");
			return ok(result.toJSONString());
		}
		int TID,SID;
		try{
			TID = Integer.parseInt(TaskID);
			SID = Integer.parseInt(SystemID);
			if( !S.CheckPeerTask(TID))
			{// the task is not a peer evaluation task 
				result.put("Amount", "");
				result.put("pairlist", "");
				result.put("Exception-Code", "010");
				return ok(result.toJSONString());
			}
			if( !S.CheckAssign(TID, SID))
			{// the task is not assigned to user 
				result.put("Amount", "");
				result.put("pairlist", "");
				result.put("Exception-Code", "008");
				return ok(result.toJSONString());
			}
			int 		Amount = S.GetPeerEvaluationAmmount(TID, SID);
			JSONArray 	Pairlist = S.GetPeerEvaluationList(TID, SID);
			result.put("Amount", Amount);
			result.put("pairlist", Pairlist);
			result.put("Exception-Code", "000");
			return ok(result.toJSONString());
		}catch( Exception E)
		{
			result.put("Amount", "");
			result.put("pairlist", "");
			result.put("Exception-Code", "018");
			E.printStackTrace();
			return ok(result.toJSONString());
		}
	}
	/**
	 * URL: 	/PEoperation/
	 * Method: 	POST
	 * Input:	
	 * 			PEID	The ID of PE pair
	 * 			Comment	The Comment associated with Current ID
	 * 			Grade 	The Grade associated with Current ID
	 * Output
	 * 			Result		true if successfully update 
	 * 			Exception-Code:
	 * 			000:	successfully update
	 * 			005:	user not login before using the system
	 * 			008:	the user have not been assigned such task
	 * 			009:	if the task have passed the due and been submitted 
	 * 			012:	the PEpair is not belong to user
	 * 			019:	input parameter Error			
	 */
	public Result PEOperation()
	{
		JSONObject result = new JSONObject();
		String PairID = request().body().asJson().asText();
		String Comment= request().body().asJson().asText();
		String Grade  = request().body().asJson().asText();
		String SystemID= session().get("SystemID");
		if( SystemID == null || SystemID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-Code", "005");
		}
		if( PairID == null || PairID.length() == 0 )
		{
			result.put("Result", "");
			result.put("Exception-Code", "019");
			return ok(result.toJSONString());
		}
		if( Comment== null ||Comment.length() == 0 )
		{
			Comment="";
		}
		if( Grade == null ||Grade.length() == 0 )
		{
			Grade = "A";
		}
		try{
			int EvaluationPairID = Integer.parseInt(PairID);
			int SID = Integer.parseInt(SystemID);
			Student S = new Student();
			int PETaskID = S.GetPeerTaskID(EvaluationPairID);
			if( !S.CheckPEOwner(EvaluationPairID, SID) )
			{// the PE pair not created by user 
				result.put("Result", "");
				result.put("Exception-Code", "012");
				return ok(result.toJSONString());
			}
			if( PETaskID == -1 )
			{// the Peer evluation pair not exist 
				result.put("Result", "");
				result.put("Exception-Code", "020");
				return ok(result.toJSONString());
			}
			if( S.CheckNotSubmit(PETaskID, SID) && S.CheckPassDueDate(PETaskID) )
			{// such assignment have passed the due date 
				result.put("Result", "");
				result.put("Exception-Code", "009");
				return ok(result.toJSONString());
			}
			S.ModifyEvaluationPair(EvaluationPairID, SID,Comment, Grade);
			result.put("Result", "");
			result.put("Exception-Code", "000");
			return ok(result.toJSONString());
		}catch(Exception E)
		{
			E.printStackTrace();
			result.put("Result", "");
			result.put("Exception-Code", "018");
			return ok(result.toJSONString());
			
		}
	}
}
