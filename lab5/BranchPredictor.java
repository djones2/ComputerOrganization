public class BranchPredictor {
	public static int NOT_TAKEN_STRONG = 0;
	public static int NOT_TAKEN_WEAK = 1;
	public static int TAKEN_WEAK = 2;
	public static int TAKEN_STRONG = 3;

	private Integer[] predictions;
	private Integer[] pattern;

	public BranchPredictor(int patternSize){
		pattern = new Integer[patternSize];
		predictions = new Integer[(int)Math.pow(2, patternSize)];
		reset();
	}

	public void reset() {
		for (int i = 0; i < predictions.length; i++)
			predictions[i] = 0;
		for (int i = 0; i < pattern.length; i++)
			pattern[i] = 0;
	}

	private int patternToAddress() {
		int address;
		
		address = 0;
		
		for (int i = 0; i < pattern.length; i++)
			address += pattern[i] * Math.pow(2, i);
			
		return address;
	}

	private void push(boolean taken) {
		for (int i = pattern.length - 1; i > 0; i--)
			pattern[i] = pattern[i-1];
			
		pattern[0] = taken ? 1 : 0;
	}


	public int branch(boolean taken) {
		int address;
		int prediction;
		
		address = patternToAddress();
		prediction = predictions[address];
		
		update(address, taken);
		
		push(taken);
		
		return prediction;
	}

	private void update(int address, boolean taken) {
		if (taken && predictions[address] < TAKEN_STRONG)
			predictions[address]++;
		else if (!taken && predictions[address] > 0)
			predictions[address]--;
	}

	public String predictionsToString() {
		String res;
		
		res = "Predictions:";
		
		for (int i = 0; i < predictions.length; i++) {
			if (i % 8 == 0)
				res = res + "\n";
			res = res + predictions[i] + " ";
		}
		
		return res;
	}
}