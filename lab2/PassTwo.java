import java.util.*;
import java.io.*;

public class PassTwo {
	
	/* Second pass through the now ideal formatted 
	assembly file.*/

	/* Pass line to line and operate on each label */
	public static void formatFile(String input) {
		Scanner scan = new Scanner(input);
		ArrayList<Label> labels;
		String currentLine;
		String outCode;
		int line;
		
		labels = findLabels(input);
		
		line = 0;
		while (scan.hasNextLine()) {
			currentLine = scan.nextLine();
			try {
				outCode = decodeAssembly(currentLine, line, labels);
					System.out.println(outCode);
			}
			catch (Exception e) {
				System.out.println(e);
			}
			line++;
		}
	}

	/* Match the correct label to the instruction set.
	   Create the string based on the current line.
	   Traverse line-by-line. */

	public static ArrayList<Label> findLabels(String input) {
		ArrayList<Label> labels;
		int line;
		Scanner scan;
		String currentLine;		
		int index;
		
		line = 0;	
		labels = new ArrayList<Label>();
		scan = new Scanner(input);
		
		while (scan.hasNextLine()) {
			currentLine = scan.nextLine();
			index = currentLine.indexOf(":");
			if (index != -1)
				labels.add(new Label(currentLine.substring(0, index), line));
			line++;
		}
		return labels;
	}
	
	/* Translate the given label and registers on the 
	   line and decode assembly language to machine code (outCode)*/

	public static String decodeAssembly(String currentLine, int line,
		ArrayList<Label> labels) 
		throws BadInstructionException, RegisterNotFoundException, SyntaxException {
			String instruction;
			String rd;
			String rs;
			String rt;
			String immediate;
			String outCode;
			Scanner scan;
			Integer immedVal;
			String immedStr;
			Label label;
			int type;
			int index;
			int labelLine;
			outCode = "";
			index = currentLine.indexOf(":");
			
			if (index != -1)
				currentLine = currentLine.substring(index + 1);
			
			scan = new Scanner(currentLine);
			
			if (!scan.hasNext())
				return outCode;
			
			instruction = scan.next();
			
			type = Instruction.type(instruction);
			
			outCode = Instruction.opCode(instruction);
			
			try {
				if (type == Instruction.RTYPE) {
					if (Instruction.isJumpRegister(instruction)) {
						rs = scan.next();
						
						outCode = outCode + " " + BinaryCodes.getRegCode(rs);
						outCode = outCode + " 000000000000000";
						outCode = outCode + " " + Instruction.getFunction(instruction);
					}
					else {
						rd = scan.next();
						rs = scan.next();
						rt = scan.next();
					
						if (Instruction.usesSHAMT(instruction))
							outCode = outCode + " 00000";
						else
							outCode = outCode + " " + BinaryCodes.getRegCode(rs);
						
						outCode = outCode + " " + BinaryCodes.getRegCode(rt);
					
						outCode = outCode + " " + BinaryCodes.getRegCode(rd);
						
						if (Instruction.usesSHAMT(instruction))
							outCode = outCode + " " + Integer.toBinaryString(new Integer(rs));
						else
							outCode = outCode + " 00000";
					
						outCode = outCode + " " + Instruction.getFunction(instruction);
					}
				}
				else if (type == Instruction.ITYPE) {
					rt = scan.next();
					rs = scan.next();
						
					if (Instruction.isBranch(instruction)) {
						immediate = scan.next();
						outCode = outCode + " " 
							+ BinaryCodes.getRegCode(rt);
					
						outCode = outCode + " " 
							+ BinaryCodes.getRegCode(rs);
							
						index = labels.indexOf(new Label(immediate, 0));
						label = labels.get(index);
						labelLine = label.getRelativeLineNumber(line);
						
						immedVal = new Integer(labelLine);
						immedStr = Integer.toBinaryString(immedVal);
					
						for (int i = immedStr.length(); i < 16; i++)
							immedStr = "0" + immedStr;
						
						for (int i = immedStr.length(); i > 16; i--)
							immedStr = immedStr.substring(1, immedStr.length());
					
						outCode = outCode + " " + immedStr;
					}
					else if (Instruction.isMemoryAccess(instruction)) {
						index = rs.indexOf("(");
						if (index == -1)
							throw new SyntaxException("No \"(\" found in a lw or sw.");
						
						immediate = rs.substring(0, index);
						rs = rs.substring(index + 1, rs.length() - 1);
						
						outCode = outCode + " " + BinaryCodes.getRegCode(rs);
						outCode = outCode + " " + BinaryCodes.getRegCode(rt);
						
						immedVal = new Integer(immediate);
						immedStr = Integer.toBinaryString(immedVal);
					
						for (int i = immedStr.length(); i < 16; i++)
							immedStr = "0" + immedStr;
						
						for (int i = immedStr.length(); i > 16; i--)
							immedStr = immedStr.substring(1, immedStr.length());
					
						outCode = outCode + " " + immedStr;
					}
					else {
						immediate = scan.next();
						outCode = outCode + " " 
							+ BinaryCodes.getRegCode(rs);
					
						outCode = outCode + " " 
							+ BinaryCodes.getRegCode(rt);
						
						immedVal = new Integer(immediate);
						immedStr = Integer.toBinaryString(immedVal);
					
						for (int i = immedStr.length(); i < 16; i++)
							immedStr = "0" + immedStr;
						
						for (int i = immedStr.length(); i > 16; i--)
							immedStr = immedStr.substring(1, immedStr.length());
					
						outCode = outCode + " " + immedStr;
					}
				}
				else if (type == Instruction.JTYPE) {
					index = labels.indexOf(new Label(scan.next(), 0));
					label = labels.get(index);
					labelLine = label.getAbsoluteLineNumber();
					immedVal = new Integer(labelLine);
					immedStr = Integer.toBinaryString(immedVal);
					
					for (int i = immedStr.length(); i < 26; i++)
						immedStr = "0" + immedStr;
					
					outCode = outCode + " " + immedStr;
				}
				else
					throw new SyntaxException("Bad Instruction " + instruction);
			}
			catch (NumberFormatException e) {
				outCode = "Bad Immed. Value";
			}
			catch (NoSuchElementException | IllegalStateException e) {
				outCode = "Could not read registers";
			}
			
			return outCode;
	}
}


