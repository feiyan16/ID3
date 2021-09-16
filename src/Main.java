import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final String trainingPath = "/Users/feiyansu/Desktop/test.txt";
//		final String testPath = "/Users/feiyansu/Desktop/4375/HW1/test.dat";
		FileInputStream file = new FileInputStream(trainingPath);
//		HashMap<String, List<Integer>> testData = new HashMap<>();
		
		Node root = new Node();
		readFile(file, root.data);
		DecisionTree tree = new DecisionTree(root);
		tree.print();
	}

	public static void readFile(FileInputStream file, HashMap<String, List<Integer>> data)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		
		String headStr = reader.readLine();
		String[] headers = headStr.split("\t");
		
		// initializes empty arrays for each header's values
		for(int i = 0; i < headers.length; i++) 
			data.put(headers[i], new ArrayList<>());
		
		String line = reader.readLine();
		while(line != null) {
			
			if(line.isBlank() || line.isEmpty()) {
				line = reader.readLine();
				continue;
			}
			
			String[] values = line.split("\t");
			
			for(int i = 0; i < headers.length; i++) {
				
				if(!(values[i].isBlank() && values[i].isEmpty())) {
					int value = Integer.valueOf(values[i]);
					data.get(headers[i]).add(value);
				}
			}
			
			line = reader.readLine();
		}
		
		reader.close();
	}
}
