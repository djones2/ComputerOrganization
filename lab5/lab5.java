/*

Daniel Jones & Anshula Singh
CPE 315 - 01
Lab 5

*/


import java.util.*;
import java.io.*;

public class lab5 {
	public static final String HELP = 
		"\nh = show help\n" +
		"d = dump register state\n" +
		"s = single step through the program (i.e. execute 1 instruction and stop)\n" +
		"s num = step through num instructions of the program\n" +
		"r = run until the program ends\n" +
		"m num1 num2 = display data memory from location num1 to num2\n" +
		"c = clear all registers, memory, and the program counter to 0\n" +
		"q = exit the program";
	
	public static final String START = "mips> ";
	public static ArrayList<Label> labels;
	public static MipsCPU mips;
	public static int numInstructionsExecuted = 0;
	public static String script = null;	
	public static int GHRsize = 2;
    
	public static void main(String[] args){
		File asm;
		String assembly;
		
		if (args.length > 0) {
			try {
				asm = new File(args[0]);			
				assembly = PassOne.formatFile(asm);			
				labels = Label.find(assembly);			
				
				if (args.length == 2){
					try{
						GHRsize = new Integer(args[1]);
					}
					catch (NumberFormatException ex){
						script = args[1];
					}
				}
				else if (args.length == 3){
					script = args[1];
					GHRsize = new Integer(args[2]);
				}

				mips = new MipsCPU(assembly, labels, GHRsize);			

				run();
			}
			catch (FileNotFoundException e) {
				System.out.println("A file could not be found.\n" + e);
			}
			catch (NumberFormatException e) {
				System.out.println("Invalud GHR size" + e);
			}
			catch (SyntaxException e) {
				System.out.println(e);
			}
		}
		else
			System.out.println("No asm file given.");
	}

	private static void run()
		throws FileNotFoundException {
		String input;
		String firstChar;
		Scanner inScan;
		Scanner lines;
		
		
		if (script == null) {
			inScan = new Scanner(System.in);
		}
		else
			inScan = new Scanner(new File(script));
		
		System.out.print(START);
				
		try {
			while (inScan.hasNextLine()) {
				input = inScan.nextLine();
				input = input.trim();
				
				if (script != null)
					System.out.println(input);
			
				if (input.compareTo("") != 0) {
					firstChar = input.substring(0, 1);
					firstChar = firstChar.toLowerCase();
			
					if (firstChar.compareTo("h") == 0) {
						System.out.println(HELP);
					}
					else if (firstChar.compareTo("d") == 0) {
						System.out.println("\npc = " + mips.getPC());
						System.out.println(mips.getRFString());
					}
					else if (firstChar.compareTo("s") == 0) {
						int step;
						
						try {
							lines = new Scanner(input);			
							lines.next();						
							if (lines.hasNext()) {
								step = new Integer(lines.next());
							}
							else {
								step = 1;
							}
							
							for (int i = 0; i < step; i++) {
								if (mips.executeSpim())
									numInstructionsExecuted++;
							}
							System.out.print("        " + numInstructionsExecuted
								+ " instruction(s) executed");
							numInstructionsExecuted = 0;
						}
						catch (NumberFormatException ex) {
							System.out.println("s <numSteps>");
						}
					}
					else if (firstChar.compareTo("r") == 0) {
						while(mips.executeSpim());
						numInstructionsExecuted = 0;
					}
					else if (firstChar.compareTo("m") == 0) {
						try {
							int startAddress;
							int endAddress;
					
							lines = new Scanner(input);
						
							lines.next();
							startAddress = new Integer(lines.next());
							endAddress = new Integer(lines.next());
						
							System.out.println(mips.getMemString(startAddress, endAddress));
						}
						catch (NoSuchElementException e) {
							System.out.println("m <startAddress> <endAddress>");
						}
						catch (NumberFormatException e) {
							System.out.println("m <startAddress> <endAddress>");
						}
					}
					else if (firstChar.compareTo("b")==0){
						int total, correct;
						double accuracy;
						
						correct = mips.getCorrectPredictions();
						total = mips.getBranches();
						accuracy = ((double)correct / (double) total) * 100;
						System.out.printf("accuracy %.2f%% (%d correct predictions, %d predictions)",
							accuracy, correct, total);
					}
					else if(firstChar.compareTo("o")==0){
						int[] coords;
						BufferedWriter writer;
						
						try {
							writer = new BufferedWriter(new FileWriter(new File("coordinates.csv")));
							coords = mips.coordinates();
						
							for (int i = 0; i < coords.length; i+=2) {
								writer.write(coords[i] + "," + coords[i+1] + "\n");
							}
							
							writer.flush();
							writer.close();
						}
						catch (IOException ex) {
							System.out.println("Could not write to file. " + ex);
						}
					}
					else if (firstChar.compareTo("x")==0){
						System.out.println(mips.predictionsToString());
					}
					else if (firstChar.compareTo("l")==0){
						System.out.println("Next instruction: \"" + mips.getNextInstruction()
							+ "\" at pc =" + 
							mips.getPC());
					}
					else if (firstChar.compareTo("l")==0){
						System.out.println("Labels");
						for(Label lab : labels)
							System.out.println(lab);
					}
					else if (firstChar.compareTo("c") == 0) {
						mips.reset();
						System.out.println("        Simulator reset");
					}
					else if (firstChar.compareTo("q") == 0) {
						return;
					}
					else if (firstChar.compareTo("a")==0){
						System.out.println(mips.imToString());
					}
					else if (firstChar.compareTo("r")==0){
						while(mips.executeSpim());
						numInstructionsExecuted = 0;
					}
					else {
						System.out.println("That is not a valid option. Enter \"h\" for help.");
					}
					if (firstChar.compareTo("r") != 0)
						System.out.print("\n");
				}
				
				System.out.print(START);
			}
		}
		catch (RegisterNotFoundException 
			| NumberFormatException | MemException | SyntaxException e) {
			System.out.println("Assembly line " + mips.getPC() + "\n   " + e);
		}
	}		
}

