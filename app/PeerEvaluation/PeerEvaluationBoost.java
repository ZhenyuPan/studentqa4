package PeerEvaluation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Physic_Structure.DatabaseHandle;

public class PeerEvaluationBoost  
{
	
	public void BoostAll()
	{	
		DatabaseHandle DH = new DatabaseHandle();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String strDate = sdf.format(now);
		//1: list all the Actived Class;
		String Query = "SELECT id FROM Class Where BeginTime < ? and EndTime > ? ";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setString(1, strDate);
			pstmt.setString(2, strDate);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next() )
			{
				this.BoostClass(rs.getInt(1));
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	/**
	 * Boost the Class	
	 * @param ClassID	the system id of Class	
	 * @throws Exception
	 */
	void BoostClass(int ClassID) throws Exception
	{
		List<Integer> QA_Task_ID = new ArrayList<Integer>();
		List<Integer> QA_Task_Grade=new ArrayList<Integer>();
		List<String>  QA_Task_Title=new ArrayList<String>();
		DatabaseHandle DH = new DatabaseHandle();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
	    String strDate = sdf.format(now);
		//1: list all the QA task that have passed the due date while have not been evaluated 
		String Query =
				  "	SELECT 	\n"
				+ "			A.id,QA.MinumalGrader,A.Title,A.DueDate,A.Description\n"
				+ " FROM	\n"
				+ "			QA_Task QA,Assignment A"
				+ " WHERE	\n"
				+ "			A.ClassID = ? and "
				+ "			QA.AssignmentID = A.id and \n"
				+ "			A.DueDate < ? and \n"
				+ "			A.id not in (select EvaluateTarget from peerevaluation_task )\n";
		try{
			PreparedStatement pstmt = DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, ClassID);
			pstmt.setString(2, strDate);
			ResultSet rs= pstmt.executeQuery();
			while( rs.next() )
			{
				QA_Task_ID.add(rs.getInt(1));
				QA_Task_Grade.add(rs.getInt(2));
				QA_Task_Title.add(rs.getString(3));
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
			//QA_Task_ID 	: the QA task that belong to current class that passed the due date while not been evaluated 
			//QA_Task_Grade : the minimum require evaluater for given Task
			//QA_Task_Title	: the Title of QA Task;
		//2: Boost peer evaluation task for each QA task 
		for( int i = 0 ; i < QA_Task_ID.size() ; i++ )
		{
			BoostTask(QA_Task_ID.get(i),QA_Task_Grade.get(i),ClassID,QA_Task_Title.get(i));
		}
	}
	/**
	 * this function is used to boost the QA task
	 * @param TaskID		the system id of QA task
	 * @param GraderNumber	the number of Task a student need to grade
	 * @param ClassID		the Class the QA task belong to 
	 * @param TaskTitle		the Title of QA Task
	 * @throws Exception
	 */
	void BoostTask(int TaskID,int GraderNumber,int ClassID, String TaskTitle) throws Exception 
	{
		DatabaseHandle DH = new DatabaseHandle();
		List<Integer> Student = new ArrayList<Integer>();
		//1:List all the student who have already submitted they assignment on given task
		String Query= "Select  distinct CampusID from Submission where AssignmentID = ? and Submitted is not null";
		try{
			PreparedStatement pstmt =  DH.Conn.prepareStatement(Query);
			pstmt.setInt(1, TaskID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				Student.add(rs.getInt(1));
			}
		}catch(Exception E)
		{
			E.printStackTrace();
		}
			// TaskID		: the QA Task Need to be Boost 
			// GraderNumber	: the number of Task A student need to grade 
			// Student		: the list of Student who have finish the QA task 
		//2: generate Peer Evaluation Task that evaluation Target is given task
		int PE_TaskID = this.CreatePETask(TaskID, ClassID, TaskTitle);
		if( PE_TaskID == -1 )
		{	throw new Exception("CreatePETask Error"); }
			// PE_TaskID	: the new created Peer Evaluation Task;
		//3: assign the Peer Evaluation Task to user
		for( int index = 0 ; index < Student.size() ; index++ )
		{
			String Insert="Insert into Submission(AssignmentID,CampusID) values(?,?)";
			PreparedStatement pstmt = DH.Conn.prepareStatement(Insert);
			pstmt.setInt(1, PE_TaskID);
			pstmt.setInt(2, Student.get(index));
			pstmt.executeUpdate();
		}
			
		//4: generate peer Evaluation pair
		BinaryGraphSolution BGS = new BinaryGraphSolution();
		BGS.InitalRandomAssigment(Student.size(), GraderNumber);
		System.out.println("For QATask"+TaskID);
		for( int index = 1 ; index <= Student.size() ; index++)
		{
			List<Integer> EvaluationList = BGS.GetAssign(index, Student.size());
			System.out.println("Student "+index+" Evaluated "+ EvaluationList);
			for( int j = 0 ; j < EvaluationList.size() ; j++ )
			{
				System.out.println(Student.get(index-1)+" Evaluated "+ Student.get(EvaluationList.get(j)-1));
			}
		}
		for( int index = 0 ; index < Student.size() ; index++)
		{
			int GraderCampusID = Student.get(index);
			List<Integer> EvaluatedTarget=BGS.GetAssign(index+1, Student.size());
			//get the Evaluation Target's Campus ID 
			for( int j = 0 ; j < EvaluatedTarget.size() ; j++ )
			{
				int TargetIndex = EvaluatedTarget.get(j);
				// the Student with index + 1 grade the student with Target Index 
				int TargetCampusID = Student.get(TargetIndex-1);
				this.PeerEvaluatePair(GraderCampusID, TargetCampusID, PE_TaskID, TaskID);
			}
		}
	}
	/**
	 * this function is used to find the maximum Assignment ID
	 * @return the maximum AssignemntID
	 */
	int  MaxmumAssignmentID()
	{
		String Query= "Select max(id) from Assignment";
		DatabaseHandle DH = new DatabaseHandle();
		try{
			Statement stmt = DH.Conn.createStatement();
			ResultSet rs   = stmt.executeQuery(Query);
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
		}
		return 0;
	}
	/**
	 * this function is used to Create A New Peer Evaluation Task 
	 * @param 
	 * 		QATaskID	the system id of QA task
	 * @param 
	 * 		ClassID		the Class that QA task belong to 
	 * @param 
	 * 		QATaskTitle	 the title of QA task
	 * @return
	 * 		the new created QA task ID
	 */
	int CreatePETask(int QATaskID,int ClassID,String QATaskTitle)
	{
		DatabaseHandle DH = new DatabaseHandle();
		int NEWPEID = MaxmumAssignmentID();
		Calendar C = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		C.add(Calendar.DATE, 7);		
		String DueDate = sdf.format(C.getTime());
		String Description = "Peer Evaluation Task";
		//1: Generate  the  new Assignment;
		try{
			String Update = "Insert into Assignment(id,ClassID,Title,Description,DueDate) values(?,?,?,?,?)";
			PreparedStatement pstmt = DH.Conn.prepareStatement(Update);
			pstmt.setInt(1, NEWPEID+1);
			pstmt.setInt(2, ClassID);
			pstmt.setString(3, "Peer Evaluation On "+ QATaskTitle);
			pstmt.setString(4, Description);
			pstmt.setString(5, DueDate);
			pstmt.executeUpdate();
		}catch(Exception E)
		{
			E.printStackTrace();
			return -1;
		}
		//2: Set the new Generated Assignment as the Peer Evaluation Task;
		try{
			String Update = "insert into PeerEvaluation_Task values(?,?)";
			PreparedStatement pstmt = DH.Conn.prepareStatement(Update);
			pstmt.setInt(1, NEWPEID+1);
			pstmt.setInt(2, QATaskID);
			pstmt.executeUpdate();
		}catch(Exception E)
		{
			E.printStackTrace();
			return -1;
		}
		return NEWPEID+1;
	}
	/**
	 * let Grader grade all target's QA pair for perticular QA_Task
	 * @param GraderCampusID	the Grader's Campus ID
	 * @param TargetCampusID	the Target's Campus ID
	 * @param PE_ID				the Peer Evaluation TaskID
	 * @param QA_TaskID			the QA Task ID
	 */
	void PeerEvaluatePair(int GraderCampusID, int TargetCampusID , int PE_ID , int QA_TaskID )
	{
		DatabaseHandle DH = new DatabaseHandle();
		String Insert = 
				  "	Insert into PeerEvaluation_Pair(AssignmentID,UserID,QAPairID) \n"
				+ "	Select distinct ?,Grader.id,QA_Pair.id\n"
				+ "	from Userlist Grader, QA_Pair,Userlist Target \n"
				+ "	Where "
				+ "		Grader.CampusID = ? and "
				+ "		Target.CampusID = ? and "
				+ "		QA_Pair.UserID  = Target.id and"
				+ "		QA_Pair.AssignmentID = ?";
		try{
			PreparedStatement pstmt =DH.Conn.prepareStatement( Insert);
			pstmt.setInt(1, PE_ID);
			pstmt.setInt(2, GraderCampusID);
			pstmt.setInt(3, TargetCampusID);
			pstmt.setInt(4, QA_TaskID);
			pstmt.executeUpdate();
		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}
}
