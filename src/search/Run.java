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
		
		//Grid grid = new Grid();
		//grid.print();
		//grid.printAll();
		Grid grid = new Grid(101);
		/*
		grid.load("C:\\Users\\Sam\\Desktop\\AI\\maze"+3+".txt");
		grid.reset();
		grid.printSteps=false;
		long startTime = System.currentTimeMillis();
		grid.repeatedForwardAStar(0,0,100,100,true);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		grid.print();
		startTime = System.currentTimeMillis();
		grid.repeatedBackwardsAStar(0,0,100,100,true);
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		grid.print();
		startTime = System.currentTimeMillis();
		grid.repeatedForwardAStar(0,0,100,100,false);
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		grid.print();
		*/
		//System.out.println(System.getProperty("user.dir"));
		grid.reset();
		grid.printSteps=false;
		grid.runAll();
	}
}