import java.io.*;
import java.util.*;

public class PassOne {
/* First pass through the input file, rearrange text to 
   become readable by the  second pass */
	public static String formatFile(File f)
		throws FileNotFoundException, SyntaxException {
		String result;
		String currentLine;
		String label;
		Scanner scan;

		scan = new Scanner(f);
		result = "";

		/* Until EOF, strip file (by each line) of 
			whitespace, comments, etc. */

		while (scan.hasNextLine()){
			currentLine = scan.nextLine();
			currentLine = stripComments(currentLine);

			if (lineHasLabel(currentLine)) {
				label = makeLabel(currentLine);
				if((label + ":").compareTo(currentLine) == 0){
					try{
						do{
							currentLine = scan.nextLine();
							currentLine = stripComments(currentLine);
							if(lineHasLabel(currentLine))
								throw new SyntaxException("Two labels on one line");
						} while (currentLine.compareTo("") == 0);
						currentLine = label + ": " + currentLine;
					}
					catch (NoSuchElementException e){
						throw new SyntaxException("Label at end of file");						
					}
				}
			}

			/* Replace characters for readability by PassTwo */

			currentLine = currentLine.replace(",", ", ");
			currentLine = currentLine.replace("$", " $");
			currentLine = currentLine.replace("( $", "($");

			if (currentLine.compareTo("") != 0)
				result = result + currentLine + "\n";
		}
		return result;
	}

	/* Helper Functions */

	private static String makeLabel(String input) {
		if (lineHasLabel(input)) 
			return input.substring(0, input.indexOf(":"));
		
		return "";
	}
	
	private static boolean lineHasLabel(String input) {
		if (input.indexOf(":") == -1)
			return false;
		
		return true;
	}

	private static String stripComments(String input) {
		int line;
		if (input == null)
			return "";
			
		line = input.indexOf("#");
		if (line != -1)
			input = input.substring(0, line);
		
		input = input.trim();
		return input;
	}
	
}