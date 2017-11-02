package PeerEvaluation;

import java.util.ArrayList;
import java.util.List;

import DirectedGraph.AdjecentMatrix_Graph;
import DirectedGraph.DirectGraph;

public class BinaryGraphSolution implements PeerEvaluation{

	// the number of task that one student should evaluated;
	DirectGraph AssigmentDiagram;
	// the assigment grade-able between those student;
	DirectGraph AssignmentResult;
	@Override
	public void InitalRandomAssigment(int TotalNumber, int Volumn) {
		// TODO Auto-generated method stub
		AssigmentDiagram = new AdjecentMatrix_Graph();
		AssigmentDiagram.InitalDirectGraph(TotalNumber*2+2);
		//the source is Zero; the Sink is 2n+1;
		// {1,3,...,2n-1} the Student Set, Student Index = (NodeIndex+1)/2;
		// {2,4,...,2n  } the Assignment Set, Assignment Index = (NodeIndex)/2;
		for( int i = 1 ; i <= TotalNumber ; i++ )
		{
			//student from 1 to n
			int StudentNode = i*2-1;
			for( int j = 1 ; j <= TotalNumber ; j++ )
			{//assignment from 1 to n
				int AssignmentNode = j*2;
				if( i == j )
				{//student i cannot grade his own assigment
					continue;
				}
				else
				{
					AssigmentDiagram.AddEdge(StudentNode, AssignmentNode, 1);
					//student i can only grade assigment j one time;
				}
			}
		}
		//Initial the Source 
		for( int i = 1 ; i <= TotalNumber ; i++ )
		{
			int StudentNode = i*2 -1;
			AssigmentDiagram.AddEdge(0, StudentNode, Volumn);
			//each student need to grade Volumn Task;
		}
		//Inital the Sink
		for( int j =1 ; j <= TotalNumber;j++ )
		{
			int AssignmentNode = j*2;
			AssigmentDiagram.AddEdge(AssignmentNode, 2*TotalNumber+1, Volumn);
		}
		// Now Generate the assignment 
		this.AssignmentResult = AssigmentDiagram.MaximumFlow(0, 2*TotalNumber+1);
	}

	@Override
	public List<Integer> GetAssign(int StudentIndex ,int total) {
		// TODO Auto-generated method stub
		int StudentNode = 2* StudentIndex-1;
		List<Integer> Assignments= new ArrayList<Integer>();
		for( int i = 1 ; i <= total ; i++)
		{
			int AssignmentID = 2*i;
			if( AssignmentResult.GetWeight(StudentNode, AssignmentID) == 1)
			{
				Assignments.add(i);
			}
		}
		return Assignments;
	}

}
