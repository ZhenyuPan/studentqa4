package PeerEvaluation;

import java.util.List;

public class PeerEvaluationDrive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("First Run");
		PeerEvaluation PE = new BinaryGraphSolution();
		PE.InitalRandomAssigment(5, 2);
		for( int i = 1 ; i <= 5 ; i++ )
		{
			List<Integer> Result = PE.GetAssign(i,5);
			System.out.println("student "+i+" assigned:"+Result);
		}
		System.out.println("Second Run");
		PE.InitalRandomAssigment(5, 2);
		for( int i = 1 ; i <= 5 ; i++ )
		{
			List<Integer> Result = PE.GetAssign(i,5);
			System.out.println("student "+i+" assigned:"+Result);
		}
		System.out.println("Third Run");
		int Total = 10 ; int Volum =2;
		PE.InitalRandomAssigment(Total, Volum);
		for( int i = 1 ; i <= Total ; i++ )
		{
			List<Integer> Result = PE.GetAssign(i,Total);
			System.out.println("student "+i+" assigned:"+Result);
		}
	}

}
