# Name: Daniel Jones & Anshula Singh
# Section: 01
# Description: This program operates the mod function
# Java code:
# 	 public int mod(int a, int b) { return a & (b - 1); }

# Global addresses
.globl welcome
.globl prompt
.globl sumText

# Data area (contains strings to be displayed during the program)
.data

welcome:
	.asciiz " This program mods two numbers \n\n"

prompt:
	.asciiz " Enter an integer: "
modText: 
	.asciiz " \n modulus = "

#Text Area (Instructions)

main:
	# Display welcome message (load 4 into $v0 to display)
	ori	$v0, $0, 4
	
	# Generates starting address for welcome message
	lui	$a0, 0x1001
	syscall

	# Display prompt
	ori	$v0, $0, 4

	# Starting address of prompt
	lui	$a0, 0x1001
	ori	$a0, $a0, 0x22
	syscall

	# Read first integer from user
	ori	$v0, $0, 5
	syscall

	# Clear $s0 for the mod
	# ori $s0, $0, 0 (not needed, can be over written
   # in next step)

	# Add 1st integer to register 
	addu	$s0, $v0, $s0

	# Display prompt
	ori	$v0, $0, 4
	lui	$a0, 0x1001
	ori	$a0, $a0, 0x22
	syscall

	# Read second integer into $v0
	ori	$v0, $0, 5
	syscall

	# Save the divisor in $s2
	addu	$s2, $v0, $0

	# Calculate modulus
	addi 	$s2, $s2, -1
	and	$s0, $s0, %s2
	
	# Display the mod text 
	ori	$v0, $0, 4
	lui	$a0, 0x1001
	ori	$a0, $a0, 0x36

	# Display the mod
	ori	$v0, $0, 1
	add	$a0, $s0, $0
	syscall

	# Exit
	ori	$v0, $0, 10
	syscall
