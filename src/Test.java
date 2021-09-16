import java.util.*;

public class Test {
	
	String type; // test or training
	
	// data to test with and tree to test on
	HashMap<String, List<Integer>> data = new HashMap<>();
	DecisionTree id3;
	
	// actual and calculated class values for comparison later
	List<Integer> actual = new ArrayList<>();
	List<Integer> calculated = new ArrayList<>();

	public Test(DecisionTree id3, HashMap<String, List<Integer>> data, String type) {
		this.id3 = id3;
		actual = data.get("class");
		data.remove("class");
		this.data = data;
		this.type = type;
	}
	
	public void runTest() {
		int miss = 0;
		for(int i = 0; i < actual.size(); i++) {
			
			String firstAtt = id3.root.left.attribute;
			int firstBranch = data.get(firstAtt).get(i);
			Node current = firstBranch == 0 ? id3.root.left :
				firstBranch == 1 ? id3.root.mid : id3.root.right;
			
			while(current.left != null || current.mid != null || current.right != null) {
				String att = current.left.attribute;
				int branch = data.get(att).get(i);
				current = branch == 0 ? current.left : branch == 1 ? current.mid : current.right;
			}
			
			if(current.clss != actual.get(i))
				miss++;
		}
		
		double accuracy = ((double)actual.size() - (double)miss) / (double)actual.size();
		accuracy *= 100;
		
		System.out.printf("Accuracy on %s set (%d instances): %.1f%%\n\n", type, actual.size(), accuracy);
	}
	

}
