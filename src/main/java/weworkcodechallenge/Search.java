package weworkcodechallenge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class Search extends Thread implements Runnable {

	private int start;
	private int end;
	private String[] urlList;
	private String searchTerm1;
	private String searchTerm2;
	private File resultFile;
	StringBuffer resultURL = new StringBuffer();
	private Object lock = new Object();

	// Constructor
	public Search(int start, int end, String[] urlList, String searchTerm1, String searchTerm2, File result) {
		this.start = start;
		this.end = end;
		this.urlList = urlList;
		this.searchTerm1 = searchTerm1;
		this.searchTerm2 = searchTerm2;
		this.resultFile = result;
	}

	// Run method
	public void run() {
		Connection con;
		String currentURL = "";
		Response res = null;
		String result = null;

		for (int i = start; i <= end; i++) {

			try {
				currentURL = "http://" + urlList[i];
				con = Jsoup.connect(currentURL);
				res = con.execute();
				
				if (res != null && res.body() != null) {
					result = res.body();

					String searchTerm = searchTerm1 + "|" + searchTerm2;
					Pattern p = Pattern.compile(searchTerm);
					Matcher m = p.matcher(result);

					if (m.find()) {
						writeFile(urlList[i]);

					}
				}
			} catch (Exception e) {

			}
		}
	}

	// Write URLs to the result file if the search term is on the page
	private synchronized void writeFile(String url) {
		BufferedWriter result = null;

		synchronized (lock) {
			try {
				result = new BufferedWriter(new FileWriter(resultFile, true));

				result.write(url);
				result.newLine();
			} catch (IOException e) {

			}
		}

		try {
			result.flush();
			result.close();

		} catch (IOException e) {

		}

	}

}
