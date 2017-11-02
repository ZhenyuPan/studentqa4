package DirectedGraph;

import java.util.List;

public interface DirectGraph 
{
	public void InitalDirectGraph( int NodeNumber );

	/*@function: we need an interface that tell us how to initalization of directed Graph*/

	/*@parameter: NodeNumber is the total number of Node in such graph*/

	public boolean ModifyNodeName( int NodeIndex, String NodeName);

	/*@function: used to change the name of Node, how to use Node's Name depend on the application*/

	/*@parameter NodeIndex: the index of Node in the Node list when we analysis simply denote Node with index i as Nodei*/

	/*@parameter NodeName : the New name of Nodei*/

	/*@return true if successfully modify the node name*/

	/*@default a node, which never been named, will be named as its index*/

	public boolean AddEdge( int NodeI , int NodeJ, int Weight );

	/*@function: used set the edge's weight from NodeI to NodeJ as Weight*/

	/*@parameter: NodeI , the index of Nodei*/

	/*@parameter: NodeJ , the index of Nodej*/

	/*@parameter: Weight, the new-weight for edge from Nodei to Nodej*/

	/*@return : true if and only if the edge modify success */

	DirectGraph CopyGraph();

	/*@function: used to generate a clone of current direct Graph*/

	/*@return: the duplication of current directed graph*/

	public DirectGraph MaximumFlow(int Source,int Sink);

	/*@function: find the maximum flow from source node to the sink node */

	/*@parameter: Source, the index of Source Node */

	/*@parameter: Sink, the index of sink Node */

	/*@return: the flow diagram of maximum flow solution*/

	List < Integer > AugmentPath(int Source, int Sink );

	/*@function: find the augment path from given source node to the sink node

	/*@parameter: Source, the index of Source Node */

	/*@parameter: Sink, the index of Sink Node */

	/*@return: the augment path from Source Node to the Sink Node if there has such path*/

	/*@return: empty list if there has no such path*/

	Boolean ModifyCapacityDiagram(List < Integer > AugmentPath,int MinWeight);

	/*@function :using the augment path to modify the flow Capacity of the Capacity Diagram*/

	/*@parameter: AugmentPath, the augmentPath from Source to sink*/

	/*@return : true if and only if the capacity diagram have been modified successfully */

	Boolean ModifyFlowDiagram(List < Integer > AugmentPaht ,int MinmumWeight);

	/*@function: using the augement path to modify the current flow in the flow diagram */

	/*@parameter: AugmentPath, the augmentPath from Source to sink*/

	/*@return: true, if and only if the flow diagram have been modified successfully*/

	int GetWeight(int NodeI, int NodeJ);

	/*@function: get the capacity or weight value of the edge from NodeI to NodeJ */

	/*@parameter: NodeI, the Index of Nodei*/

	/*@parameter: NodeJ, the Index of Nodej*/

	/*@return: the weight from Nodei to Nodej if such edge exist*/
	public int GetMinimumWeight(List<Integer> AugmentPath);
}
