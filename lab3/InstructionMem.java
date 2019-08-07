import java.util.*;

public class InstructionMem {
	protected ArrayList<String> mem;

	public InstructionMem(String assembly){
		mem = new ArrayList<String>();
		Scanner scan;

		if(assembly.compareTo("") != 0 && assembly != null){
			scan = new Scanner(assembly);
			while(scan.hasNextLine()) {
				mem.add(scan.nextLine());
			}

		}
	}

	public String toString(){
		String ret;		
		ret = "";
		for (String i:mem){
			ret = ret + i + "\n";
		}	
		return ret;
	}

	public int size(){
		return mem.size();
	}

	public String getAddress(int addr) throws MemException {
		if(addr<0 || addr > ((mem.size())-1))
			throw new MemException("Incorrect Instruction Address: " + addr);
		return mem.get(addr);
	}
}