package Memory_Structure;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Physic_Structure.DatabaseHandle;

/*TA is a special kind of user
 * who has the authorization to  
 * 1) post Task for a class that he serve as TA
 * 2) view all the student's homework on the class that he serve as TA
 * 3) give commence to a particular QA pair*/

public class TA 
{
	/**
	 * this function is used to check if the User is serve as a TA for given class
	 * @param
	 * 		SystemID: the System ID of user 
	 * @param
	 * 		ClassID : the System ID of class
	 * @return
	 * 		true:	  if the user serve as the TA of such class
	 * @return
	 * 		false:	  if the user not serve as the TA of such class
	 * @exception
	 * 		Mysql error 
	 * */ 
	public boolean IsTeachSuchClass(int SystemID , int ClassID) throws Exception
	{
		String Query = "Select * from Userlist,Grader where Userlist.CampusID=Grader.CampusID and Userlist.id=? and Grader.ClassID=?";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, SystemID);
		pstmt.setInt(2, ClassID);
		ResultSet rs = pstmt.executeQuery();
		if( rs.next() )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/***
	 * this function is used to check if the user is serve as a TA for the assignment that current task belong to 
	 * @param 
	 * 		SystemID	the system id of user
	 * @param 
	 * 		TaskID		the system id of task
	 * @return
	 * 		true		if the user serve as a TA for given task
	 * @return
	 * 		false		otherwise 
	 * @throws 
	 * 		Exception	mysql error 
	 */
	public boolean IsGradeSuchTask(int SystemID, int TaskID) throws Exception
	{
		String Query = 
				  "	SELECT * "
				+ " FROM Grader,Userlist,Assignment "
				+ "	WHERE "
				+ "		Grader.CampusID = Userlist.CampusID and	"
				+ "		Grader.ClassID=Assignment.ClassID and 	"
				+ "		Userlist.id = ? and "
				+ "		Assignment.id= ?";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, SystemID);
		pstmt.setInt(2, TaskID);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next() )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/***
	 * this function is used to check if the user is serve as a TA for view and write comment to such QApair 
	 * @param 
	 * 		SystemID	the user's system id 
	 * @param 
	 * 		QApairID	the QA pair's system id 
	 * @return
	 * 		true		if the user is a TA with autherization to view and write comments 
	 * @return
	 * 		false		if the user is not a TA with autherization 
	 * @throws 
	 * 		Exception	mysql Error
	 */
	public boolean IsGradeSuchPair(int SystemID,int QApairID) throws Exception
	{
		String Query = 
					"	Select * "
				+ 	"	From Grader,Userlist,Assignment,QA_Pair"
				+ 	"	WHERE "
				+ 	"		Userlist.id = ? and "
				+ 	"		QA_Pair.id = ?	and "
				+ 	"		Userlist.CampusID = Grader.CampusID and "
				+ 	"		Grader.ClassID = Assignment.ClassID and "
				+ 	"		QA_Pair.AssignmentID = Assignment.id";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, SystemID);
		pstmt.setInt(2, QApairID);
		ResultSet rs = pstmt.executeQuery();
		if( rs.next() )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/***
	 * this function is used to list all the assignment that belong to given class 
	 * @param 
	 * 		SystemID	the system ID of user
	 * @param 
	 * 		ClassID		the system id of class
	 * @return
	 * 		a json array of Task basic information with following segment 
	 * 		[Task-id, Task-title, Task-DES, Task-Due-Date ]
	 * @throws 
	 * 		Exception	Mysql Exception 
	 * 		020			the user not server as TA for given class 
	 * 		
	 */
	public JSONArray ListTeachTask(int SystemID, int ClassID) throws Exception
	{
		JSONArray Result = new JSONArray();
		if( !IsTeachSuchClass(SystemID,ClassID))
		{
			throw new Exception("the user "+SystemID+" is not a TA for given "+ClassID+" class");
		}
		String Query = 
					"	SELECT 	id,Title,Description,DueDate "
				+ 	"	FROM   	Assignment "
				+ 	"	WHERE	ClassID = ?";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, ClassID);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next() )
		{
			JSONObject Tuple = new JSONObject();
			Tuple.put("Task-id", rs.getInt(1));
			Tuple.put("Task-Title", rs.getString(2));
			Tuple.put("Task-DES", rs.getString(3));
			Tuple.put("Task-DueDate", rs.getString(4));
			Result.add(Tuple);
		}
		return Result;
	}
	/**this function is used to tall the finish rate of a task that was working on by given student 
	 * @param
	 * 		TaskID 	the system id of assignment 
	 * @param
	 * 		StudentUserID 	the system id of user
	 * @return 
	 * 		the Finish Ratio of such task 
	 */
	private float GetFinishRate(int TaskID, int StudentUserID) throws Exception 
	{
		String QueryQATask = "Select MinumalNumber from QA_Task where AssignmentID = ?";
		// Query the total number of QA pair the user need to given 
		String QueryPETask = "Select count(id) from PeerEvaluation_Pair where UserID = ? and AssignmentID = ?";
		// Query the total number of QA pair the user need to grade 
		String QueryQAProcess = "Select count(id) from QA_Pair where UserID=? and AssignmentID=?";
		// Query the total numbser of QA pair the user have already give 
		String QueryPEProcess = "Select count(id) from PeerEvaluation_Pair where UserID = ? and AssignmentID = ? and Grade is not null";
	

		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(QueryQATask);
		pstmt.setInt(1, TaskID);
		ResultSet rs1 = pstmt.executeQuery();
		ResultSet rs2;
		if( rs1.next() )
		{
			int QAProcess;
			int QATotal = rs1.getInt(1);
			pstmt = DH.Conn.prepareStatement(QueryQAProcess);
			pstmt.setInt(1, StudentUserID);
			pstmt.setInt(2, TaskID);
			rs2 = pstmt.executeQuery();
			if( rs2.next() )
			{
				QAProcess = rs2.getInt(1);
				return ( QAProcess*1.0f / QATotal);
			}else
			{
				return 0;
			}
			
		}
		
		pstmt = DH.Conn.prepareStatement(QueryPETask);
		pstmt.setInt(1, StudentUserID);
		pstmt.setInt(2, TaskID);
		rs1 = pstmt.executeQuery();
		if( rs1.next() )
		{
			int PETotal= rs1.getInt(1);
			int PEProcess ;
			pstmt = DH.Conn.prepareStatement(QueryPEProcess);
			pstmt.setInt(1, StudentUserID);
			pstmt.setInt(2, TaskID);
			rs2 = pstmt.executeQuery();
			if( rs2.next() )
			{
				PEProcess = rs2.getInt(1);
				return ( PEProcess*1.0f / PETotal);
			}
			else
			{
				return 0;
			}
		}
		return 0;
	}
	/**
	 * this function is used to list all the student who have been assigned the given task 
	 * @param 
	 * 		SystemID the system ID of TA 
	 * @param 
	 * 		TaskID	 the system id of task 
	 * @return
	 * 		a json Array with following segment 
	 * 		[StudentID, Finish-Rate , Submit-Date ]
	 * @throws Exception
	 */
	public JSONArray ListStudentProcedure(int SystemID,int TaskID) throws Exception
	{
		DatabaseHandle DH = new DatabaseHandle();
		if( !IsGradeSuchTask(SystemID,TaskID))
		{
			throw new Exception("this user "+SystemID+" is not TA to grader Task "+TaskID);
		}
		String Query = 
					"	SELECT 	Userlist.CampusID,Submission.SubmitDate,Userlist.id "
				+	"	FROM 	Submission	,	Userlist "
				+ 	"	WHERE 	Submission.AssignmentID = ? and Submission.CampusID= Userlist.CampusID";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, TaskID);
		ResultSet rs = pstmt.executeQuery();
		JSONArray Result = new JSONArray();
		while( rs.next() )
		{
			JSONObject tuple = new JSONObject();
			int StudentID = rs.getInt(1);
			int StudentSystemID= rs.getInt(3);
			String SubmitDate= rs.getString(2);
			float processratio= this.GetFinishRate(TaskID, StudentSystemID);
			tuple.put("Student-id", StudentID);
			tuple.put("SubmitDate", SubmitDate);
			tuple.put("Finish-Rate", processratio);
			Result.add(tuple);
		}
		return Result;
		
	}
	/***
	 * this function will list all the comment associated with given QApair 
	 * @param 
	 * 			QApairID 	the QA pair ID 
	 * @return
	 * 			the JSONArray that each element made by following segment 
	 * 			[Grader-ID , Grade , Comments]
	 * @throws 
	 * 			Exception	Mysql Error
	 */
	private JSONArray ListAllComment(int QApairID) throws Exception
	{
		JSONArray Result = new JSONArray();
		String Query = 
					"	SELECT 	PE.Comments,PE.Grade, U.CampusID 	"
				+ 	"	FROM	PeerEvaluation_Pair PE, Userlist U	"
				+ 	"	WHERE	PE.QAPairID = ? and PE.UserID = U.id";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt =DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, QApairID);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next() )
		{
			JSONObject tuple = new JSONObject();
			String Comments = rs.getString(1);
			String Grade	= rs.getString(2);
			int	   Grader	=rs.getInt(3);
			tuple.put("Grader-ID", Grader);
			tuple.put("Grade", Grade);
			tuple.put("Comments", Comments);
			Result.add(tuple);
		}
		return Result;
	}
	/**this function will list the Information of a given QAPair 
	 * @param
	 * 		QAPairID the system ID of QA pair
	 * @return
	 * 		JSONObject with 
	 * 			Owner 			the campus ID of student who create such QA pair
	 * 			Question		the Question associated with QA pair 
	 * 			Answer			the Answer associated with QA pair
	 * 			Grading-Result	a json Array made by Commence towards current QA pair 
	 */
	private JSONObject ListQApair(int QAPairID) throws Exception
	{
		String Query = "Select QA_Pair.Question, QA_Pair.Answer , Userlist.CampusID from QA_Pair,Userlist where QA_Pair.id = ? and QA_Pair.UserID = Userlist.id";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, QAPairID);
		ResultSet rs = pstmt.executeQuery();
		if( rs.next() )
		{
			JSONObject Result= new JSONObject();
			Result.put("Owner", rs.getInt(3));
			Result.put("Question", rs.getString(1));
			Result.put("Answer", rs.getString(2));
			JSONArray Comment= ListAllComment(QAPairID);
			Result.put("Grading-Result", Comment);
			return Result;
		}
		else
		{
			throw new Exception("Cannot find " + QAPairID);
		}
	}
	/***
	 * this function is used to view a given student process on a given task 
	 * if the Task type is a QA task, it will list all the QA pair that belong to QA task and all the Comment associated with it 
	 * if the Task type is a Peer task, it will retrace the QA pair that been evaluated by given student and list all the comment associated with it  
	 * @param 
	 * 		TaskID 				the system id of Task
	 * 		SystemID			the system id of TA
	 * 		StudentCampusID 	the CampusID of Student 
	 * @return
	 * 		JSONArray contain all the QA pair associated with current task and current user
	 * 			Owner 			the campus id of student who create such QA pair
	 * 			Question		the Question associated with QA pair
	 * 			Answer			the Answer associated with QA pair
	 * 			Grading-Result	a json array made by commence towards current QA pair 
	 */
	public JSONArray CheckStudentProcedure(int TaskID, int SystemID, int StudentCampusID) throws Exception 
	{
		String QATaskQuery = 
					"	SELECT 	QA_Pair.id "
				+ 	"	FROM 	QA_Pair, Userlist "
				+ 	"	WHERE 	QA_Pair.AssignmentID = ? and Userlist.CampusID = ? and Userlist.id = QA_Pair.UserID  ";
		//in case Current Task is a QA task, the query result is the QA_Pair ID that create by given user for given task ;
		String PETaskQuery = 
					"	SELECT 	QAPairID "
				+ 	"	FROM	PeerEvaluation_Pair, Userlist "
				+ 	"	WHERE	PeerEvaluation_Pair.AssignmentID = ? and Userlist.CampusID= ? and Userlist.id = PeerEvaluation_Pair.UserID";
		// in case Current Task is a Peer Evaluation Task, the Query result is the QA_Pair that been Evaluated by given user for given task 
		if( !IsGradeSuchTask(SystemID,TaskID))
		{
			throw new Exception("the User " +SystemID +" not grade "+TaskID);
		}
		JSONArray Result = new JSONArray();
		DatabaseHandle DH = new DatabaseHandle();

		PreparedStatement QA_pstmt = DH.Conn.prepareStatement(QATaskQuery);
		QA_pstmt.setInt(1, TaskID);
		QA_pstmt.setInt(2, StudentCampusID);
		System.out.println(QA_pstmt);
		ResultSet QA_Result = QA_pstmt.executeQuery();
		while( QA_Result.next() )
		{//current Task is a QA task 
			int QA_PairID = QA_Result.getInt(1);
			JSONObject QA_PairInfo = ListQApair(QA_PairID);
			Result.add(QA_PairInfo);
		}
		PreparedStatement PE_pstmt = DH.Conn.prepareStatement(PETaskQuery);
		PE_pstmt.setInt(1, TaskID);
		PE_pstmt.setInt(2, StudentCampusID);
		System.out.println(PE_pstmt);
		ResultSet PE_Result = PE_pstmt.executeQuery();
		while( PE_Result.next() )
		{
			int QA_PairID = PE_Result.getInt(1);
			JSONObject QA_PairInfo = ListQApair(QA_PairID);
			Result.add(QA_PairInfo);
		}
		return Result;
	}
	public boolean VerifiyQApair(int QAPairID , int SystemID) throws Exception 
	{
		String Verify = 
				"	SELECT 	* "
			+	"	FROM 	"
			+ 	"			Assignment,"
			+ 	"			QA_Pair,"
			+ 	"			Grader,"
			+ 	"			Userlist"
			+ 	"	WHERE "
			+ 	"			Userlist.id = ? and"
			+ 	" 			QA_Pair.id= ? and"
			+ 	" 			Userlist.CampusID = Grader.CampusID and"
			+ 	" 			QA_Pair.AssignmentID = Assignment.id and "
			+ 	"			Assignment.ClassID = Grader.ClassID";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(Verify);
		pstmt.setInt(1, SystemID);
		pstmt.setInt(2, QAPairID);
		ResultSet rs = pstmt.executeQuery();
		if( rs.next())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/***
	 * this function is used to give comments to a student QA pair 
	 * if the TA is a grader to such QA pair */
	void AddComment(int QApairID , int SystemID, String Comments, String Grade) throws Exception
	{
		//1) Verify if the TA acturally Teach the QApair's Class 
		if(! VerifiyQApair(QApairID,SystemID))
		{
			throw new Exception("User "+ SystemID+" not a TA for QA pair "+QApairID);
		}
		String Insert="Insert Into PeerEvaluation_Pair (UserID,QAPairID,Comments,Grade) value(?,?,?,?)";
		DatabaseHandle DH = new DatabaseHandle();
		PreparedStatement pstmt = DH.Conn.prepareStatement(Insert);
		pstmt.setInt(1, SystemID);
		pstmt.setInt(2, QApairID);
		pstmt.setString(3, Comments);
		pstmt.setString(4,""+ Grade.charAt(0));
		pstmt.executeUpdate();
	}
	
}
