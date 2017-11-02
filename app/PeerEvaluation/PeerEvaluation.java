package PeerEvaluation;

import java.util.List;

public interface PeerEvaluation {
	void InitalRandomAssigment( int TotalNumber, int Volumn );

	/*@function: by knowing the total number of student, and how many assignment one student need to grade */

	/*@parameter: TotalNumber : the total number of student that submit their assignments*/

	/*@parameter: Volumn: the total number of assigment that one student need to grade*/

	List<Integer> GetAssign( int StudentIndex , int total);

	/*@function: after initial the structure, using the Student Index to find out the indexs of assignments that assign to the him*/

	/*@parameter:StudentIndex: the index of student (not student ID )*/

	/*@return:the index of assignments that assign to given student*/
}
