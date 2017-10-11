package weworkcodechallenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParseURL {

	public static String[] urlfinal;
	public static String searchTerm1;
	public static String searchTerm2;
	public static File results;

	public static void main(String[] args) throws IOException {

		// User input search terms
		Scanner in = new Scanner(System.in);
		System.out.println("\t" + "\t" + "\t" + "\t" + "*******************************");
		System.out.println("\t" + "\t" + "\t" + "\t" + "Welcome to the Website Searcher");
		System.out.println("\t" + "\t" + "\t" + "\t" + "*******************************");

		System.out.println();

		System.out.println("STEP 1:");
		System.out.println();
		System.out.println("Please enter the input file directory in the below format: ");
		System.out.println("/user/documents/foldername");
		String inLoc = in.nextLine();

		File input = new File(inLoc);

		// Input file validation
		try {

			if (input.isDirectory()) {
				input = new File(input + "/input.txt");
			}

			if (input.exists()) {
				System.out.println();
				System.out.println("Found file in the given path: " + input.getAbsolutePath());
				System.out.println();
				System.out.println("File found. Parsing the input file. ");

				urlfinal = getURL(input);
			} else {
				input = new File("input.txt");
				System.out.println();
				System.out.println("The given input file is either incorrect or no input has been given...." + "\n"
						+ "Hence looking for input in the default location: ");

				if (input.exists()) {
					System.out.println();
					System.out.println("Found file in the default path: " + input.getAbsolutePath());
					System.out.println();
					System.out.println("Parsing the input file...");

					urlfinal = getURL(input);
				} else {
					System.out.println();
					System.out.println("File not found anywhere");
					System.exit(0);
				}

			}

		} catch (Exception e) {

		}

		System.out.println();
		System.out.println("Input file has been parsed and all the URLs have been extracted..." + "\n"
				+ "Proceed with the search " + "\n");
		
		System.out.println("*************************************************************************");
		System.out.println();
		System.out.println("STEP 2:");
		System.out.println("Enter the first seachTerm (Cannot be empty)");
		searchTerm1 = in.nextLine();
		System.out.println("Enter the second seachTerm (Cannot be empty)");
		searchTerm2 = in.nextLine();

		System.out.println();
		System.out.println("Please enter the output file directory in the below format (Cannot be empty) : ");
		System.out.println("/user/documents/foldername");
		String outLoc = in.nextLine();

		File result = new File(outLoc);

		// Output file validation
		try {
			if (result.isDirectory()) {
				System.out.println();
				System.out.println("Valid directory.. Begin search");
				System.out.println();
				results = new File(result + "/results.txt");
			}

			if (results.exists()) {
				System.out.println();
				System.out.println(
						"There is already a results.txt file... Deleting the file before beginning to search..");
				results.delete();
			}
		}

		catch (Exception e) {

		}

		System.out.println("*************************************************************************");
		System.out.println();
		System.out.println("STEP 3:");
		System.out
				.println("Beginning to search the URLs to see if either of the two search terms exists on the page...");

		System.out.println();
		System.out.println("Post search, please find all the matching URLs listed in the results.txt file located at " + "\n" + 
								results.getAbsolutePath());

		ManageThreads thread = new ManageThreads(20);

	}

	// Parse the input file to take the list of URLs to be searched and put the
	// URL list into an array
	public static String[] parseInputFile(File input, String line, String splitBy) throws IOException {

		BufferedReader inFile = new BufferedReader(new FileReader(input));
		inFile.readLine();

		List<String> list = new ArrayList<String>();

		while ((line = inFile.readLine()) != null) {
			String[] split = line.split(splitBy);
			String url = split[1].replaceAll("\"", "");
			list.add(url);
		}

		String[] urlList = list.toArray(new String[0]);

		return urlList;
	}

	public static String[] getURL(File input) throws IOException {
		String line = null;
		String splitBy = ",";
		String[] urlFinal = parseInputFile(input, line, splitBy);

		return urlFinal;
	}

}
