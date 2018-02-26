package search;
import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class Run {

	public static void main(String[] args) throws IOException {
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