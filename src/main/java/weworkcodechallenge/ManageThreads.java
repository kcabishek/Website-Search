package weworkcodechallenge;

import java.io.File;

public class ManageThreads {

	private final Search[] threads;

	// Constructor to initialize threads
	public ManageThreads(int nThreads) {
		// this.nThreads = nThreads;
		threads = new Search[nThreads];
		ParseURL parse = new ParseURL();
		String[] urlList = parse.urlfinal;
		String searchTerm1 = parse.searchTerm1;
		String searchTerm2 = parse.searchTerm2;
		File result = parse.results;
		int i = 0;
		int start = 0;
		int range = (urlList.length / nThreads) - 1;
		int end = (urlList.length / nThreads) - 1;

		for (i = 0; i < nThreads; i++) {
			threads[i] = new Search(start, end, urlList, searchTerm1, searchTerm2, result);
			start = end + 1;
			end += range;
		}

		for (int k = 0; k < nThreads; k++) {
			threads[k].start();
		}
		
	}

}
