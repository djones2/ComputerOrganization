public class MipsMemory {
	protected Integer[] mipsMemory;
	
	public MipsMemory(int size) {
		mipsMemory = new Integer[size];
		clearMemory();
	}

	public void clearMemory() {
		for (int i = 0; i < mipsMemory.length; i++) {
			mipsMemory[i] = 0;
		}
	}
	
	
	public int get(int address) throws MemException {
		if (address >= mipsMemory.length || address < 0)
			throw new MemException("Invalid Address.");		
		return mipsMemory[address];
	}
	
	public void set(int address, int value) throws MemException {
		if (address < 0 || address >= mipsMemory.length)
			throw new MemException("Invalid Address.");
		mipsMemory[address] = value;
	}
	
	public String toString(int start, int end){
		String mem;		
		mem = "";	
		if (start < 0 || start >= mipsMemory.length
			||  end < 0 || end >= mipsMemory.length || start > end)
		{
			mem = "Invalid Addresses";
		}
		else {
			for (int i = start; i <= end; i++) {
				mem = mem + "\n[" + i + "] = " + mipsMemory[i];
			} 
		}
		return mem;
	}
}