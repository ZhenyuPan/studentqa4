package Memory_Structure;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Physic_Structure.DatabaseHandle;

/*As currently I didn't know how to bonding a memory structure with a particular session the operation here are merely query to database*/
public class Student {
	//the task 	that assigned to current student  
	//the class	that current student enrolled in 
	DatabaseHandle DH = new DatabaseHandle();
	//get the class basic information that given student have enrolled
	String GetDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String strDate = sdf.format(now);
		return strDate;
	}
	public boolean CheckPEOwner(int PEPairID , int StudentID ) throws Exception
	{
		String Query = "Select * from PeerEvaluation_Pair where id = ? and UserID = ?";
		PreparedStatement pstmt= DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, PEPairID);
		pstmt.setInt(2, StudentID);
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
	/**
	 * given the Peer Evaluation Pair ID and return the Peer Evaluation Task that current pair belong to 
	 * @param 
	 * 		PEPairID the system id of Peer Evaluation Pair
	 * @return
	 * 		the Peer Evaluation Task ID
	 * @throws 
	 * 		Exception SQL error
	 */
	public int 	GetPeerTaskID( int PEPairID) throws Exception
	{
		String Query = "Select AssignmentID from PeerEvaluation_Pair where id = ?";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, PEPairID);
		ResultSet rs = pstmt.executeQuery();
		if( rs.next() )
		{
			return rs.getInt(1);
		}
		else
		{
			return -1;
		}
	}
	/**
	 * check if the task is a peer Evaluation Task 
	 * @param 
	 * 			TaskID		the system ID of Task 
	 * @return
	 * 			true if the given task is a peer evaluation task 
	 * @throws 
	 * 			Exception	Mysql Error 
	 */
	public boolean CheckPeerTask(int TaskID) throws Exception 
	{
		String Query ="Select * from PeerEvaluation_Task where AssignmentID = ?";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, TaskID);
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
	/**
	 * this function is used to check if the QA pair do created by user
	 * @param QAPairID 	the system id of QA pair
	 * @param SystemID 	the system id of user
	 * @return	
	 * 			true if user do create such QA pair
	 * 			false if not 
	 * @throws Exception
	 * 			SQL error
	 */
	public boolean CheckQAOwer(int QAPairID, int SystemID) throws Exception
	{
		String Query="Select * from QA_Pair where id=? and UserID=?";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1,QAPairID);
		pstmt.setInt(2, SystemID);
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
	/**
	 * this function is used to check if the task that assigned to given user have been submitted 
	 * @param TaskID	the system id of task
	 * @param SystemID	the system id of user
	 * @return	
	 * 			true	if the Task have been submitted 
	 * 			false	if the Task have not been submitted 
	 * @throws Exception 
	 * 			not assign task 
	 * 			SQL Error
	 * 			...
	 */
	public boolean CheckNotSubmit( int TaskID, int SystemID) throws Exception
	{
		String Query = "Select S.Submitted is not null as submitted from Submission S,Userlist U where S.AssignmentID=? and U.id=? and S.CampusID=U.CampusID";
			PreparedStatement pstmt =DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, TaskID);
			pstmt.setInt(2, SystemID);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() )
			{
				if( rs.getBoolean(1))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			throw new Exception("Not Assign Task");
	}
	/**
	 * this function return the Assignment ID of Given QApair
	 * @param QAPairID	the system id of QA paird
	 * @return
	 * 			the System ID of task
	 * @throws 
	 * 		Exception
	 * 			SQL Error
	 * 			Such ID not QA pair
	 */
	public int GetQATASKID(int QAPairID) throws Exception
	{
		String Query ="Select AssignmentID from QA_Pair where id = ?";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, QAPairID);
		ResultSet rs = pstmt.executeQuery();
		if( rs.next() )
		{// get the Assigment ID that Current QA Pair belong to
			return rs.getInt(1);	
			// check the corresponding Assigment have passed the due date 
		}
		else
		{
			throw new Exception("Such QApair ID not exist");
		}
	
	}
	/**
	 * this function check if the given task have passed the due date
	 * @param TaskID	the system id of the task
	 * @return 
	 * 		true 	if the task passed the due date
	 * 		false	if not
	 */
	public boolean CheckPassDueDate(int TaskID) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
	    String strDate = sdf.format(now);
	    //StrDate is current Date in Year-month-date form
		String Query="Select * from Assignment where id = ? and DueDate < ?";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, TaskID);
		pstmt.setString(2, strDate);
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
	/**
	 * this function check if the given task is a QA task
	 * @param TaskID:	the given Task id
	 * @return 
	 * 		true if the given task is a QA task
	 * 		false otherwise
	 */
	public boolean CheckQAType(int TaskID)
	{
		String Query ="select * from QA_Task where AssignmentID = ?";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, TaskID);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() )
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		return false;
	}
	/**
	 * this function is used to check if the task have been assigned to user 
	 * @param TaskID	the task's system id
	 * @param SystemID	the user's system id
	 * @return 
	 * 		true if the task have been assign to user 
	 * 		false if not
	 */
	public boolean CheckAssign(int TaskID,int SystemID)
	{
		String Query = "select * from Submission S,Userlist U where S.AssignmentID = ? and S.CampusID = U.CampusID and U.id = ?";
		try{
			PreparedStatement pstmt =DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, TaskID);
			pstmt.setInt(2, SystemID);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() )
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		return false;
	}
	/**
	 * @function: Check if current user enrolled in given class
	 * @param	ClassID: the id of class
	 * @param 	SystemID: the id of user 
	 * @return 	
	 * 			true	if the user enrolled in given class
	 * 			false	if the user not enrolled in given class
	 * */
	public boolean CheckEnroll(int ClassID,int SystemID)
	{
		String Query = "SELECT * FROM Enrollment E,Userlist U WHERE E.CampusID=U.CampusId and E.ClassID=? and U.id= ?";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, ClassID);
			pstmt.setInt(2, SystemID);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() )
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception E)
		{
			System.err.println("Memory_Structure.Student.CheckEnroll Error");
			E.printStackTrace();
			return false;
		}
	}
	/**
	 * @function:
	 * 		list all the activated class that associated with current user 
	 * @param
	 * 		SystemID: current student's system id
	 * 		Detail  : if display the detail information of current user 
	 * 		aspect 	: enrolled-classlist, teached-classlist 
	 * @return
	 * 		the JSONArray which indicated the Course information that current user enrolled in 
	 * */
	public JSONArray GetClassList(int SystemID,boolean Detail,ClassAspect aspect)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
	    String strDate = sdf.format(now);
	    //StrDate is current Date in Year-month-date form
	    String Query="";
	    PreparedStatement pstmt;
	    ResultSet Rs;
	    switch(aspect )
	    {
	    case Enroll:
	    	JSONArray Enrolled = new JSONArray();
			Query=
			" SELECT C.id, Course.Title"
			+ 	" FROM Enrollment E, Class C, Course , Userlist U "
			+   " WHERE "
			+ 	"		U.id = ? and "
			+ "			E.CampusID=U.CampusID and"
			+ "			C.id=E.ClassID and C.CourseID = Course.id and "
			+ "			C.BeginTime < ? and C.EndTime > ?";
			// this query will list all the activated class that current user enrolled
			try{
				pstmt = DH.Conn.prepareStatement(Query);
				pstmt.setInt(1, SystemID);
				pstmt.setString(2, strDate);
				pstmt.setString(3, strDate);
				Rs = pstmt.executeQuery();
				while(Rs.next() )
				{
					JSONObject 	Class 	= new JSONObject();
					int 		ClassID = Rs.getInt(1);
					String 		Title	= Rs.getString(2);
					Class.put("Class-id", ClassID);
					Class.put("Class-Title", Title);
					//add such Class into JSON
					if(Detail == true)
					{
						JSONArray TaskList = this.GetAssignmentList(ClassID, SystemID);
						Class.put("Task", TaskList);
					}
					Enrolled.add(Class);
				}
				return Enrolled;
			}catch(Exception E)
			{
				E.printStackTrace();
				return null;
				//error case return null
			}
	    case Grade:
	    	Query=
	    	" SELECT C.id, Course.Title"
	    	+ 	"	FROM grader G, Class C , Course , Userlist U "
	    	+ 	"   WHERE "
	    	+	"		U.id = ? and "
	    	+ 	"		G.CampusID = U.CampusID and"
	    	+ 	"		C.id = G.ClassID and"
	    	+ 	"		C.CourseID = Course.id and "
	    	+	"		C.BeginTime < ? and C.EndTime > ?";
	    	// this query will list all the activated class that current user grade
	    	try{
	    		JSONArray Grade= new JSONArray();
	    		pstmt = DH.Conn.prepareStatement(Query);
	    		pstmt.setInt(1, SystemID);
	    		pstmt.setString(2, strDate);
	    		pstmt.setString(3, strDate);
	    		Rs = pstmt.executeQuery();
	    		while( Rs.next() )
	    		{
	    			JSONObject 	Class 	= new JSONObject();
					int 		ClassID = Rs.getInt(1);
					String 		Title	= Rs.getString(2);
					Class.put("Class-id", ClassID);
					Class.put("Class-Title", Title);
					if(Detail == true)
					{
						TA ta = new TA();
						JSONArray TaskList = ta.ListTeachTask(SystemID, ClassID);
						Class.put("Task", TaskList);
					}
					Grade.add(Class);
	    		}
	    		return Grade;
	    	}catch(Exception E)
	    	{
	    		E.printStackTrace();
	    		return null;
	    	}
	    case Teach:
	    	Query=
	    	" SELECT C.id,Course.Title"
	    	+	"	FROM Class C, Course , Userlist U "
	    	+ 	"	WHERE "
	    	+ 	"			C.InstructorID = ? and"
	    	+ 	"			C.CourseID = Course.id and "
	    	+ 	"			C.BeginTime < ? and C.EndTime > ?";
	    	//this query will list all the active class that current user teaches
	    	try{
	    		JSONArray Teach=new JSONArray();
	    		pstmt = DH.Conn.prepareStatement(Query);
	    		pstmt.setInt(1, SystemID);
	    		pstmt.setString(2, strDate);
	    		pstmt.setString(3, strDate);
	    		Rs = pstmt.executeQuery();
	    		while( Rs.next())
	    		{
	    			JSONObject Class = new JSONObject();
	    			int ClassID		 = Rs.getInt(1);
	    			String Title	 = Rs.getString(2);
	    			Class.put("Class-id", ClassID);
	    			Class.put("Class-Title", Title);
	    			/*
					if(Detail == true)
					{
						JSONArray TaskList = this.GetAssignmentList(ClassID, SystemID);
						Class.put("Task", TaskList);
					}
					*/
	    			Teach.add(Class);
	    		}
	    		return Teach;
	    	}catch(Exception E)
	    	{
	    		E.printStackTrace();
	    		return null;
	    	}
	    }
		return null;
		
	}
	
	/**
	 * @function:
	 * 		list all the task that assigned to current user belong to given class 
	 * 		example:(a task belong to current class but not assign to user)
	 * 			user not submit his QA task yet, so he couldn't been assign peer evaluation task of corresponding class
	 * @param 
	 * 		ClassID:
	 * 			the class current User enrolled 
	 * @param 
	 * 		SystemID:
	 * 			the user id 
	 * @return
	 * 		the JSONArray the contain all the assignment that belong to given class and assigned to user 
	 * @comment
	 * 		not finished 
	 */
	public JSONArray GetAssignmentList(int ClassID, int SystemID)
	{
		JSONArray Result = new JSONArray();
		String QueryForQATask=
					" SELECT A.id,A.Title,A.Description,A.DueDate,S.Submitted is not null as Submitted,\"QA Task\" as TaskType , QA.MinumalNumber as QATaskVolumn"
				+ 	" FROM Assignment A, QA_Task QA, Submission S , Userlist U "
				+   " WHERE "
				+ 	"	U.CampusID = S.CampusID and "
				+ 	"	S.AssignmentID = A.id   and "
				+ 	"	QA.AssignmentID= A.id   and "
				+ 	"	U.id=? and A.ClassID=?";
		String QueryForPETask=
					"	SELECT A.id as taskid,A.Title,A.Description,A.DueDate,S.Submitted is not null as Submitted,\"PE Task\" as TaskType,count( PEP.QAPairID) as PETaskvolumn "
				+ 	"	FROM Assignment A, PeerEvaluation_Task PE, Submission S, Userlist U,PeerEvaluation_Pair PEP "
				+ 	"	WHERE "
				+ 	"		U.id = ? 	and	"
				+ 	"		A.ClassID=? and	"
				+ 	"	    A.id = PE.AssignmentID and"
				+ 	"	    S.AssignmentID = A.id and"
				+ 	"	    U.CampusID= S.CampusID and "
				+ 	"		PEP.AssignmentID = A.id and "
				+ 	"		PEP.UserID = U.id"
				+ 	"	group by taskid";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(QueryForQATask);
			pstmt.setInt(1, SystemID);
			pstmt.setInt(2, ClassID);
			ResultSet Rs = pstmt.executeQuery();
			while( Rs.next() )
			{
				JSONObject Assignment=new JSONObject();
				Assignment.put("Task-id", Rs.getInt(1));
				Assignment.put("Task-Title", Rs.getString(2));
				Assignment.put("Task-Des", Rs.getString(3));
				Assignment.put("Task-DueDate", Rs.getString(4));
				Assignment.put("Task-Submitted", Rs.getInt(5));
				Assignment.put("Task-Type", Rs.getString(6));
				Assignment.put("QA-Task-Volumn", Rs.getInt(7));
				Assignment.put("PE-Task-Volumn", "");
				Result.add(Assignment);
			}
			pstmt = DH.Conn.prepareStatement(QueryForPETask);
			pstmt.setInt(1, SystemID);
			pstmt.setInt(2, ClassID);
			Rs = pstmt.executeQuery();
			while( Rs.next() )
			{
				JSONObject Assignment=new JSONObject();
				Assignment.put("Task-id", Rs.getInt(1));
				Assignment.put("Task-Title", Rs.getString(2));
				Assignment.put("Task-Des", Rs.getString(3));
				Assignment.put("Task-DueDate", Rs.getString(4));
				Assignment.put("Task-Submitted", Rs.getInt(5));
				Assignment.put("Task-Type", Rs.getString(6));
				Assignment.put("QA-Task-Volumn", "");
				Assignment.put("PE-Task-Volumn", Rs.getInt(7));
				Result.add(Assignment);
			}
			return Result;
		}catch(Exception E)
		{
			System.err.println("Error in Memory_Structure.Student.AssignmentInform");
			E.printStackTrace();
		}
		return null;
	}
	/**
	 * @function
	 * 		list all the Commence and Grade towards given QApair
	 * @param
	 * 		QAPairID : the ID of Question Answer Pair
	 * @return
	 * 		the Commence List toward such QA pair
	 * */
	public JSONArray CommentsList(int QAPairID)
	{
		JSONArray Result = new JSONArray();
		String Query ="Select Comments,Grade  from PeerEvaluation_Pair where QAPairID = ?";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, QAPairID);
			ResultSet RS = pstmt.executeQuery();
			while( RS.next() )
			{
				JSONObject GradingResult= new JSONObject();
				GradingResult.put("Commence", RS.getString(1));
				GradingResult.put("Grade", RS.getString(2));
				Result.add(GradingResult);
			}
			return Result;
		}catch(Exception E)
		{
			System.err.println("Error in Student.CommentsList");
			E.printStackTrace();
			return null;
		}
	}
	/**
	 * @function 
	 * 		list all the QA pair that belong to given task and created by user 
	 * @param 
	 * 		AssignemntID
	 * @param 
	 * 		SystemID
	 * @return
	 * 		the JSONArray that contain all the QA pair that belong to given QA task 
	 * 			[QAID,Question,Answer,PeerEvaluationResult]
	 */
	public JSONArray GetQAPairList(int AssignemntID,int SystemID)
	{
		JSONArray Result = new JSONArray();
		String Query="SELECT id,Question,Answer FROM QA_Pair WHERE UserID=? and AssignmentID =?";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, SystemID);
			pstmt.setInt(2, AssignemntID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next() )
			{
				int QAPairID = rs.getInt(1);
				JSONArray Commence= this.CommentsList(QAPairID);
				String Question   = rs.getString(2);
				String Answer	  = rs.getString(3);
				JSONObject QApair = new JSONObject();
				QApair.put("QAID", QAPairID);
				QApair.put("Question", Question);
				QApair.put("Answer", Answer);
				QApair.put("PeerEvaluationResult",Commence);
				Result.add(QApair);
			}
			return Result;
		}catch(Exception E)
		{
			System.err.println("Error in Student.QAPairList");
			E.printStackTrace();
			return null;
		}
	}
	/**
	 * this function is used to get the maximum QA pair ID
	 * @return
	 */
	public int GetMaximumQAPair()
	{
		String Query="Select Max(id) from QA_Pair";
		try{
			Statement stmt =DH.Conn.createStatement();
			ResultSet rs= stmt.executeQuery(Query);
			if(rs.next() )
			{
				return rs.getInt(1);
			}
			else
			{
				return 0;
			}
		}catch(Exception E)
		{
			E.printStackTrace();
			return -1;
		}
	}
	/**
	 * Create a QA Pair
	 * @param AssignmentID	the system id of task
	 * @param SystemID		the system id of user
	 * @param Question		the new Question
	 * @param Answer		the new Answer
	 * @return
	 * 		the QApair ID 	if success created
	 * @throws Exception 
	 * 		if have some Error
	 */
	public int CreateQAPair(int AssignmentID,int SystemID,String Question,String Answer) throws Exception
	{
		//first check if the task have been assigned to user
		if( !this.CheckAssign(AssignmentID, SystemID))
		{
			throw new Exception("Task have not been assign to user");
		}
		//second check if the student have already submited it & check if the Assignment have passed the due date
		if( this.CheckPassDueDate(AssignmentID) && this.CheckNotSubmit(AssignmentID, SystemID))
		{// we not allow user to create more QA pair if the user submit his assignment and have passed due date for corresponding task
			throw new Exception("Passed the due date and user have submitted before");
		}
		int NewQAPairID = GetMaximumQAPair()+1;
		String UPdate = "Insert into QA_Pair(id,UserID,AssignmentID,Question,Answer) values(?,?,?,?,?)";
		PreparedStatement pstmt =DH.Conn.prepareStatement(UPdate);
		pstmt.setInt(1, NewQAPairID);
		pstmt.setInt(2, SystemID);
		pstmt.setInt(3, AssignmentID);
		pstmt.setString(4, Question);
		pstmt.setString(5, Answer);
		pstmt.executeUpdate();
		return NewQAPairID;

	}
	/**
	 * this function is used to Delete QA pair from System
	 * @param QApairID	the system id of QA pair
	 * @param SystemID	the system id of user
	 * @throws Exception
	 * 		the Corresponding Task have passed the due date and user have already submitted it
	 * 		the QApair id not own by given user
	 */
	public void DeleteQAPair(int QApairID, int SystemID) throws Exception
	{
		int AssignmentID = this.GetQATASKID(QApairID);
		//first check if the Assignment such QApair Belong to have been passed the duedate 
		if( this.CheckPassDueDate(AssignmentID) && this.CheckNotSubmit(AssignmentID, SystemID))
		{// we not allow user to create more QA pair if the user submit his assignment and have passed due date for corresponding task
			throw new Exception("Passed the due date and user have submitted before");
		}
		//Second Check if the QApair is created by given user
		if( !this.CheckQAOwer(QApairID, SystemID))
		{
			throw new Exception("the QApair is not own by given user");
		}
		String Delete="Delete from QA_Pair where id = ?";
		PreparedStatement pstmt =DH.Conn.prepareStatement(Delete);
		pstmt.setInt(1, QApairID);
		pstmt.executeUpdate();
	}
	/**
	 * this function is used to upadate QA pair in the system 
	 * @param 
	 * 		QApairID	the system id of QApair 
	 * @param 
	 * 		SystemID	the system id of user
	 * @param 
	 * 		Question	the new Question 
	 * @param 
	 * 		Answer		the new answer 
	 * @throws Exception
	 * 		the Corresponding Task have passed the due date and user have already submitted it 
	 * 		the QApair id not own by given user 
	 */
	public void ModifyQAPair(int QApairID, int SystemID, String Question,String Answer) throws Exception
	{
		int AssignmentID = this.GetQATASKID(QApairID);
		//first check if the Assignment such QApair Belong to have been passed the duedate 
		if( this.CheckPassDueDate(AssignmentID) && this.CheckNotSubmit(AssignmentID, SystemID))
		{// we not allow user to create more QA pair if the user submit his assignment and have passed due date for corresponding task
			throw new Exception("Passed the due date and user have submitted before");
		}
		//Second Check if the QApair is created by given user
		if( !this.CheckQAOwer(QApairID, SystemID))
		{
			throw new Exception("the QApair is not own by given user");
		}
		String Update="UPDATE QA_Pair SET Question = ?,Answer=? WHERE id=?";
		PreparedStatement pstmt= DH.Conn.prepareStatement(Update);
		pstmt.setString(1, Question);
		pstmt.setString(2,Answer);
		pstmt.setInt(3, QApairID);
		pstmt.executeUpdate();
	}
	/**
	 * this function is used to get the Campus ID of user 
	 * @param 
	 * 		SystemID the system id of user 
	 * @return
	 * 		the Campus ID of user 
	 */
	public int GetCampusID( int SystemID )
	{
		String Query ="Select CampusID from Userlist where id = ?";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, SystemID);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() )
			{
				int CampusID = rs.getInt(1);
				return CampusID;
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
		return -1;
	}
	/**
	 * this function is used to submit a perticular task for Student 
	 * @param 
	 * 		TaskID 		the system id of Task
	 * @param 
	 * 		SystemID	the system Id of user
	 * @throws Exception 
	 * 		1. the user have already submit his assigment and it have passed the due date 
	 * 		2. the user have not assigned such task 
	 */
	public void SubmitTask(int TaskID, int SystemID) throws Exception
	{
		String Update ="Upadate Submission Set Submitted = 1, SubmitDate = ? where AssignmentID = ? and CampusID = ?";
		if( !this.CheckAssign(TaskID, SystemID))
		{
			throw new Exception("User "+SystemID+" have not been assigned "+TaskID);
		}
		if(  this.CheckNotSubmit(TaskID, SystemID) && this.CheckPassDueDate(TaskID) )
		{
			throw new Exception("TaskID "+ TaskID +"have passed the due date and have alread submitted " );
		}
		PreparedStatement pstmt = DH.Conn.prepareStatement(Update);
		String Date = this.GetDate();
		int CampusID= this.GetCampusID(SystemID);
		pstmt.setString(1, Date);
		pstmt.setInt(2, TaskID);
		pstmt.setInt(3, CampusID);
		pstmt.executeUpdate();
	}
	/**
	 * by entering the peer evaluation task id and the user system id, tracing back the Peer Evaluation Task List that created by given user and belong to given task 
	 * @param 
	 * 		TaskID		the system id of task
	 * @param 
	 * 		SystemID	the system id of user 
	 * @return
	 * 		the JSONArray of PeerEvaluation Pair each pair is a JSON object with segment 
	 * 			PEID		The id of peer evaluation pair
	 * 			Question	The Question from evaluate target 
	 * 			Answer 		The answer from evaluate target
	 * 			Comment		The Comment given by user
	 * 			Grade		The grade given by user
	 * @throws Exception
	 * 		The given task is not a peer evaluation task 
	 * 		User have not been assigned such task
	 */
	public JSONArray GetPeerEvaluationList(int TaskID, int SystemID) throws Exception
	{
		if( !this.CheckAssign(TaskID, SystemID))
		{
			throw new Exception("User "+SystemID+" Not been assigned Task "+ TaskID);
		}
		if( !this.CheckPeerTask(TaskID))
		{
			throw new Exception("Task "+ TaskID + " is not a peer Evaluation Task");
		}
		String Query = 
					"	SELECT 	PE.id as PEID, QA.Question as Question , QA.Answer as Answer , PE.Comments as Comment , PE.Grade as Grade "
				+	"	FROM 	PeerEvaluation_Pair PE, QA_Pair QA "
				+ 	"	WHERE 	PE.AssignmentID = ? and PE.UserID= ? and PE.QAPairID = QA.id";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, TaskID);
		pstmt.setInt(2, SystemID);
		ResultSet rs = pstmt.executeQuery();
		JSONArray Result = new JSONArray();
		while( rs.next() )
		{
			JSONObject Tuple = new JSONObject();
			Tuple.put("PEID", rs.getInt(1));
			Tuple.put("Question", rs.getString(2));
			Tuple.put("Answer", rs.getString(3));
			Tuple.put("Comment", rs.getString(4));
			Tuple.put("Grade", rs.getString(5));
			Result.add(Tuple);
		}
		return Result;
	}
	/**
	 * by given the peer evaluation task id and the user system id, tracing back the ammount of QA pair that have already been graded
	 * @param
	 * 		TaskID 		the system id of Task 
	 * @param	
	 * 		SystemID 	the system id of user 
	 * @return 
	 * 		the integer tell how many QA pair have been evaluated 
	 */
	public int GetPeerEvaluationAmmount( int TaskID, int SystemID ) throws Exception 
	{
		if( !this.CheckAssign(TaskID, SystemID))
		{
			throw new Exception("User "+SystemID+" Not been assigned Task "+ TaskID);
		}
		if( !this.CheckPeerTask(TaskID))
		{
			throw new Exception("Task "+ TaskID + " is not a peer Evaluation Task");
		}
		String Query = 
					"	SELECT 	count(distinct PE.id)"
				+	"	FROM 	PeerEvaluation_Pair PE, QA_Pair QA "
				+ 	"	WHERE 	PE.AssignmentID = ? and PE.UserID= ? and PE.QAPairID = QA.id and PE.Comments is not null";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
		pstmt.setInt(1, TaskID);
		pstmt.setInt(2, SystemID);
		ResultSet rs = pstmt.executeQuery();
		if( rs.next() )
		{
			return rs.getInt(1);
		}
		else
		{
			return 0;
		}

	}
	/**
	 * by given the user system id and the peer evaluation task id, update the Grade of Peer Evaluation Pair
	 * @param EvaluationPairID
	 * @param SystemID
	 * @param Commence
	 * @param Grade
	 * @throws 
	 * 		1. the task have been submitted and passed the due date 
	 * 		2. the task have not been assigned to current user 
	 * 		3. such peer evaluation pair not exist 
	 */
	public void ModifyEvaluationPair(int EvaluationPairID , int SystemID , String Commence, String Grade ) throws Exception 
	{
		int PETaskID = this.GetPeerTaskID(EvaluationPairID);
		if( !this.CheckPEOwner(EvaluationPairID, SystemID) )
		{// the PE pair not created by user 
			throw new Exception("PE pair not belong to user");
		}
		if( PETaskID == -1 )
		{// the Peer evluation pair not exist 
			throw new Exception("PE pair not exist");
		}
		if( !this.CheckAssign(PETaskID, SystemID))
		{// such assignment have not been assigned to user 
			throw new Exception("PE task "+ PETaskID +" have not been assigned to user "+SystemID);
		}
		if( this.CheckNotSubmit(PETaskID, SystemID) && this.CheckPassDueDate(PETaskID) )
		{// such assignment have passed the due date 
			throw new Exception("User " +SystemID + " have submitt "+PETaskID + " and Task "+PETaskID + " have passed the due date ");
		}
		String Update= "Update PeerEvaluation_Pair set Comments =?, Grade =? where id = ?";
		PreparedStatement pstmt = DH.Conn.prepareStatement(Update);
		pstmt.setString(1, Commence);
		pstmt.setString(2, Grade.charAt(0)+"");
		pstmt.setInt(3, EvaluationPairID);
		pstmt.executeUpdate();
	}
}
