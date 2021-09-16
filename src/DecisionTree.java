import java.util.*;

class Node {
	HashMap<String, List<Integer>> data = new HashMap<>();
	
	double 
	H, // entropy
	IG = -1, // information gain
	tot, // # of rows
	ct0 = 0, ct1 = 0, ct2 = 0; // partitioned count of classes
	
	String attribute = "";
	
	int branch = -1, clss = -1;
	
	Node left, mid, right; // 0, 1, 2 branch
	
	Node(){}

	void init() {
		this.tot = ct0 + ct1 + ct2;
		this.H = entropy();
	}
	
	boolean isLeaf() {
		if(H == 0)
			return true;
		return false;
	}

	double entropy() {
		
		if(tot == 0) 
			return 0;
		
		double zero = (ct0/tot) * (log2(ct0/tot));
		double one = (ct1/tot) * (log2(ct1/tot));
		double two = (ct2/tot) * (log2(ct2/tot));

		return -(zero + one + two);
	}

	double log2(double n) { 
		return n == 0 ? 0 : (Math.log(n) / Math.log(2)); 
	}

	void print() {
		System.out.printf("%s = %d : ", attribute, branch);
		System.out.print(clss == -1 ? "\n" : clss + "\n");
	}
}

public class DecisionTree {
	
	Node root;
	double currentIG = -1;
	
	public DecisionTree(Node root) {
		this.root = root;
		init();
		populate(root);
	}
	
	public void init() {
		List<Integer> classVal = root.data.get("class");
		for(Integer in : classVal) {
			if(in == 0) root.ct0++;
			else if (in == 1) root.ct1++;
			else if (in == 2) root.ct2++;
		}
		root.init();
	}
	
	public void populate(Node current) {
		if(current.isLeaf() || current.IG == 0) {
			int max = (int) Math.max(Math.max(current.ct0, current.ct1), current.ct2);
			if(current.ct0 == max)
				current.clss = 0;
			else if (current.ct1 == max)
				current.clss = 1;
			else 
				current.clss = 2;
			return;
		}
		
		String attribute = chooseAttribute(current);
		Node[] nodes = partition(attribute, current);
		
		for(Node n : nodes) {
			n.attribute = attribute;
			n.IG = currentIG;
		}

		current.left = nodes[0];
		current.mid = nodes[1];
		current.right = nodes[2];
		
		populate(current.left);
		populate(current.mid);
		populate(current.right);
	}
	
	public String chooseAttribute(Node current) {
		
		// Data table in current node
		HashMap<String, List<Integer>> data = current.data;
		// maps IG to attribute
		HashMap<Double, String> map = new HashMap<>();
		double MAX_IG = 0;
		
		for(String key : data.keySet()) {
			if(key.compareTo("class") == 0 || key.compareTo(current.attribute) == 0) 
				continue;
			
			Node[] nodes = partition(key, current);
			// calculate IG
			double IG = current.H - (
					nodes[0].H * (nodes[0].tot/current.tot) + // left, 0 node
					nodes[1].H * (nodes[1].tot/current.tot) + // mid, 1 node
					nodes[2].H * (nodes[2].tot/current.tot)); // right, 2 node
			
			MAX_IG = Math.max(MAX_IG, IG);
			map.put(IG, key);
		}
		// return attribute with the max IG
		currentIG = MAX_IG;
		return map.get(MAX_IG);
	}
	
	public Node[] partition(String attribute, Node current) {
		// initialize 3 nodes for branch 0, 1, and 2
		Node[] nodes = new Node[] { new Node(), new Node(), new Node() };
		nodes[0].branch = 0;
		nodes[1].branch = 1;
		nodes[2].branch = 2;
		
		// values for the relevant attribute
		List<Integer> attributeValues = current.data.get(attribute);
		// class values for current data set
		List<Integer> classValues = current.data.get("class");
		
		for(Node n : nodes) {
			for(String key : current.data.keySet()) 
				n.data.put(key, new ArrayList<>());
			
			for(int i = 0; i < attributeValues.size(); i++) {
				int v = attributeValues.get(i); // attribute value;
				int c = classValues.get(i); // corresponding class to value;
				
				if(v == n.branch) {
					if(c == 0) n.ct0++;
					else if (c == 1) n.ct1++;
					else if (c == 2) n.ct2++;
					
					for(String key : n.data.keySet()) 
						n.data.get(key).add(current.data.get(key).get(i));
				}
			}
			n.init();
		}
		
		return nodes;
	}

	public void print() {
		traverse(root, 0);
	}

	public void traverse(Node current, int level) {
		if(current == null)
			return;
		
		if(level > 0) {
			if (level > 1)
				for(int i = 0; i < level; i++) 
					System.out.print("|  ");

			current.print();
		} 
		traverse(current.left, level + 1);
		traverse(current.mid, level + 1);
		traverse(current.right, level + 1);
	}

}
