public class BinaryCodes {
    /* Binary codes for the 31-bit register value of each instruction */
	public static String getRegCode(String reg) throws RegisterNotFoundException {
		reg = reg.trim();
		reg = reg.toLowerCase();
		
		if (reg.charAt(reg.length() - 1) == ',')
			reg = reg.substring(0, reg.length() - 1);
		/* 5-bit register values */
		if (reg.compareTo("$zero") == 0)
			return "00000";
		if (reg.compareTo("$0") == 0)
			return "00000";
		else if (reg.compareTo("$v0") == 0)
			return "00010";
		else if (reg.compareTo("$v1") == 0)
			return "00011";
		else if (reg.compareTo("$a0") == 0)
			return "00100";
		else if (reg.compareTo("$a1") == 0)
			return "00101";
		else if (reg.compareTo("$a2") == 0)
			return "00110";
		else if (reg.compareTo("$a3") == 0)
			return "00111";
		else if (reg.compareTo("$t0") == 0)
			return "01000";
		else if (reg.compareTo("$t1") == 0)
			return "01001";
		else if (reg.compareTo("$t2") == 0)
			return "01010";
		else if (reg.compareTo("$t3") == 0)
			return "01011";
		else if (reg.compareTo("$t4") == 0)
			return "01100";
		else if (reg.compareTo("$t5") == 0)
			return "01101";
		else if (reg.compareTo("$t6") == 0)
			return "01110";
		else if (reg.compareTo("$t7") == 0)
			return "01111";
		else if (reg.compareTo("$t8") == 0)
			return "11000";
		else if (reg.compareTo("$t9") == 0)
			return "11001";
		else if (reg.compareTo("$s0") == 0)
			return "10000";
		else if (reg.compareTo("$s1") == 0)
			return "10001";
		else if (reg.compareTo("$s2") == 0)
			return "10010";
		else if (reg.compareTo("$s3") == 0)
			return "10011";
		else if (reg.compareTo("$s4") == 0)
			return "10100";
		else if (reg.compareTo("$s5") == 0)
			return "10101";
		else if (reg.compareTo("$s6") == 0)
			return "10110";
		else if (reg.compareTo("$s7") == 0)
			return "10111";
		else if (reg.compareTo("$ra") == 0)
			return "11111";
		else if (reg.compareTo("$sp") == 0)
			return "11101";
		throw new RegisterNotFoundException("No available register for " + reg);
	}
}
