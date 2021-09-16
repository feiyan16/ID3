import java.util.*;

public class Test {
	
	// data to test with and tree to test on
	HashMap<String, List<Integer>> data = new HashMap<>();
	DecisionTree id3;
	
	// actual and calculated class values for comparison later
	List<Integer> actual = new ArrayList<>();
	List<Integer> calculated = new ArrayList<>();

	public Test(DecisionTree id3, HashMap<String, List<Integer>> data) {
		this.id3 = id3;
		actual = data.get("class");
		data.remove("class");
		this.data = data;
	}
	
	public void runTest() {
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
		}
	}
	

}
