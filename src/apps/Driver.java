package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import structures.Graph;

public class Driver {
	public static void main(String[] args) throws IOException{
		Graph g = new Graph("graph1.txt");
		PartialTreeList t=MST.initialize(g);
		//Iterator<PartialTree> i=t.iterator();
		ArrayList<PartialTree.Arc> a = MST.execute(t);
		for(PartialTree.Arc b : a)
		{
			System.out.println(b.toString());
		}
		//ArrayList<PartialTree.Arc> b = MST.execute(t);
		
	}  
}
 