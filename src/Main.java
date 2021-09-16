import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		// get path and create input stream
		final String trainingPath = "/Users/feiyansu/Desktop/4375/HW1/train.dat";
		final String testPath = "/Users/feiyansu/Desktop/4375/HW1/test.dat";
		FileInputStream trainingFile = new FileInputStream(trainingPath);
		FileInputStream testFile = new FileInputStream(testPath);
		
		// create root node for tree
		Node root = new Node();
		// read file into root node
		String[] attributes = readFile(trainingFile, root.data);
		// create tree
		DecisionTree tree = new DecisionTree(root, attributes);
		// print tree
		tree.print();
		System.out.println();
		
		// read store data from files for testing
		HashMap<String, List<Integer>> testData = new HashMap<>();
		HashMap<String, List<Integer>> trainingData = new HashMap<>();
		for(String key : root.data.keySet()) trainingData.put(key, root.data.get(key));
		readFile(testFile, testData);
		
		Test testTraining = new Test(tree, trainingData, "training");
		testTraining.runTest();
		
		Test testTest = new Test(tree, testData, "test");
		testTest.runTest();
		
	}

	public static String[] readFile(FileInputStream file, HashMap<String, List<Integer>> data)
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
		
		return headers;
	}
}
