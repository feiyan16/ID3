import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String trainingPath = null;
		String testPath = null;
		if(args.length < 2) {
			System.out.println("Please enter training data and test data");
			System.exit(0);
		} else {
			trainingPath = args[0];
			testPath = args[1];
		}
		
		// get path and create input stream
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
		trainingFile = new FileInputStream(trainingPath);
		readFile(trainingFile, trainingData);
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

			if(line.isEmpty() || line.startsWith(" ") || line.startsWith("\t")) {
				line = reader.readLine();
				continue;
			}

			String[] values = line.split("\t");

			for(int i = 0; i < headers.length; i++) {

				if(!(values[i].startsWith(" ") && values[i].startsWith("\t") && values[i].isEmpty())) {
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
