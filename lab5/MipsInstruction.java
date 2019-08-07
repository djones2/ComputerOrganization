import java.util.*;

public class MipsInstruction {
	public static final int RTYPE = 0;
	public static final int ITYPE = 1;
	public static final int JTYPE = 2;
	public static final int NOTYPE = 3;
	public static final int STALL = 0;
	public static final int EMPTY = 1;
	public static final int SQUASH = 2;
	public static final int END = 2;
	
	public String op;
	public String rd;
	public String rs;
	public String rt;
	public String imm;
	public String shamt;
	public String c;
	public boolean end;
	public int pc;


	public MipsInstruction(int type) {
		if (type == STALL) {
			op = "stall";
			end = false;
		}
		else if (type == EMPTY) {
			op = "empty";
			end = false;
		}
		else if (type == SQUASH) {
			op = "squash";
			end = false;
		}
		else if (type == END){
			op = "empty";
			end = true;
		}
		else {
			this.op = "";
			end = false;
		}
		rd = rs = rt = imm = shamt = c = "";
		pc = -1;
	}
	
	public MipsInstruction(int type, int pc) {
		if (type == END){
			op = "empty";
			end = true;
			this.pc = pc;
		}
		else {
			this.op = "";
			end = false;
		}
		rd = rs = rt = imm = shamt = c = "";
	}
	
	public MipsInstruction(String instruction, int pc) throws SyntaxException, 
		MemException, NoSuchElementException, RegisterNotFoundException,
			NumberFormatException {
		Scanner scanner;
		String operation;		
		
		op = rd = rs = rt = imm = shamt = c = "";
		this.pc = pc;
		end = false;
		
		instruction = instruction.trim();
		
		instruction = PassOne.removeLabels(instruction);
	
		instruction = instruction.replace("(", " ");
		instruction = instruction.replace(")", " ");
		instruction = instruction.replace(",", " ");
	
		if (instruction.compareTo("") == 0) {
			op = "empty";
			end = true;
			return;
		}
		scanner = new Scanner(instruction);

		operation = scanner.next();

		operation = operation.trim();
		operation = operation.toLowerCase();
		op = operation;

		if (operation.compareTo("and") == 0
			|| operation.compareTo("or") == 0
			|| operation.compareTo("add") == 0
			|| operation.compareTo("slt") == 0
			|| operation.compareTo("sub") == 0) {	
			
			rd = scanner.next();
			rs = scanner.next();
			rt = scanner.next();
		}
		else if (operation.compareTo("addi") == 0) {
			rt = scanner.next();
			rs = scanner.next();
			imm = scanner.next();
		}
		else if (operation.compareTo("sll") == 0) {
			rd = scanner.next();
			rt = scanner.next();
			shamt = scanner.next();
		}
		else if (operation.compareTo("beq") == 0
			|| operation.compareTo("bne") == 0) {
			rt = scanner.next();
			rs = scanner.next();
			c = scanner.next();
		}
		else if (operation.compareTo("lw") == 0
			|| operation.compareTo("sw") == 0) {
			rt = scanner.next();
			c = scanner.next();
			rs = scanner.next();
		}
		else if (operation.compareTo("j") == 0
		|| operation.compareTo("jal") == 0) {
			c = scanner.next();
		}
		else if (operation.compareTo("jr") == 0) {
			rs = scanner.next();
		}
		else
			throw new SyntaxException("Unknown isntruction: " + operation);
			
		trimAll();
	}
	
	public int getType() throws SyntaxException {
		if (op == null)
			throw new SyntaxException("No type for null instruction.");
		if (op == "")
			throw new SyntaxException("No type for blank instruction.");
		
		if (op.compareTo("and") == 0)
			return RTYPE;
		else if (op.compareTo("or") == 0)
			return RTYPE;
		else if (op.compareTo("add") == 0)
			return RTYPE;
		else if (op.compareTo("addi") == 0)
			return ITYPE;
		else if (op.compareTo("sll") == 0)
			return ITYPE;
		else if (op.compareTo("sub") == 0)
			return RTYPE;
		else if (op.compareTo("slt") == 0)
			return RTYPE;
		else if (op.compareTo("beq") == 0)
			return ITYPE;
		else if (op.compareTo("bne") == 0)
			return ITYPE;
		else if (op.compareTo("sw") == 0)
			return ITYPE;
		else if (op.compareTo("lw") == 0)
			return ITYPE;
		else if (op.compareTo("j") == 0)
			return JTYPE;
		else if (op.compareTo("jr") == 0)
			return RTYPE;
		else if (op.compareTo("jal") == 0)
			return JTYPE;
		else if (op.compareTo("empty") == 0)
			return NOTYPE;
		else if (op.compareTo("stall") == 0)
			return NOTYPE;
		else if (op.compareTo("squash") == 0)
			return NOTYPE;
		else
			throw new SyntaxException("Could not find type for " + op + ".");
	}

	public  boolean isMemAccess() {
		if (op.compareTo("sw") == 0)
			return true;
		else if (op.compareTo("lw") == 0)
			return true;
			
		return false;
	}
	
	public boolean isBranch() {
		if (op.compareTo("beq") == 0)
			return true;
		else if (op.compareTo("bne") == 0)
			return true;
			
		return false;
	}
	
	public boolean isShift() {
		if (op.compareTo("sll") == 0)
			return true;
		
		return false;
	}
	
	public boolean isJump() {
		if (op.compareTo("j") == 0)
			return true;
		else if (op.compareTo("jr") == 0)
			return true;
		else if (op.compareTo("jal") == 0)
			return true;
			
		return false;
	}
	
	public boolean doNothing() {
		if (op.compareTo("empty") == 0
			|| op.compareTo("stall") == 0
			|| op.compareTo("squash") == 0)
			return true;
			
		return false;
	}
	
	public boolean checkSquash() {
		return op.compareTo("stall") != 0;
	}
	
	public String toString() {
		return op;
	}
	
	public void squash() {
		op = "squash";
		end = false;
	}
	
	private void trimAll() {
		if (op != null)
			op = op.trim();
		if (rd != null)
			rd = rd.trim();
		if (rs != null)
			rs = rs.trim();
		if (rt != null)
			rt = rt.trim();
		if (imm != null)
			imm = imm.trim();
		if (shamt != null)
			shamt = shamt.trim();
		if (c != null)
			c = c.trim();
	}
}