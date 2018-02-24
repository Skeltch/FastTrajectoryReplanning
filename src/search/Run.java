package search;
import java.util.Scanner;
import java.util.Random;

public class Run {

	public static void main(String[] args) {
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
		
		Grid grid = new Grid(5);
		//Grid grid = new Grid();
		//grid.print();
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
		input.close();
		grid.visitedAll();
		//grid.print();
		System.out.println("Path");
		//grid.revealAll();
		grid.repeatedForwardAStar(startX, startY, endX, endY);
		System.out.println("Finished");
		grid.print();
		grid.statistics();
		
	}
}