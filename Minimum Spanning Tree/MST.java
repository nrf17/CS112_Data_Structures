package apps;

import structures.*;
import java.util.ArrayList;

public class MST {

	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		PartialTreeList partTreeList = new PartialTreeList();
		for(int i = 0; i < graph.vertices.length; i++){
			Vertex vert = graph.vertices[i];
			PartialTree partTree = new PartialTree(vert);
			for(Vertex.Neighbor friend = vert.neighbors; friend != null; friend = friend.next){
				PartialTree.Arc insurt = new PartialTree.Arc(vert, friend.vertex, friend.weight);
				partTree.getArcs().insert(insurt);
			}
			partTreeList.append(partTree);
		}
		return partTreeList;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		ArrayList<PartialTree.Arc> XeCute = new ArrayList<PartialTree.Arc>();
		int countDown = ptlist.size();
		while(countDown > 1){
			PartialTree partTree = ptlist.remove();
			if(partTree == null){ break; }
			MinHeap<PartialTree.Arc> partHeap = partTree.getArcs();
			PartialTree.Arc bP = partHeap.deleteMin();
			Vertex vertB = bP.v2;
			Vertex vertA = partTree.getRoot();
			if(vertA == vertB.parent || vertB == vertA){ bP = partHeap.deleteMin(); vertB = bP.v2.parent; }
			XeCute.add(bP);
			PartialTree pTree = ptlist.removeTreeContaining(vertB);
			pTree.getRoot().parent = partTree.getRoot();
			pTree.merge(partTree);
			ptlist.append(pTree);
			countDown--;
		}
		return XeCute;
	}
}
