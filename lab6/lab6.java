/*

Daniel Jones

CPE 315 

Lab 6

*/
import java.util.*;
import java.io.*;

public class lab6{
	private static int[][] caches = new int[6][512];
	private static int[][] leastRecentlyUsed = new int[3][512];
	private static int[] cache7 = new int[1028];
	private static int[] hits = new int[7];
	private static int count = 0;

	public static void main(String[] args){
		Scanner inScan;
		int address;

		if(args.length < 1){
			System.out.println("No memory stream detected.");
			return;
		}

		try{
			inScan = new Scanner(new File(args[0]));

			while (inScan.hasNext()){
				inScan.next();
				address = Integer.parseInt(inScan.next(), 16);

				if (address < 0)
					throw new Exception("Invalid address: " + address);
				cache(address);

				// if (count % 10000 == 0)
				// 	System.out.printf("%.1f%%\n", (double)count * 100.0 / 5000000);

				count++;
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Could not find file: " + args[0]);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		System.out.println("Cache #1\nCache size: 2048B\tAssociativity: 1\tBlock size: 1");
		System.out.printf("Hits: %d\tHit rate: %.2f%%\n", hits[0], (100.0*(double)hits[0])/(double)count);
		System.out.println("---------------------------");

		System.out.println("Cache #2\nCache size: 2048B\tAssociativity: 1\tBlock size: 2");
		System.out.printf("Hits: %d\tHit rate: %.2f%%\n", hits[1], (100.0*(double)hits[1])/(double)count);
		System.out.println("---------------------------");

		System.out.println("Cache #3\nCache size: 2048B\tAssociativity: 1\tBlock size: 4");
		System.out.printf("Hits: %d\tHit rate: %.2f%%\n", hits[2], (100.0*(double)hits[2])/(double)count);
		System.out.println("---------------------------");

		System.out.println("Cache #4\nCache size: 2048B\tAssociativity: 2\tBlock size: 1");
		System.out.printf("Hits: %d\tHit rate: %.2f%%\n", hits[3], (100.0*(double)hits[3])/(double)count);
		System.out.println("---------------------------");

		System.out.println("Cache #5\nCache size: 2048B\tAssociativity: 4\tBlock size: 1");
		System.out.printf("Hits: %d\tHit rate: %.2f%%\n", hits[4], 100.0*(double)hits[4]/(double)count);
		System.out.println("---------------------------");

		System.out.println("Cache #6\nCache size: 2048B\tAssociativity: 4\tBlock size: 4");
		System.out.printf("Hits: %d\tHit rate: %.2f%%\n", hits[5], (100.0*(double)hits[5])/(double)count);
		System.out.println("---------------------------");

		System.out.println("Cache #7\nCache size: 4096B\tAssociativity: 1\tBlock size: 1");
		System.out.printf("Hits: %d\tHit rate: %.2f%%\n", hits[6], (100.0*(double)hits[6])/(double)count);
		System.out.println("---------------------------");
	}

	private static void cache(int inputAddress) throws Exception {
		int address;
		int index;

		address = inputAddress;
		index = (address>>2) % 512;

		if (address == 0)
			System.out.println("Address 0");

		if (index > caches[0].length)
			throw new Exception("Invalid index: " + index);

		/* Cache 1: 2KB, direct mapped, 1-word blocks */
		if (caches[0][index] == address)
			hits[0]++;
		else{
			caches[0][index] = address;
		}

		/* Cache 2: 2KB, direct mapped, 2-word blocks */
		if (index % 2 == 1){
			index--;
			address -= 4;
		}
		if (caches[1][index] == address)
			hits[1]++;
		else{
			caches[1][index] = address;
			caches[1][index+1] = address + 4;
		}

		/* Cache 3: 2KB, direct mapped, 4-word blocks */
		index = (inputAddress >> 2) % 512;
		address = inputAddress - (4*(index%4));
		index = index - (index%4);

		if (caches[2][index] == address)
			hits[2]++;
		else{
			caches[2][index] = address;
			caches[2][index+1] = address + 4;
			caches[2][index+2] = address + 8;
			caches[2][index+3] = address + 12;
		}

		/* Cache 4: 2KB, 2-way set associative, 1-word blocks */
		address = inputAddress;
		index = (address >> 2) % 256;

		if (caches[3][index] == address){
			hits[3]++;
			leastRecentlyUsed[0][index] = count;
		}
		else if (caches[3][index+256] == address){
			hits[3]++;
			leastRecentlyUsed[0][index+256] = count;
		}
		else{
			if (leastRecentlyUsed[0][index] < leastRecentlyUsed[0][index+256]){
				caches[3][index] = address;
				leastRecentlyUsed[0][index] = count;
			}
			else{
				caches[3][index+256] = address;
				leastRecentlyUsed[0][index+256] = count;
			}
		}

		/* Cache 5: 2KB, 4-way set associative, 1-word blocks */
		address = inputAddress;
		index = (address >> 2) % 128;

		if (caches[4][index] == address){
			hits[4]++;
			leastRecentlyUsed[1][index] = count;
		}
		else if (caches[4][index+128] == address){
			hits[4]++;
			leastRecentlyUsed[1][index+128] = count;
		}
		else if (caches[4][index+256] == address){
			hits[4]++;
			leastRecentlyUsed[1][index+256] = count;
		}
		else if (caches[4][index+384] == address){
			hits[4]++;
			leastRecentlyUsed[1][index+384] = count;
		}
		else{
			if (leastRecentlyUsed[1][index] == 0 
				|| (leastRecentlyUsed[1][index] < leastRecentlyUsed[1][index+128]
				&& leastRecentlyUsed[1][index] < leastRecentlyUsed[1][index+256]
				&& leastRecentlyUsed[1][index] < leastRecentlyUsed[1][index+384])){
					caches[4][index] = address;
					leastRecentlyUsed[1][index] = count;
			}
			else if (leastRecentlyUsed[1][index+128] == 0 
				|| (leastRecentlyUsed[1][index+128] < leastRecentlyUsed[1][index]
				&& leastRecentlyUsed[1][index+128] < leastRecentlyUsed[1][index+256]
				&& leastRecentlyUsed[1][index+128] < leastRecentlyUsed[1][index+384])){
					caches[4][index+128] = address;
					leastRecentlyUsed[1][index+128] = count;
			}
			else if (leastRecentlyUsed[1][index+256] == 0 
				|| (leastRecentlyUsed[1][index+256] < leastRecentlyUsed[1][index+128]
				&& leastRecentlyUsed[1][index+256] < leastRecentlyUsed[1][index]
				&& leastRecentlyUsed[1][index+256] < leastRecentlyUsed[1][index+384])){
					caches[4][index+256] = address;
					leastRecentlyUsed[1][index+256] = count;
			}
			else{
				caches[4][index+384] = address;
				leastRecentlyUsed[1][index+384] = count;
			}
		}

		/* Cache 6: 2KB, 4-way set associative, 4-word blocks */

		address = inputAddress;
		index = (address >> 2) % 128;
		address = address - (4*(index%4));
		index = index - (index%4);

		if (caches[5][index] == address){
			hits[5]++;
			leastRecentlyUsed[2][index] = count;
		}
		else if (caches[5][index+128] == address){
			hits[5]++;
			leastRecentlyUsed[2][index+128] = count;
		}
		else if (caches[5][index+256] == address){
			hits[5]++;
			leastRecentlyUsed[2][index+256] = count;
		}
		else if (caches[5][index+384] == address){
			hits[5]++;
			leastRecentlyUsed[2][index+384] = count;
		} 
		else{
			if (leastRecentlyUsed[2][index] == 0 
				|| (leastRecentlyUsed[2][index] < leastRecentlyUsed[2][index+128]
				&& leastRecentlyUsed[2][index] < leastRecentlyUsed[2][index+256]
				&& leastRecentlyUsed[2][index] < leastRecentlyUsed[2][index+384])){
					caches[5][index] = address;
					caches[5][index+1] = address + 4;
					caches[5][index+2] = address + 8;
					caches[5][index+3] = address + 12;
					leastRecentlyUsed[2][index] = count;
			}
			else if (leastRecentlyUsed[2][index+128] == 0 
				|| (leastRecentlyUsed[2][index+128] < leastRecentlyUsed[2][index]
				&& leastRecentlyUsed[2][index+128] < leastRecentlyUsed[2][index+256]
				&& leastRecentlyUsed[2][index+128] < leastRecentlyUsed[2][index+384])){
					caches[5][index+128] = address;
					caches[5][index+129] = address + 4;
					caches[5][index+130] = address + 8;
					caches[5][index+131] = address + 12;
					leastRecentlyUsed[2][index+128] = count;
			}
			else if (leastRecentlyUsed[2][index+256] == 0 
				|| (leastRecentlyUsed[2][index+256] < leastRecentlyUsed[2][index+128]
				&& leastRecentlyUsed[2][index+256] < leastRecentlyUsed[2][index]
				&& leastRecentlyUsed[2][index+256] < leastRecentlyUsed[2][index+384])){
					caches[5][index+256] = address;
					caches[5][index+257] = address + 4;
					caches[5][index+258] = address + 8;
					caches[5][index+259] = address + 12;
					leastRecentlyUsed[2][index+256] = count;
			}
			else {	caches[5][index+384] = address;
					caches[5][index+385] = address + 4;
					caches[5][index+386] = address + 8;
					caches[5][index+387] = address + 12;
					leastRecentlyUsed[2][index+384] = count;
			}
		}

		/* Cache 7: 4KB, direct mapped, 1-word blocks */

		address = inputAddress;
		index = (address>>2)%1024;

		if(cache7[index]==address)
			hits[6]++;
		else{
			cache7[index] = address;
		}
	}
}
