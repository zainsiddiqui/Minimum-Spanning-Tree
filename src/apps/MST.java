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

		PartialTreeList list = new PartialTreeList();
		Vertex[] vert = graph.vertices; 
		
		for (int i = 0; i < vert.length; i++) {
			PartialTree tree = new PartialTree(vert[i]);
			Vertex v1 = vert[i];
			Vertex.Neighbor vN = vert[i].neighbors;
		
			for (;vN != null; vN = vN.next) {
				int weight = vN.weight;
				Vertex v2 = vN.vertex;
				PartialTree.Arc A =new PartialTree.Arc(v1, v2, weight);
				tree.getArcs().insert(A);
			}
			
			list.append(tree);
		}
		return list;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		PartialTreeList L = ptlist;
		ArrayList<PartialTree.Arc> arcList = new ArrayList<PartialTree.Arc>();
		
		while(L.size() > 1){
			//Remove the first partial tree PTX from List
			PartialTree PTX = L.remove();
			
			//System.out.println(PTX.toString());
			
			// Let PQX be PTX's priority queue
			MinHeap<PartialTree.Arc> PQX;
			PQX = PTX.getArcs();
			
			//Remove the highest-priority arc from PQX. Say this arc is a. 
			PartialTree.Arc alpha;
			alpha = PQX.getMin();
			
			//Let v1 and v2 be the two vertices connected by a -- where v1 belongs to PTX.
			Vertex v1 = PTX.getRoot();
			Vertex v2 = alpha.v2;
			//If v2 also belongs to PTX, go back  and pick the next highest priority arc
			while(v2.getRoot().equals(v1) && PQX.isEmpty() == false){   
				
				alpha = PQX.deleteMin();
				v2 = alpha.v2;
				
			}
			
			
			//Find the partial tree PTY to which v2 belongs and remove PTY from the partial tree list L.
			PartialTree PTY = L.removeTreeContaining(v2);
			
			//Let PQY be PTY's priority queue.
			MinHeap<PartialTree.Arc> PQY = PTY.getArcs();
			
			//Integrate PTX and PTY. 
			PTY.getRoot().parent=PTX.getRoot();
			
			//Merge the priority queues PQX and PQY into a single priority queue.
			PQY.merge(PQX);
			
			try {
				PQX.getMin();
			}catch (Exception noSuchElement) {
				//Exit while loop, PQX is Empty
				break;
			}
			//Merge PTX with PTY
			PTX.merge(PTY);
			
			//Append the resulting tree to the end of L 
			L.append(PTX);
			arcList.add(alpha);
			 
			
		}
		return arcList;
	}
}
