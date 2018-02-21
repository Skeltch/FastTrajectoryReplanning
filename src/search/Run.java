package search;
import java.util.Scanner;
import java.util.Random;

public class Run {

	public static void main(String[] args) {
		
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
		/*
		Grid grid = new Grid(5);
		//Grid grid = new Grid();
		grid.print();
		//grid.printAll();
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
		grid.print();
		//grid.repeatedForwardAStar(startX, startY, endX, endY);
		grid.statistics();
		*/
	}
}
/*
		1
	2		1
3		2 4		1

*/