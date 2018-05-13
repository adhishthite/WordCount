import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileOperator implements Iterator, AutoCloseable {
	private final BufferedReader bufferedReader;
	private String nextLine;

	/** Constructor */
	public FileOperator(String fileName) throws IOException {
		bufferedReader = new BufferedReader(new FileReader(fileName));
		nextLine = bufferedReader.readLine();
	}

	/** Check whether the next line in the file is NULL */
	@Override public boolean hasNext() {
		return nextLine != null;
	}

	/** Get the current line and move the iterator to the next line */
	@Override public String next() {
		String lineToReturn = nextLine;
		
		try {
			nextLine = bufferedReader.readLine();
		} catch (IOException e) {
			nextLine = null;
		}
		return lineToReturn;
	}

	/** Close the buffered reader */
	@Override public void close() throws IOException {
		bufferedReader.close();
	}
}