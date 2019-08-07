public class Label {
/* Label object and helper functions for each line */

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