import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//To DO
//This is all in BinaryCodes file 
//-compute immediate addresses 
//-compute shift amount for SLL
//-compute where to jump from labels/immediate values 
//write the full stuff to be able to 

public class Lab2 {


    /*
    *first pass of file that puts labels into hash map 
    *it also puts all valid instruction lines into an arraylist of instructions 
    */


/*	private static void passOne(File name) throws FileNotFoundException{
		ArrayList<Instruction> instrsList = new ArrayList<Instruction>();  
		Map<Label, Integer> labels = new HashMap<Label, Integer>();
		
		Scanner scanner = new Scanner(name);
		int lineNum = 0; //correlates to address
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if( (line = line.trim()).length() > 0 ) {
				line = line.replace(",", " "); //following lines are meant to make it easier for me
				line = line.replace("$", " "); //to put it into a uniform arraylist
				line = line.replace("\t", " "); //so that i know what maps to what
				line = line.toLowerCase(); //doesn't matter the case, just want uniform
				

				//doesn't create specific label though 
				if (line.indexOf(':') > -1) {
					Label newLabel = new Label((line.substring(0, line.indexOf(':'))), lineNum);
					labels.put(newLabel, lineNum);
				
				}
				line = makeInstruction(line); //removes all comments and labels from the line.

				if(line.length() > 0)
				{
					Instruction instruction = new Instruction(line, lineNum, labels, 'a');
					instrsList.add(instruction);
					++ lineNum; //we only iterate when there's a valid line number 
				}
			}
			passTwo(name, instrsList, labels);

			

		}
		
		System.out.println("***********************");
		System.out.println(Arrays.asList(labels));
		System.out.println("***********************");
		System.out.println(instrsList);
		

	}
	
	private static void passTwo(File name, ArrayList<Instruction> instrList, Map<Label,Integer> labels) {
		//has access to label arraylist of instructions as well as the hash map of labels 
		//now you have to go back and compute the jump instructions and what not 
		
		for(int i = 0; i < instrList.size(); i++) {
			if (instrList.get(i).getType() == 'E') {
				//idk what printf does 
				System.out.printf("invalid instruction: %s\n", instrList.get(i).getInstr() );
				return;
			}
			//this is still not complete and needs a proper outfile 
			instrList.get(i).binaryCodes();
			
		}
	}


	//method to remove label because it's not needed for instruction lines
	private static String stripLabel(String line)
	{
		int labelIdx = line.indexOf(':'); 
		if(labelIdx > -1)
		{
			line = line.substring(labelIdx + 1, line.length());
		}
		return line;
	}

    //method to remove comment because it's also unnecessary 
	private static String stripComment(String line)
	{ 
		int commentIdx = line.indexOf('#'); 
		if(commentIdx > -1)
		{
			line = line.substring(0, commentIdx);
		}

		return line;
	}

	//instructions don't have either of the earlier 
	private static String makeInstruction(String line)
	{
		line = stripLabel(line).trim();
		line =  stripComment(line).trim();
		return line;
	}

	//argument checker for command line 
	private static String fileArgs(String[] args) throws FileNotFoundException {
		if (args.length < 1) {
			System.out.println("Improper file usage, please print file");
			System.exit(1);
		}
		return args[0];
	}

	My dearest Anshula,

		Thank you for getting these functions down!
		I used most everything in the PassOne and PassTwo 
		files. Only minor change I made was in storing the
		registers and instructions in strings. I 
		also added classes for exception catching, I copied
		those from a 203 class, who knew it would help.
		I added a lot of trimming/finding helper functions,
		I think those help in certain instances rather than
		having a generic strip one, but it all works! Feel 
		free to double check. Also, I think the Makefile is
		correctly formatted? Otherwise, should be good to
		go. You rock! 

		- Daniel
*/


	public static void main(String[] args){
		
		String formattedFile;

		if (args.length < 1){
			System.out.println("Bad argument");
			return;
		}
		File file = new File(args[0]);
		if(!file.exists()){
			System.out.println("Could not locate file " + file.getName() + ".");
			return;
		}
		try {
			formattedFile = PassOne.formatFile(file);
			PassTwo.formatFile(formattedFile);
		} catch (Exception e) {
			System.out.println("Caught " + e + " during assembly.");
		}	
	}
}

