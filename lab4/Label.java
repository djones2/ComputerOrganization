import java.util.*;

public class Label {

	private String myLabel;
	private int line;
	
	public Label(String inLabel, int line) {
		myLabel = inLabel;
		this.line = line;
	}

	public String getLabel() {
		return myLabel;
	}
	
	public int getAbsoluteLineNumber() {
		return line;
	}
	
	public int getRelativeLineNumber(int i) {
		return -1 * (i + 1 - line);
	}

	public static ArrayList<Label> find(String in) {
		Scanner scan;
		String currentLine;
		int lineNum;
		String label;
		ArrayList<Label> labels;
		
		int index;
		
		labels = new ArrayList<Label>();
		scan = new Scanner(in);
		lineNum = 0;
		
		while (scan.hasNextLine()) {
			currentLine = scan.nextLine();
			
			while (PassOne.lineHasLabel(currentLine)) {
				label = PassOne.makeLabel(currentLine);
				labels.add(new Label(label, lineNum));
				currentLine = PassOne.removeLabel(currentLine);
			}
			
			lineNum++;
		}
		
		return labels;
		
	}
	
	public boolean equals(Object o) {
		Label other;
		
		if (o instanceof Label) {
			other = (Label) o;
			if (other.getLabel().compareTo(this.myLabel) == 0)
				return true;
		}
		
		return false;
	}
}