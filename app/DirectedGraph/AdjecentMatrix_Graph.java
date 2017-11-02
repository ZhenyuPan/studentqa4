package DirectedGraph;

import java.util.ArrayList;
import java.util.List;

public class AdjecentMatrix_Graph implements DirectGraph {

	String[] NodeList;
	// the name of Node 
	int[][]  A;
	// the Adjecent Matrix :
    //   A[i][j] = w iff weight from Node i to Node j is w 
	//   A[i][j] = 0 iff there has no edge from Node i to Node j
	boolean[] ViewedNode;
	// used to mark if the Node have been visted before
	@Override
	public void InitalDirectGraph(int NodeNumber) {
		// TODO Auto-generated method stub
		NodeList = new String[NodeNumber];
		A        = new int[NodeNumber][NodeNumber];
		ViewedNode = new boolean[NodeNumber];
		for( int i = 0 ; i< NodeNumber ; i++ )
		{
			NodeList[i] = i+"";
			ViewedNode[i] = false;
		}
		// allocation space for both data strucutre 
	}

	@Override
	public boolean ModifyNodeName(int NodeIndex, String NodeName) {
		// TODO Auto-generated method stub
		if( NodeIndex < 0)
		{
			return false;
		}
		if( NodeIndex < NodeList.length)
		{
			NodeList[NodeIndex]=NodeName;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public AdjecentMatrix_Graph CopyGraph() {
		// TODO Auto-generated method stub
		AdjecentMatrix_Graph Result = new AdjecentMatrix_Graph();
		//create new graph;
		Result.InitalDirectGraph(NodeList.length);
		//allocation space;
		for( int i=0 ; i < NodeList.length ; i++ )
		{
			Result.NodeList[i] = NodeList[i];
		}
		for( int i=0 ; i< NodeList.length ; i++ )
		{
			for( int j = 0 ; j < NodeList.length ; j++ )
			{
				Result.A[i][j] = A[i][j];
			}
		}
		return Result;
	}

	@Override
	public DirectGraph MaximumFlow(int Source, int Sink) {
		// TODO Auto-generated method stub
		AdjecentMatrix_Graph CapacityGraph=  this.CopyGraph();
		// the capacity graph will keep change;
		AdjecentMatrix_Graph FlowGraph = new AdjecentMatrix_Graph();
		// the flow graph will be the result;
		FlowGraph.InitalDirectGraph(NodeList.length);
		for( int i= 0 ; i < NodeList.length ; i++ )
		{
			FlowGraph.NodeList[i] = NodeList[i];
		}
		// Copy the Node Name list;
		do{
			List<Integer> AugmentPath = CapacityGraph.AugmentPath(Source, Sink);
			if( AugmentPath.size() == 0 )
			{
				break;
			}
			int MinimumWeight = CapacityGraph.GetMinimumWeight(AugmentPath);
			FlowGraph.ModifyFlowDiagram(AugmentPath,MinimumWeight);
			CapacityGraph.ModifyCapacityDiagram(AugmentPath,MinimumWeight);
			for( int i = 0 ; i< NodeList.length ; i++ )
			{
				CapacityGraph.ViewedNode[i] = false;
			}
		}while(true);
		return FlowGraph;
	}
	
	public int GetMinimumWeight(List<Integer> AugmentPath)
	{
		if( AugmentPath.size() == 0)
		{
			return 0;
		}
		int Current = AugmentPath.get(0);
		int Next = AugmentPath.get(1);
		//Start from the Source Node
		int MinWeight = this.GetWeight(Current, Next);
		for( int i=1; i< AugmentPath.size() ; i++ )
		{
			Next = AugmentPath.get(i);
			int Weight = this.GetWeight(Current, Next);
			if( Weight < MinWeight)
			{
				MinWeight = Weight;
			}
			Current = Next;
		}
		return MinWeight;
	}
	
	@Override
	public List<Integer> AugmentPath(int Source, int Sink) {
		// TODO Auto-generated method stub
		java.util.Random MyRandom = new java.util.Random();
		List<Integer> Result = new ArrayList<Integer>();
		// prepare the result;
		if( Source == Sink)
		{
			Result.add(Sink);
			return Result;
		}
		else
		{
			List<Integer> AdjecentNodeList= new ArrayList<Integer>();
			for( int i= 0 ; i < NodeList.length ; i++ )
			{
				if(A[Source][i]!= 0)
				{// there exist a edge from Source to i 
					if( ViewedNode[i] == false)
					{
						// there exist a edge from Source to i and Node_i have not been checked yet;
						AdjecentNodeList.add(i);
						// Now add the check mark 
						ViewedNode[i] = true;
					}
				}
			}
			//AdjecentNode List is the list of node that has adjacent to source and not been checked;
			while( AdjecentNodeList.size() > 0 )
			{
				int RandomIndex = Math.abs(MyRandom.nextInt())%AdjecentNodeList.size();
				//int RandomIndex = (int)Math.random()*AdjecentNodeList.size();
				int NodeRandom  = AdjecentNodeList.get(RandomIndex);
				// random pick up an adjacent node from Adjacent list;
				List<Integer> SubPath = this.AugmentPath(NodeRandom, Sink);
				// try to find a sub-augment path from NodeRandom to Sink;
				if( SubPath.size() > 0 )
				{// there do exist an augment path from NodeRandom to Sink;
					Result.add(Source);
					// Source -> NodeRandom
					for( int i = 0 ; i < SubPath.size() ; i++ )
					{
						Result.add(SubPath.get(i));
					}
					return Result;
					// Source -> NodeRandom ->... -> Sink;
				}
				else
				{// there do not exist an augment path from NodeRandom to Sink;
					AdjecentNodeList.remove(RandomIndex);
					// remove the Random Node from the AdjecentNodeList;
				}
			}
		}
		return Result;
		// return an empty List;
	}

	@Override
	public Boolean ModifyCapacityDiagram(List<Integer> AugmentPath,int MinWeight) {
		// TODO Auto-generated method stub
		if( AugmentPath.size() == 0 )
		{
			return false;
		}
		int Current = AugmentPath.get(0);
		for( int i = 1; i< AugmentPath.size();i++)
		{
			int Next = AugmentPath.get(i);
			int NewWeight = this.GetWeight(Current, Next)-MinWeight;
			int ReverseWeight = this.GetWeight(Next, Current)+MinWeight;
			this.AddEdge(Current, Next, NewWeight);
			//reduce the capacity from Current-> Next;
			this.AddEdge(Next, Current, ReverseWeight);
			//increase the capacity from Next->Current;
			Current = Next;
		}
		//modify the capacity alone the Augment Path
		return true;
	}

	@Override
	public Boolean ModifyFlowDiagram(List<Integer> AugmentPath,int MinWeight) 
	{
		if( AugmentPath.size() == 0 )
		{
			return false;
		}
		int Current = AugmentPath.get(0);
		for( int i = 1 ; i < AugmentPath.size() ; i++ )
		{
			int Next = AugmentPath.get(i);
			int Currentflow = this.GetWeight(Current, Next);
			int Reverseflow = this.GetWeight(Next, Current);
			if( Reverseflow > 0 )
			{
				int NewReverseflow = Reverseflow - MinWeight;
				this.AddEdge(Next, Current, NewReverseflow);
			}
			else
			{
				this.AddEdge(Current, Next, Currentflow+MinWeight);
			}
			Current = Next;
		}
		return true;
	}

	@Override
	public int GetWeight(int NodeI, int NodeJ) {
		// TODO Auto-generated method stub
		return A[NodeI][NodeJ];
	}

	@Override
	public boolean AddEdge(int NodeI, int NodeJ, int Weight) {
		// TODO Auto-generated method stub
		A[NodeI][NodeJ]=Weight;
		return true;
	}

	static private void DisplayAugmentPath(List<Integer> AugmentPath)
	{
		for( int i = 0; i< AugmentPath.size() ; i++ )
		{
			int Current = AugmentPath.get(i);
			System.out.print(Current);
			if( i == AugmentPath.size()-1 )
			{
				System.out.println();
			}
			else
			{
				System.out.print("->");
			}
		}
	}
}
