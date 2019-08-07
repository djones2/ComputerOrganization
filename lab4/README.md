A MIPS CPU simulator which will model the flow of instructions through a pipelined processor.  
The program will simulate a 5-stage pipeline.

The processor will accurately simulate the following pipeline delays:
 - 3 cycle delay for taken conditional branches
 - 1 cycle delay for a use-after-load condition
 - 1 cycle delay for any unconditional jump (j, jal, and jr)
