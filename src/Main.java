import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file = new File("/Users/feiyansu/Desktop/train4.txt");
		HashMap<String, List<Integer>> data = new HashMap<>();
		
		Node root = new Node();
		readFile(file, root.data);
		DecisionTree tree = new DecisionTree(root);
		tree.print();
	}

	public static void readFile(File file, HashMap<String, List<Integer>> data)
			throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		String headStr = scan.nextLine();
		String[] headers = headStr.split("\t");
		
		// initializes empty arrays for each header's values
		for(int i = 0; i < headers.length; i++) 
			data.put(headers[i], new ArrayList<>());
		
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] values = line.split("\t");
			
			for(int i = 0; i < headers.length; i++) {
				int value = Integer.valueOf(values[i]);
				data.get(headers[i]).add(value);
			}
		}
		scan.close();
	}
}
