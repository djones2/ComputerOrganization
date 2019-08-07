# Name: Daniel Jones & Anshula Singh
# Section: 01
# Description: This program divides as 64-bit unsigned number with
#					a 31-bit unsigned number (power of 2)
# Java Code:
##############################################################
#		public int divide(int upper, int low, int div) {
#			int carry = 0;
#			while (div != 0) {
#				carry = upper & 1;  // Take in 31 bits 
#				upper = upper>>1;   // Shift upper to make 31 bits				
#				carry = carry<<31;  // Move upper to top 31 bits of carry  
#				low   = low>>1; 	  // Right shift lower bits
#				div   = div>>1;	  // Right shift divisor
#			}
#			System.out.println("Upper bits: %d", upper);
#			System.out.println("Lower bits: %d", low);
#}
################################################################
#
# Declare global so the programmer can see addresses
.globl welcome
.globl prompt1
.globl prompt2
.globl prompt3
.globl result1
.globl result2

# Data area with the stings that will be displayed
.data

welcome:
	.asciiz " This program divides two numbers \n\n"

prompt1: 
	.asciiz " Enter upper bits of a number: \n"

prompt2: 
	.asciiz " Enter lower bits of a number: \n"
	
prompt3: 
	.asciiz " Enter divisor: \n"

result1:
	.asciiz " Upper bits: \n"

result2:
	.asciiz " Lower bits: \n"

# Instructions
.text

main:
	# Display welcome message
	ori	$v0, $0, 4
	
	# Generate starting address for welcome message
	lui	$a0, 0x1001
	syscall
	
	# Display prompt1
	ori	$v0, $a0, 4

	# Starting address of prompt1
	lui   $a0, 0x1001
	ori	$a0, $a0, 0x25
	syscall

	# Read upper 32 bits of number from user
	ori 	$v0, $0, 5
	syscall

	# Save upper dividen in $s4
	ori	$s4, $0, 0
	addu	$s4, $v0, $0
 
	# Display prompt2
	ori	$v0, $0, 4

	# Starting address of prompt2
	lui $a0, 0x1001
	ori $a0, $a0, 0x6B
	syscall
	
	# Read lower 32 bits of number from user
	ori	$v0, $0, 5
	syscall
	
	# Save lower dividen in $s5
	ori	$s5, $0, 0
	addu	$s5, $v0, $0
	
	# Display prompt3
	ori	$v0, $0, 4
	
	# Starting address of prompt3
	lui 	$a0m 0x1001
	ori	$a0, $a0, 0x7D
	syscall

	# Read divisor from user
	ori	$v0, $0, 5
	syscall

	# Save divisor in $s6
	ori	$s6, $0m 0
	addu	$s6, $v0, $0
	
	# Load upper, lower, and div in temporary registers
	add 	$t4, $s4, $0
	add	$t5, $s5, $0
	add	$t6, $s6, $0
	
	# Load 1 into $t7 for AND purposes
	addi	$t7, $0, 1

	# Dividing loop, see Java code while loop for implementation
	division_loop:
		beq	$t6, $t7, output

	# $t3 is the "carry" variable
		add	$t3, $t4, $0
		andi	$t3, $t3, 1
		sll	$t3, $t3, 31
		srl	$t4, $t4, 1
		srl	$t5, $t5, 1
		or 	$t5, $t5, $t3 
		srl	$t6, $t6, 1
		j		division_loop

	output:
	
	# Display upper bits result (result1)
		ori	$v0, $0, 4
	
	# Starting address of upper bits results
		lui	$a0, 0x1001
		ori	$a0, $a0, 0x8C
		syscall

	# Display lower bits results (result2)
		ori	$v0, $0, 4
		
	# Starting address of lower bits results
		lui	$a0m 0x1001
		ori	$a0, $a0, 0x9B
		syscall
	
	# Exit
		ori	$v0, $0, 10
		syscall
