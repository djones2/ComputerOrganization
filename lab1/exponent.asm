# Name: Daniel Jones & Anshula Singh
# Section: 01
# Description: This program raises a number to the power of another
# Java code:
#	public int exponent(int a, int b){
#		
#		int c = 1;
#		int res = 0;
#		res = a;
#
#		if (b == 0) { return 1; }
#
#		for (c=1; c<b; c++) { 
#			res = multiply(res, a);
#		}
#		return res;
#   }
#
#	public int multiply(int d, int e) {
#		
#		int f = 0;
#		int result = 0;
#	
#		for (f=0; f<e; f++){
#			result += d;
#		}
#		return result;
#	}
#		

# Declare global for programmer to see addresses
.globl welcome
.globl prompt
.globl prompt2
.globl result

# Data area
.data

welcome:
	.asciiz " This program raises one number to a power of another \n\n"

prompt: 
	.asciiz " Enter an integer: "

prompt2:
	.asciiz " Enter a power: "

result:
	.asciiz " \n Result: "

# Instructions
.text

main:
	# Display welcome message
	ori	$v0, $0, 4
	
	# Starting address for welcome message
	lui	$a0m 0x1001
	syscall
	
	# Display prompt
	ori	$v0, $0, 5
	
	# Starting address of prompt
	lui	$a0, 0x1001
	ori	$a0, $a0, 0x38
	syscall

	# Read integer from user
	ori	$v0, $0, 5
	syscall
	addu	$s1, $v0, $0
	
	# Display prompt 2
	ori	$v0, $0, 5
	
	# Starting address of prompt2
	lui	$a0, 0x1001
	ori	$a0, $a0, 0x4C
	syscall

	# Read the power 
	ori	$v0, $0, 5
	syscall
	addu	$s2, $v0, $0

	# Display the result text
	ori	$v0, $0, 4
	
	# Starting address of result text
	lui	$a0, 0x1001
	ori	$a0, $a0, 0x5C
	syscall
	
	# Take integer and power as inputs to multiply
	add	$a0, $s1, $0
	add	$a1, $s2, $0
	jal	exponent

	add   $a0, $s1, $0
	
	# Display the FINAL result (1?)
	ori	$v0, $0, 1
	add	$a0, $s1, $0
	syscall
	
	# Exit
	ori	$v0, $0, 10
	syscall


