# Name: Daniel Jones & Anshula Singh
# Section: 01
# Description: This program reverses the binary representation of an input number
#
#  Note(as stated in syllabus to do):
#   I talked to about 3 other students in my attempt of this program
#  (Amanda and Evan are the only names I remember)
# 
# Java Code:
#################################
#	public int reverse(int a) {
#		int reverse = 0;
#		while (a > 0){
#			reverse << 1;
#			if (a & 1) == 1) {
#				reverse ^= 1;	
#		}
#     a>>1;			
#		}				
#     return reverse;
#  }
#################################

# Declare globals so programmer can see addresses
.globl welcome
.globl prompt
.globl reverse

# Data area
.data

welcome:
	.asciiz " This program reverses a input \n\n"

prompt:
	.asciiz " Enter an integer: "

reverse:
	.asciiz " \n Reverse = "

# Instruction
.text

main:

	# Display welcome message
	ori	$v0, $0, 4

	# Starting address for welcome message 
	lui 	$a0, 0x1001
	syscall

	# Display prompt
	ori	$v0, $0, 4
	
	# Starting address of prompt
	lui 	$a0, 0x1001
	ori	$a0, $a0, 0x22

	# Read integer from user
	ori	$v0, $0, 5
	syscall

	# Add integer to $t0
	addu	$t0, $v0, $s0

	# Store reverse variable in $t1
	addi	$t1, $0, 0

	# Store 1 in $t2 for operation in loop
	addi	$t2, $0, 1

reverse:
	# Logic for shifting bits, loop logic 
	beq	$t0, 0, end
	sll	$t1, $t1, 1
	and	$t7, $t0, $t2
	srl	$t0, $t0, 1
	beq	$t7, $0, reverse 
	xor	$t1, $t1, $t2
	b		reverse
	# Display the reverse text
end:
	# Put reversde $t1 into $s0
	move 	$t1, $s0		
	ori	$v0, $0, 4
	lui	$a0, 0x1001
	ori	$a0, $a0, 0x36
	syscall

	# Display to reverse binary number
	ori	$v0, $0, 1
	add	$a0, $s0, $0
	syscall

	# Exit
	ori	$v0, $0, 10
