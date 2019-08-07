import java.io.*;
import java.util.*;

public class PassOne {
/* First pass through the input file, rearrange text to 
   become readable by the  second pass */
	public static String formatFile(File input)
		throws FileNotFoundException, SyntaxException {
		String result;
		String currentLine;
		String label;
		Scanner scan;
		int index;

		scan = new Scanner(input);
		result = "";

		/* Until EOF, strip file (by each line) of 
			whitespace, comments, etc. */

		while (scan.hasNextLine()){
			currentLine = scan.nextLine();
			currentLine = stripComments(currentLine);

			if (lineHasLabel(currentLine)) {
				label = "";
				while(lineHasLabel(currentLine)){
					label = label + makeLabel(currentLine) + ":";
					currentLine = removeLabel(currentLine);
				}

				try{
					while(currentLine.compareTo("")==0){
						currentLine = scan.nextLine();
						currentLine = stripComments(currentLine);
						if(lineHasLabel(currentLine)){
							while (lineHasLabel(currentLine)){
								label = label + makeLabel(currentLine) + ":";
								currentLine = removeLabel(currentLine);
								currentLine = currentLine.trim();

						}
					}
				}
				currentLine = label + currentLine;
			}
				catch (NoSuchElementException e){
					currentLine = label;
				} 
			}

			currentLine = currentLine.replace("$", " $");
			currentLine = currentLine.replace("( $", "($");
			currentLine = currentLine.replace(",", ", ");

			if (currentLine.compareTo("") != 0)
				result = result + currentLine + "\n";
		}
		return result;
	}

	/* Helper Functions */

	public static String makeLabel(String input) {
		if (lineHasLabel(input)) 
			return input.substring(0, input.indexOf(":"));		
		return "";
	}
	
	public static boolean lineHasLabel(String input) {
		if (input.indexOf(":") == -1)
			return false;
		
		return true;
	}
	public static String removeLabel(String in) {
			int index;
			
			index = in.indexOf(":");
			
			if (index != -1 && index < in.length())
				return in.substring(index + 1);
			
			return in;
		}

	public static String removeLabels(String in) {
		String out;
		
		out = in;
		
		while (lineHasLabel(out))
			out = removeLabel(out);
			
		return out;
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