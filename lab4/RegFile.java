import java.util.*;

public class RegFile extends MipsMemory {
	public static final Map<String, Integer> REGISTERS;
    static
    {
        REGISTERS = new HashMap<String, Integer>();
        REGISTERS.put("$zero", 0);
        REGISTERS.put("$0", 0);
        REGISTERS.put("$v0", 2);
        REGISTERS.put("$v1", 3);
        REGISTERS.put("$a0", 4);
        REGISTERS.put("$a1", 5);
        REGISTERS.put("$a2", 6);
        REGISTERS.put("$a3", 7);
        REGISTERS.put("$t0", 8);
        REGISTERS.put("$t1", 9);
        REGISTERS.put("$t2", 10);
        REGISTERS.put("$t3", 11);
        REGISTERS.put("$t4", 12);
        REGISTERS.put("$t5", 13);
        REGISTERS.put("$t6", 14);
        REGISTERS.put("$t7", 15);
        REGISTERS.put("$s0", 16);
        REGISTERS.put("$s1", 17);
        REGISTERS.put("$s2", 18);
        REGISTERS.put("$s3", 19);
        REGISTERS.put("$s4", 20);
        REGISTERS.put("$s5", 21);
        REGISTERS.put("$s6", 22);
        REGISTERS.put("$s7", 23);
        REGISTERS.put("$t8", 24);
        REGISTERS.put("$t9", 25);
        //REGISTERS.put("$s8", 26); 
        //REGISTERS.put("$s9", 27);
        REGISTERS.put("$gp", 28);
        REGISTERS.put("$sp", 29);
        REGISTERS.put("$fp", 30);
        REGISTERS.put("$ra", 31);
    }

    public static final int NUM_MIPS_REGISTERS = 32;
    
    private static final String[] MIPS_REGISTERS = {
    	"$0", "$v0", "$v1","$a0", "$a1", "$a2", "$a3", "$t0", "$t1", "$t2", "$t3", "$t4", "$t5", "$t6",
    	"$t7", "$s0", "$s1", "$s2", "$s3", "$s4", "$s5", "$s6", "$s7", "$t8", "$t9", "$sp", "$ra" 
    	};
	
	public RegFile() {
		super(NUM_MIPS_REGISTERS);
	}

	public String toString() {
		String returnRegister;
		String register;
		int indent;
		
		returnRegister = "";
		indent = 0;
		
		for (int i = 0; i < MIPS_REGISTERS.length; i++) {
			register = MIPS_REGISTERS[i];
			returnRegister = returnRegister + register + " = " + mipsMemory[REGISTERS.get(register)];
			
			if (i == 0) {
				returnRegister = returnRegister + " ";
			}
			if (indent < 3) {
				if (i != MIPS_REGISTERS.length - 1)
					returnRegister = returnRegister + "         ";
				indent++;
			}
			else if (i < MIPS_REGISTERS.length - 1) {
				returnRegister = returnRegister + "\n";
				indent = 0;
			}
		}
		
		return returnRegister;
	}
	
	public int get(String register) throws RegisterNotFoundException {
		Integer index;
		
		register = register.toLowerCase();
		register = register.replace(",", " ");
		register = register.trim();
		
		index = REGISTERS.get(register);
		
		if (index == null)
			throw new RegisterNotFoundException("No register named \"" + register + "\"");
		
		return mipsMemory[index];
	}
	
	public void set(String regName, int val) throws RegisterNotFoundException {
		Integer index;
		
		regName = regName.toLowerCase();
		regName = regName.replace(",", " ");
		regName = regName.trim();
		
		index = REGISTERS.get(regName);
		
		if (index == null)
			throw new RegisterNotFoundException("No register named \"" + regName + "\"");
		
		mipsMemory[index] = val;
	}
}