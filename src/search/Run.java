package search;
import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class Run {

	public static void main(String[] args) throws IOException {
		/*
		BinaryHeap queue = new BinaryHeap(100);
		Random rand = new Random();
		for(int i=0; i<7; i++) {
			queue.insert(rand.nextInt(5));
		}
		for(int i=0; i<queue.size; i++) {
			System.out.println(queue.heap[i]);
		}
		System.out.println("New");
		queue.deleteTop();
		for(int i=0; i<queue.size; i++) {
			System.out.println(queue.heap[i]);
		}
		*/
		/*
		OpenList queue = new OpenList();
		Random rand = new Random();
		for(int i=0; i<7; i++) {
			Cell cell = new Cell(0,0);
			cell.values(rand.nextInt(5), rand.nextInt(5));
			queue.push(cell);
		}
		for(int i=0; i<queue.size; i++) {
			System.out.println(queue.heap.get(i).fval);
		}
		System.out.println("New");
		queue.pop();
		for(int i=0; i<queue.size; i++) {
			System.out.println(queue.heap.get(i).fval);
		}
		*/
		
		//Grid grid = new Grid(101);
		Grid grid = new Grid();
		//grid.print();
		//grid.printAll();
		//grid.save("C:\\Users\\Sam\\Desktop\\AI\\maze.txt");
		//System.out.println();
		//grid.load("C:\\Users\\Sam\\Desktop\\AI\\maze.txt");
		grid.printAll();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter start x:");
		int startX = input.nextInt();
		System.out.println("Enter start y:");
		int startY = input.nextInt();
		System.out.println("Enter end x:");
		int endX = input.nextInt();
		System.out.println("Enter end y:");
		int endY = input.nextInt();
		
		System.out.println("Break ties with large g values or small g values?");
		System.out.println("1. Large");
		System.out.println("2. Small");
		int tieBreakerInput = input.nextInt();
		boolean tieBreaker=true;;
		if(tieBreakerInput==1) {
			tieBreaker=true;
		}
		else if(tieBreakerInput==2) {
			tieBreaker=false;
		}
		
		input.close();
		grid.reset();
		//grid.printSteps=false;
		//grid.repeatedForwardAStar(startX, startY, endX, endY, tieBreaker);
		//grid.repeatedBackwardsAStar(startX, startY, endX, endY, tieBreaker);
		//grid.smallLargeGValues(startX, startY, endX, endY);
		grid.forwardBackwardsAStar(startX, startY, endX, endY);
		//grid.print();
		grid.statistics();
		
	}
}