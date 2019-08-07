public class Instruction {
	/* Creating each instruction from label */

	public static final int RTYPE = 0;
	public static final int ITYPE = 1;
	public static final int JTYPE = 2;

	public static String opCode(String instruction) throws BadInstructionException {
		instruction = instruction.toLowerCase();
		instruction = instruction.trim();
		
		/* 6-bit OpCode */
		
		if (instruction.compareTo("and") == 0)
			return "000000";
		else if (instruction.compareTo("or") == 0)
			return "000000";
		else if (instruction.compareTo("add") == 0)
			return "000000";
		else if (instruction.compareTo("addi") == 0)
			return "001000";
		else if (instruction.compareTo("sll") == 0)
			return "000000";
		else if (instruction.compareTo("sub") == 0)
			return "000000";
		else if (instruction.compareTo("slt") == 0)
			return "000000";
		else if (instruction.compareTo("beq") == 0)
			return "000100";
		else if (instruction.compareTo("bne") == 0)
			return "000101";
		else if (instruction.compareTo("sw") == 0)
			return "101011";
		else if (instruction.compareTo("lw") == 0)
			return "100011";
		else if (instruction.compareTo("j") == 0)
			return "000010";
		else if (instruction.compareTo("jr") == 0)
			return "000000";
		else if (instruction.compareTo("jal") == 0)
			return "000011";
		else
			throw new BadInstructionException("Could not find opCode for " + instruction + ".");
	}

	/* Get the function from the string passed */
	public static String getFunction(String instruction) throws BadInstructionException {
		instruction = instruction.toLowerCase();
		instruction = instruction.trim();
		
		if (instruction.compareTo("and") == 0)
			return "100100";
		else if (instruction.compareTo("or") == 0)
			return "100101";
		else if (instruction.compareTo("add") == 0)
			return "100000";
		else if (instruction.compareTo("addi") == 0)
			return "";
		else if (instruction.compareTo("sll") == 0)
			return "000000";
		else if (instruction.compareTo("sub") == 0)
			return "100010";
		else if (instruction.compareTo("slt") == 0)
			return "101010";
		else if (instruction.compareTo("beq") == 0)
			return "";
		else if (instruction.compareTo("bne") == 0)
			return "";
		else if (instruction.compareTo("sw") == 0)
			return "";
		else if (instruction.compareTo("lw") == 0)
			return "";
		else if (instruction.compareTo("j") == 0)
			return "";
		else if (instruction.compareTo("jr") == 0)
			return "001000";
		else if (instruction.compareTo("jal") == 0)
			return "";
		else
			throw new BadInstructionException("No opCode available for " + instruction + ".");
	}
	/* Determine if the instruction is R,I, or J type */
	public static int type(String instruction) throws BadInstructionException {
		instruction = instruction.toLowerCase();
		instruction = instruction.trim();
		
		if (instruction.compareTo("and") == 0)
			return RTYPE;
		else if (instruction.compareTo("or") == 0)
			return RTYPE;
		else if (instruction.compareTo("add") == 0)
			return RTYPE;
		else if (instruction.compareTo("addi") == 0)
			return ITYPE;
		else if (instruction.compareTo("sll") == 0)
			return ITYPE;
		else if (instruction.compareTo("sub") == 0)
			return RTYPE;
		else if (instruction.compareTo("slt") == 0)
			return RTYPE;
		else if (instruction.compareTo("beq") == 0)
			return ITYPE;
		else if (instruction.compareTo("bne") == 0)
			return ITYPE;
		else if (instruction.compareTo("sw") == 0)
			return ITYPE;
		else if (instruction.compareTo("lw") == 0)
			return ITYPE;
		else if (instruction.compareTo("j") == 0)
			return JTYPE;
		else if (instruction.compareTo("jr") == 0)
			return RTYPE;
		else if (instruction.compareTo("jal") == 0)
			return JTYPE;
		else
			throw new BadInstructionException("No available type for " + instruction + ".");
	
	}
	
	/* If it uses a shift amount, compare to sll */
	public static boolean usesSHAMT(String instruction) {
		if (instruction.compareTo("sll") == 0)
			return true;
		
		return false;
	}
	
	/* See  if instruction is branch */
	public static boolean isBranch(String instruction) {
		instruction = instruction.toLowerCase();
		instruction = instruction.trim();
		
		if (instruction.compareTo("beq") == 0)
			return true;
		else if (instruction.compareTo("bne") == 0)
			return true;
		return false;
	}
	/* See if the instruction accesses memory*/
	public static boolean isMemoryAccess(String instruction) {
		instruction = instruction.toLowerCase();
		instruction = instruction.trim();
		
		if (instruction.compareTo("sw") == 0)
			return true;
		else if (instruction.compareTo("lw") == 0)
			return true;
		return false;
	}
    /* Final check for a jumpo register */
	public static boolean isJumpRegister(String instruction) {
		instruction = instruction.toLowerCase();
		instruction = instruction.trim();
		
		if (instruction.compareTo("jr") == 0)
			return true;
		return false;
	}
}