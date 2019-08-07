	addi $sp, $0, 0
		
	addi $a0, $0, 30
	addi $a1, $0, 100
	addi $a2, $0, 20
	jal circle 						
		
	addi $a0, $0, 30
	addi $a1, $0, 80
	addi $a2, $0, 30
	addi $a3, $0, 30
	jal line						
		
	addi $a0, $0, 20
	addi $a1, $0, 1
	addi $a2, $0, 30
	addi $a3, $0, 30
	jal line 		
		
	addi $a0, $0, 40
	addi $a1, $0, 1
	addi $a2, $0, 30
	addi $a3, $0, 30
	jal line 			
		
	addi $a0, $0, 15
	addi $a1, $0, 60
	addi $a2, $0, 30
	addi $a3, $0, 50
	jal line 					
		
	addi $a0, $0, 30
	addi $a1, $0, 50
	addi $a2, $0, 45
	addi $a3, $0, 60
	jal line 					
		
	addi $a0, $0, 24
	addi $a1, $0, 105
	addi $a2, $0, 3
	jal circle 				
		
	addi $a0, $0, 36
	addi $a1, $0, 105
	addi $a2, $0, 3
	jal circle 						
		
	addi $a0, $0, 25
	addi $a1, $0, 90
	addi $a2, $0, 35
	addi $a3, $0, 90
	jal line 						
		
	addi $a0, $0, 25
	addi $a1, $0, 90
	addi $a2, $0, 20
	addi $a3, $0, 95
	jal line 						
		
	addi $a0, $0, 35
	addi $a1, $0, 90
	addi $a2, $0, 40
	addi $a3, $0, 95
	jal line 						

	j end


circle:
	add $s0, $0,  $0 			

	add $s1, $a2, $0 				

	add $t0, $a2, $a2
	addi $t1, $0, 3
	sub $s2, $t1, $t0 			

	add $t0, $t0, $t0
	addi $t1, $0, 10
	sub $s3, $t1, $t0				

	addi $s4, $0, 6				

circleLoop:
	addi $t0, $s0, -1
	slt $t0, $t0, $s1
	beq $t0, $0, circleLoopEnd	
	add $t0, $a0, $s0
	add $t1, $a1, $s1
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2				
					
	add $t0, $a0, $s0
	sub $t1, $a1, $s1
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2				
					
	sub $t0, $a0, $s0
	add $t1, $a1, $s1
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2				
					
	sub $t0, $a0, $s0
	sub $t1, $a1, $s1
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2				
					
	add $t0, $a0, $s1
	add $t1, $a1, $s0
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2			
					
	add $t0, $a0, $s1
	sub $t1, $a1, $s0
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2				
					
	sub $t0, $a0, $s1
	add $t1, $a1, $s0
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2				
					
	sub $t0, $a0, $s1
	sub $t1, $a1, $s0
	sw  $t0, 0($sp)
	sw  $t1, 1($sp)
	addi $sp, $sp, 2				 
					
	slt $t0, $s2, $0 
	bne $t0, $0, circleElse 	
	add $s2, $s2, $s3				
	addi $s3, $s3, 8				
	addi $s1, $s1, -1				
	j ifEnd

circleElse:							
	add $s2, $s2, $s4				
	addi $s3, $s3, 4				

ifEnd:
	addi $s4, $s4, 4				
	addi $s0, $s0, 1				

	j circleLoop
circleLoopEnd:
circleEnd:
	jr $ra

line: 

	addi $t0, $a0, 0 				
	addi $t1, $a1, 0 				
	addi $t2, $a2, 0				
	addi $t3, $a3, 0 				

									
	sub $t4, $t3, $t1
	slt $t5, $t4, $0
	beq $t5, $0, skip1
	sub $t4, $0, $t4 

skip1:

	sub $t5, $t2, $t0
	slt $t6, $t5, $0
	beq $t6, $0, skip2
	sub $t5, $0, $t5 
skip2: 								 
	slt $s0, $t5, $t4 			
	sw  $s0, 0($sp)			   

	beq $s0, $0, skip3
	add $t0, $t1, $0
	add $t1, $a0, $0
	add $t2, $t3, $0
	add $t3, $a2, $0

skip3:							 
	slt $t4, $t2, $t0
	beq $t4, $0, skip4
	add $t4, $t2, $0
	add $t5, $t3, $0
	add $t2, $t0, $0
	add $t3, $t1, $0
	add $t0, $t4, $0
	add $t1, $t5, $0
skip4:
	addi $a0, $t0, 0 #x0
	addi $a1, $t1, 0 #y0
	addi $a2, $t2, 0 #x1
	addi $a3, $t3, 0 #y1

	sub $t0, $a2, $a0 			
	sub $t1, $a3, $a1 
	slt $t2, $t1, $0
	beq $t2, $0, skip5
	sub $t1, $0, $t1  			
	skip5:
	add $t2, $0, $0   			
	add $t3, $a1, $0  			

									
	slt $t4, $a1, $a3
	bne $t4, $0, skip6
	addi $t4, $0, -1

skip6:
	lw $t5, 0($sp)             
	addi $a2, $a2, 1

lineLoop:
	beq $a0, $a2, lineEnd
										
	beq $t5, $0, plotXY
	sw  $t3, 0($sp)
	sw  $a0, 1($sp)
	j skip7

	plotXY:
	sw  $a0, 0($sp)
	sw  $t3, 1($sp)
	skip7:
	add  $t2, $t2, $t1		


	add $t6, $t2, $t2
	slt $t7, $t6, $t0
	bne $t7, $0, skip8
	add $t3, $t3, $t4
	sub $t2, $t2, $t0

skip8:
	addi $sp, $sp, 2
	addi $a0, $a0, 1
	j lineLoop

lineEnd:
	jr $ra

end: