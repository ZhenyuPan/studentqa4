package DirectedGraph;

public class MaximumFlowDrive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DirectGraph TestingCase= new AdjecentMatrix_Graph();
		TestingCase.InitalDirectGraph(6);
		TestingCase.AddEdge(0, 1, 3);
		TestingCase.AddEdge(0, 2, 2);
		TestingCase.AddEdge(1, 2, 2);
		TestingCase.AddEdge(1, 3, 3);
		TestingCase.AddEdge(2, 4, 2);
		TestingCase.AddEdge(3, 4, 4);
		TestingCase.AddEdge(3, 5, 2);
		TestingCase.AddEdge(4, 5, 3);
		DirectGraph Flow = TestingCase.MaximumFlow(0, 5);
	}

}
