import java.util.*;

public class MipsCPU {
	private MipsMemory mem = new MipsMemory(8192);
	private RegFile rf = new RegFile();
	private InstructionMem im;
	private ArrayList<Label> labels;
	private int pc = 0;

	public MipsCPU(String assembly, ArrayList<Label> labels){
		mem = new MipsMemory(8192);
		im = new InstructionMem(assembly);
		rf = new RegFile();
		this.labels = labels; //I think??
		reset();
	}

	public void reset(){
		pc = 0;
		mem.clearMemory();
		rf.clearMemory();
	}

	boolean executeSpim() throws
	MemException, NoSuchElementException, RegisterNotFoundException,
	NumberFormatException, SyntaxException{
		int labelIndx;
		String operation;
		Scanner scanner;
		String instr;
		if (pc > (im.size()-1))
			return false;
		instr = im.getAddress(pc);
		instr = instr.trim();
		instr = PassOne.removeLabels(instr);
		instr = instr.replace("("," ");
		instr = instr.replace(")"," ");
		if (instr.compareTo("")==0)
			return false;
		scanner = new Scanner(instr);
		operation = scanner.next();
		operation = operation.trim();
		operation = operation.toLowerCase();

		if (operation.compareTo("and") == 0) {
			String rd, rs, rt;
	
			rd = scanner.next();
			rs = scanner.next();
			rt = scanner.next();
	
			rf.set(rd, rf.get(rs) & rf.get(rt));
		}
		else if (operation.compareTo("add") == 0) {
			String rd, rs, rt;
	
			rd = scanner.next();
			rs = scanner.next();
			rt = scanner.next();
	
			rf.set(rd, rf.get(rs) + rf.get(rt));
		}
		else if (operation.compareTo("addi") == 0) {
			String rt, rs, imm;
	
			rt = scanner.next();
			rs = scanner.next();
			imm = scanner.next();
	
			rf.set(rt, rf.get(rs) + new Integer(imm));

		}
		else if (operation.compareTo("or") == 0) {
			String rd, rs, rt;
	
			rd = scanner.next();
			rs = scanner.next();
			rt = scanner.next();
	
			rf.set(rd, rf.get(rs) | rf.get(rt));

		}
		else if (operation.compareTo("sll") == 0) {
			String rd, rt, shamt;
			Integer shiftAmount;
	
			rd = scanner.next();
			rt = scanner.next();
			shamt = scanner.next();	
			shiftAmount = new Integer(shamt);
	
			if (shiftAmount < 0 || shiftAmount > 31)
				throw new SyntaxException("Illegal shift amount: " + shiftAmount);
	
			rf.set(rd, rf.get(rt) << shiftAmount);
		}
		else if (operation.compareTo("sub") == 0) {
			String rd, rs, rt;
	
			rd = scanner.next();
			rs = scanner.next();
			rt = scanner.next();
	
			rf.set(rd, rf.get(rs) - rf.get(rt));
		}
		else if (operation.compareTo("slt") == 0) {
			String rd, rs, rt;
	
			rd = scanner.next();
			rs = scanner.next();
			rt = scanner.next();
	
			if (rf.get(rs) < rf.get(rt))
				rf.set(rd, 1);
			else
				rf.set(rd, 0);
		}
		else if (operation.compareTo("beq") == 0) {
			String rt, rs, C;
			int index, address;
	
			rt = scanner.next();
			rs = scanner.next();
			C = scanner.next();
	
			index = labels.indexOf(new Label(C.trim(), 0));
	
			if (index == -1)
				address = new Integer(C);
			else
				address = labels.get(index).getRelativeLineNumber(pc);
	
			address += pc;
	
			if (address >= 0 && address < im.size()) {
				if (rf.get(rs) == rf.get(rt))
					pc = address;	
			}
			else
				throw new SyntaxException("Branch to address outside of program: " + address);
		}
		else if (operation.compareTo("bne") == 0) {
			String rt, rs, C;
			int index, address;
	
			rt = scanner.next();
			rs = scanner.next();
			C = scanner.next();
	
			index = labels.indexOf(new Label(C.trim(), 0));
	
			if (index == -1)
				address = new Integer(C);
			else
				address = labels.get(index).getRelativeLineNumber(pc);
	
			address += pc;
	
			if (address >= 0 && address < im.size()) {
				if (rf.get(rs) != rf.get(rt))
					pc = address;	
			}
			else
				throw new SyntaxException("Branch to address outside of program: " + address);			
		}
		else if (operation.compareTo("lw") == 0) {
			String rt, C, rs;
			int address;
	
			rt = scanner.next();
			C = scanner.next();
			rs = scanner.next();
	
			address = rf.get(rs) + new Integer(C);
	
			rf.set(rt, mem.get(address));
		}
		else if (operation.compareTo("sw") == 0) {
			String rt, C, rs;
			int address;
	
			rt = scanner.next();
			C = scanner.next();
			rs = scanner.next();
	
			address = rf.get(rs) + new Integer(C);
	
			mem.set(address, rf.get(rt));
		}
		else if (operation.compareTo("j") == 0) {
			String C;
			int index, address;
			C = scanner.next();
	
			index = labels.indexOf(new Label(C.trim(), 0));
	
			if (index == -1)
				address = new Integer(C);
			else
				address = labels.get(index).getAbsoluteLineNumber();
	
			if (address >= 0 && address < im.size()) {
				pc = address - 1;	
			}
			else
				throw new SyntaxException("Jump to address outside of program: " + address);
	
		}
		else if (operation.compareTo("jr") == 0) {
			String rs;
			int address;
			rs = scanner.next();
	
			address = rf.get(rs);
	
			if (address >= 0 && address < im.size()) {
				pc = address - 1;	
			}
			else
				throw new SyntaxException("Jump to address outside of program: " + address);
		}
		else if (operation.compareTo("jal") == 0) {
			String C;
			int index, address;
			C = scanner.next();
	
			index = labels.indexOf(new Label(C.trim(), 0));
	
			if (index == -1)
				address = new Integer(C);
			else
				address = labels.get(index).getAbsoluteLineNumber();
	
			if (address >= 0 && address < im.size()) {
				rf.set("$ra", pc+1);
				pc = address - 1;	
			}
			else
				throw new SyntaxException("Jump to address outside of program: " + address);
		}
		else
			throw new SyntaxException("Unknown isntruction: " + operation);
	
		pc++;
	
		return true;
	}

	public String imToString(){
		return im.toString();
	}

	public String getMemString(int b, int a) {
		return mem.toString(b, a);
	}
	
	public String getRFString() {
		return rf.toString();
	}
	
	public int getPC() {
		return pc;
	}

	public String getNextInstruction(){
		try {
			return im.getAddress(pc);
		}
		catch (MemException e){
			return "No more instructions left to execute.";
		}
	}

}
