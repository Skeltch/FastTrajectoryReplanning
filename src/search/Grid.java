package search;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.HashMap;
import java.util.Collections;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Grid {
	Random rand = new Random();
	public ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();
	int size;
	boolean printSteps=true;
	//Example grids for easy testing and debugging
	public Grid() {
		size=5;
		for(int i=0; i<5; i++) {
			grid.add(new ArrayList<Cell>());
			for(int j=0; j<5; j++) {
				grid.get(i).add(new Cell(j,i));
			}
		}
		
		grid.get(4).get(3).blocked=true;
		grid.get(3).get(2).blocked=true;
		grid.get(2).get(2).blocked=true;
		grid.get(1).get(2).blocked=true;
		grid.get(3).get(3).blocked=true;
		grid.get(2).get(3).blocked=true;
		
		//grid.get(4).get(3).blocked=true;
		//grid.get(3).get(4).blocked=true;
	}
	//Initialize the grid with the described method and input size
	public Grid(int size) {
		this.size=size;
		for(int i=0; i<size; i++) {
			grid.add(new ArrayList<Cell>());
			for(int j=0; j<size; j++) {
				grid.get(i).add(new Cell(j, i));
			}
		}
		//Store parent nodes
		Stack<Cell> buildStack = new Stack<Cell>();
		//Pick random starting point
		int startY=rand.nextInt(size);
		int startX=rand.nextInt(size);
		Cell startCell = grid.get(startY).get(startX);
		buildStack.push(startCell);
		
		//Starting node is visited and unblocked (default value)
		startCell.visited=true;
		//Count number of visited Nodes to ensure we reach every single one
		int visitedNodes=1;
		Cell curCell=startCell;
		while(visitedNodes<size*size) {
			Cell nextCell=nextCell(curCell);
			if(nextCell==null) {
				if(buildStack.size()!=0) {
					curCell=buildStack.pop();
				}
				else {
					for(int i=0; i<size; i++) {
						for(int j=0; j<size; j++) {
							if(grid.get(i).get(j).visited==false) {
								curCell=grid.get(i).get(j);
								visitedNodes++;
							}
						}
					}
				}
			}
			else {
				nextCell.visited=true;
				buildStack.push(nextCell);
				curCell=nextCell;
				//From 0 to 2, giving 30 percent to be blocked
				if(rand.nextInt(10)<3) {
					curCell.blocked=true;
				}
				visitedNodes++;
			}
		}		
	}
	//Pick next Cell, if all are visited return null
	public Cell nextCell(Cell curCell) {
		//We add a list of potential cells to traverse
		ArrayList<Cell> potential = new ArrayList<Cell>();
		boolean up=false;
		boolean right=false;
		boolean down=false;
		boolean left=false;
		//Check we're not at any limits
		Cell testCell = up(curCell);
		if(testCell==null) {
			up=true;
		}
		//Check if it's visited or not
		else if(testCell.visited==false) {
			potential.add(up(curCell));
		}
		//If it is visited then we check the next ones
		else {
			up=true;
		}
		testCell = right(curCell);
		if(testCell==null) {
			right=true;
		}
		else if(testCell.visited==false) {
			potential.add(right(curCell));
		}
		else {
			right=true;
		}
		testCell = down(curCell);
		if(testCell==null) {
			down=true;
		}
		else if(testCell.visited==false) {
			potential.add(down(curCell));
		}
		else {
			down=true;
		}
		testCell = left(curCell);
		if(testCell==null) {
			left=true;
		}
		else if(testCell.visited==false) {
			potential.add(left(curCell));
		}
		else {
			left=true;
		}
		if(up==true && right==true && down==true && left==true) {
			return null;
		}
		//Randomly pick a cell
		return potential.get(rand.nextInt(potential.size()));
	}
	//Easy way to move 
	public Cell up(Cell curCell) {
		if(curCell.y==size-1) {
			return null;
		}
		return grid.get(curCell.y+1).get(curCell.x);
	}
	public Cell right(Cell curCell) {
		if(curCell.x==size-1) {
			return null;
		}
		return grid.get(curCell.y).get(curCell.x+1);
	}
	public Cell down(Cell curCell) {
		if(curCell.y==0) {
			return null;
		}
		return grid.get(curCell.y-1).get(curCell.x);
	}
	public Cell left(Cell curCell) {
		if(curCell.x==0) {
			return null;
		}
		return grid.get(curCell.y).get(curCell.x-1);
	}
	
	//Method to create a list of neighbors to traverse
	public ArrayList<Cell> neighbors(Cell curCell){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		Cell testCell = up(curCell);
		if(testCell!=null) {
			neighbors.add(testCell);
		}
		testCell = right(curCell);
		if(testCell!=null) {
			neighbors.add(testCell);
		}
		testCell = down(curCell);
		if(testCell!=null) {
			neighbors.add(testCell);
		}
		testCell = left(curCell);
		if(testCell!=null) {
			neighbors.add(testCell);
		}
		return neighbors;
	}
	//f(s)=g(s)+h(s)
	//where g(s) is absolute steps from s to target (including blocks)
	//h(s) is heuristic steps (stays constant regardless of blocks)
	//insert all adjacent cells (not in heap already)
	//with f g and h values to priority queue
	//g value just increases by 1 while searching, h values are calculated
	//take smallest f values, with higher g values breaking ties until target reached
	//when we come upon new data (blocked cell) repeat A*?
	//**************Instead of fval we need to break ties with another value******
	public void repeatedForwardAStar(int startX, int startY, int endX, int endY, boolean tieBreaker) {
		//Initiate the starting cell as the agent cell, setting it visible and visited
		Cell agentCell = grid.get(startY).get(startX);
		agentCell.visible=true;
		agentCell.values(0, agentCell.hval(endX, endY), size, tieBreaker);
		grid.get(endY).get(endX).target=true;
		Cell curCell;
		agentCell.agent=true;
		int moveCounter=0;
		for(Cell cell : neighbors(agentCell)){
			cell.visible=true;
		}
		if(printSteps) {
			System.out.println("Step 0");
			print();
		}
		
		//While the agent cell isn't at the end we are not finished
		while(agentCell.x!=endX || agentCell.y!=endY) {
			//This is the tree pointer for calculating path
			HashMap<Cell, Cell> path = new HashMap<Cell, Cell>();
			//This array will have the shortest path but backwards
			ArrayList<Cell> shortestPath = new ArrayList<Cell>();
			//Open list heap
			OpenList openlist = new OpenList(size*size);
			//Closed list of cells
			ArrayList<Cell> closedlist = new ArrayList<Cell>();
			openlist.push(agentCell);
			//This is A* if openlist is empty there is no possible path
			while(openlist.size()>0) {
				//Take lowest f value cell off heap
				curCell = openlist.pop();
				//We have found the shortest path if A* has reached the end and is the smallest value in the heap
				if(curCell.x==endX && curCell.y==endY && curCell.priority<=openlist.top().priority) {
					while(path.containsKey(curCell)) {
						shortestPath.add(curCell);
						curCell=path.get(curCell);
					}
					//System.out.println("Running A*");
					/*
					System.out.println("Shortest path");
					for(Cell cell : shortestPath) {
						System.out.println(cell.x+","+cell.y);
					}
					*/
					break;
				}
				closedlist.add(curCell);
				ArrayList<Cell> neighbors = neighbors(curCell);
				for(Cell nextCell : neighbors) {
					//If the cell hasn't been added previously add it to the openlist if it's not blocked
					if(!closedlist.contains(nextCell) && !openlist.contains(nextCell)) {
						//if it's not visible we assume it's unblocked and try to traverse it anyways
						if(!nextCell.blocked || !nextCell.visible) {
							nextCell.values(curCell.gval+1, nextCell.hval(endX,endY), size, tieBreaker);
							openlist.push(nextCell);
							//Path uses parents as values and children as keys so we can find shortest path lather
							path.put(nextCell, curCell);
						}
					}
				}
			}
			//We have exhausted all options and cannot find a path
			if(shortestPath.isEmpty()) {
				System.out.println("No possible path");
				break;
			}
			//Move
			Collections.reverse(shortestPath);
			for(Cell cellPath : shortestPath) {
				//Whenever the path is blocked we need to redo A*
				if(cellPath.blocked==true) {
					break;
				}
				else{
					//Move agent
					agentCell.agent=false;
					agentCell=cellPath;
					agentCell.agent=true;
					for(Cell neighbor : neighbors(agentCell)) {
						neighbor.visible=true;
					}
					moveCounter++;
					if(printSteps) {
						System.out.println("Step "+moveCounter);
						print();
						System.out.println();
					}
				}
			}
		}
	}
	
	//Very similar to repeated Forwards A Star
	public void repeatedBackwardsAStar(int startX, int startY, int endX, int endY, boolean tieBreaker) {
		//Initiate the starting cell as the agent cell, setting it visible and visited
		Cell agentCell = grid.get(startY).get(startX);
		agentCell.visible=true;
		Cell targetCell = grid.get(endY).get(endX);
		targetCell.target=true;
		Cell curCell;
		agentCell.agent=true;
		int moveCounter=0;
		for(Cell cell : neighbors(agentCell)){
			cell.visible=true;
		}
		if(printSteps) {
			System.out.println("Step 0");
			print();
		}
		
		//While the agent cell isn't at the end we are not finished
		while(agentCell.x!=endX || agentCell.y!=endY) {
			//This is the tree pointer for calculating path
			HashMap<Cell, Cell> path = new HashMap<Cell, Cell>();
			int aSteps=0;
			//This array will have the shortest path but backwards
			ArrayList<Cell> shortestPath = new ArrayList<Cell>();
			//Open list heap
			OpenList openlist = new OpenList(size*size);
			//Closed list of cells
			ArrayList<Cell> closedlist = new ArrayList<Cell>();
			openlist.push(targetCell);
			//This is A* if openlist is empty there is no possible path
			while(openlist.size()>0) {
				//If it's the first step we need to include the neighbors of the targetCell, and recalculate targetCell's values
				if(aSteps==0) {
					//Comparing from targetCell to agentCell now
					targetCell.values(0, targetCell.hval(agentCell.x,agentCell.y), size, tieBreaker);
					for(Cell cell : neighbors(targetCell)) {
						path.put(cell, targetCell);
					}
				}
				aSteps++;
				//Take lowest f value cell off heap
				curCell = openlist.pop();
				//We have found the shortest path if A* has reached the end and is the smallest value in the heap
				if(curCell.x==agentCell.x && curCell.y==agentCell.y && curCell.priority<=openlist.top().priority) {
					while(path.containsKey(curCell)) {
						shortestPath.add(curCell);
						curCell=path.get(curCell);
					}
					shortestPath.add(targetCell);
					/*
					System.out.println("Running A*");
					
					System.out.println("Shortest path");
					for(Cell cell : shortestPath) {
						System.out.println(cell.x+","+cell.y);
					}
					*/
					break;
				}
				closedlist.add(curCell);
				ArrayList<Cell> neighbors = neighbors(curCell);
				for(Cell nextCell : neighbors) {
					//If the cell hasn't been added previously add it to the openlist if it's not blocked
					if(!closedlist.contains(nextCell) && !openlist.contains(nextCell)) {
						//if it's not visible we assume it's unblocked and try to traverse it anyways
						if(!nextCell.blocked || !nextCell.visible) {
							//Compare current cell to agent cell
							nextCell.values(curCell.gval+1, nextCell.hval(agentCell.x,agentCell.y), size, tieBreaker);
							openlist.push(nextCell);
							//Path uses parents as values and children as keys so we can find shortest path lather
							path.put(nextCell, curCell);
						}
					}
				}
			}
			//We have exhausted all options and cannot find a path
			if(shortestPath.isEmpty()) {
				System.out.println("No possible path");
				break;
			}
			//Move
			//Collections.reverse(shortestPath);
			for(Cell cellPath : shortestPath.subList(1, shortestPath.size())) {
				//Whenever the path is blocked we need to redo A*
				if(cellPath.blocked==true) {
					break;
				}
				else{
					//Move agent
					agentCell.agent=false;
					agentCell=cellPath;
					agentCell.agent=true;
					for(Cell neighbor : neighbors(agentCell)) {
						neighbor.visible=true;
					}
					moveCounter++;
					if(printSteps) {
						System.out.println("Step "+moveCounter);
						print();
						System.out.println();
					}
				}
			}
		}
	}
	//Compare small vs large g values for tie breaking
	public void smallLargeGValues(int startX, int startY, int endX, int endY) {
		long startTime = System.currentTimeMillis();
		repeatedForwardAStar(startX,startY,endX,endY, true);
		long endTime = System.currentTimeMillis();
		long totalTimeLarge = endTime - startTime;
		print();
		reset();
		startTime = System.currentTimeMillis();
		repeatedForwardAStar(startX,startY,endX,endY, false);
		endTime = System.currentTimeMillis();
		long totalTimeSmall = endTime - startTime;
		print();
		System.out.println("Large G Value run time:"+totalTimeLarge);
		System.out.println("Small G Value run time:"+totalTimeSmall);
	}
	//Compare forwards vs backwards repeated a star breaking ties with large g values
	public void forwardBackwardsAStar(int startX, int startY, int endX, int endY) {
		long startTime = System.currentTimeMillis();
		repeatedForwardAStar(startX,startY,endX,endY,true);
		long endTime = System.currentTimeMillis();
		long totalTimeForwards = endTime - startTime;
		print();
		reset();
		startTime = System.currentTimeMillis();
		repeatedBackwardsAStar(startX, startY, endX, endY, true);
		endTime = System.currentTimeMillis();
		long totalTimeBackwards = endTime - startTime;
		print();
		System.out.println("Large Forwards run time:"+totalTimeForwards);
		System.out.println("Small Backwards run time:"+totalTimeBackwards);
	}
	
	//Run on all 50 mazes and report run time
	public void runAll() throws IOException {
		long totalForwards=0;
		long totalBackwards=0;
		long totalSmall=0;
		long startTime=0;
		long endTime=0;
		long totalTime=0;
		for(int i=1; i<=50; i++) {
			load("mazes\\maze"+i+".txt");
			//Run repeated forwards a star with large G tie break
			startTime = System.currentTimeMillis();
			repeatedForwardAStar(0,0,100,100,true);
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			totalForwards+=totalTime;
			reset();
			//Run repeated backwards a star with large G tie break
			startTime = System.currentTimeMillis();
			repeatedBackwardsAStar(0,0,100,100,true);
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			totalBackwards+=totalTime;
			reset();
			//Run repeated forwards a star with small G tie break
			startTime = System.currentTimeMillis();
			repeatedForwardAStar(0,0,100,100,false);
			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			totalSmall+=totalTime;
			//Run repeated adaptive a* with large G tie break
			System.out.println("Running Maze "+i+":"+totalForwards+","+totalBackwards+","+totalSmall);
		}
		System.out.println("Average Time for Repeated Forwards A*: "+totalForwards/50);
		System.out.println("Average Time for Repeated Backwards A*: "+totalBackwards/50);
		System.out.println("Average Time for Small vs Large G Value tie breaking A*: "+totalSmall/50);
	}
	
	//Debugging
	public void revealAll() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				grid.get(i).get(j).visible=true;
			}
		}
	}
	
	//Reset all blocks back to not visited, also set blocks back to not visible
	public void reset() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(grid.get(i).get(j).visited=false) {
					System.out.println("Cell not visited!");
				}
				grid.get(i).get(j).visited=false;
				grid.get(i).get(j).visible=false;
				grid.get(i).get(j).agent=false;
				grid.get(i).get(j).target=false;
			}
		}
	}
	//Number of blocked and unblocked cells
	public void statistics() {
		int numBlocked=0;
		int numUnblocked=0;
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(grid.get(i).get(j).blocked==true) {
					numBlocked++;
				}
				else {
					numUnblocked++;
				}
			}
		}
		System.out.println("Number of blocked cells: "+Integer.toString(numBlocked));
		System.out.println("Number of unblocked cells: "+Integer.toString(numUnblocked));
	}
	//print maze taking into account visibility
	public void print() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				Cell curCell = grid.get(i).get(j);
				if(curCell.agent==true) {
					System.out.print("A");
				}
				else if(curCell.target==true) {
					System.out.print("T");
				}
				else if(curCell.blocked==true && curCell.visible==true) {
					System.out.print("x");
				}
				else if(curCell.blocked==false && curCell.visible==true) {
					System.out.print("o");
				}
				else {
					System.out.print("?");
				}
			}
			System.out.println("");
		}
	}
	//print maze ignoring visibility
	public void printAll() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				Cell curCell = grid.get(i).get(j);
				if(curCell.blocked==true) {
					System.out.print("x");
				}
				else{
					System.out.print("o");
				}
			}
			System.out.println("");
		}
	}
	
	public void save(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				Cell curCell = grid.get(i).get(j);
				if(curCell.blocked) {
					writer.write("1");
				}
				else {
					writer.write("0");
				}
			}
			writer.newLine();
		}
		writer.close();
		/*
		OutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			for(int i=0; i<size; i++) {
				for(int j=0; j<size; j++) {
					Cell curCell = grid.get(j).get(i);
					if(curCell.blocked) {
						out.write(0);
					}
					else {
						out.write(1);
					}
				}
			}
		} finally {
			if(out !=null) {
				out.close();
			}
		}
		*/
	}
	public void load(String filename) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String currentLine;
		int i=0;
		int j=0;
		while((currentLine = in.readLine()) != null) {
			j=0;
			for(char c : currentLine.toCharArray()) {
				//System.out.println(i+","+j+","+c+","+currentLine.length());
				//System.out.print(c);
				if(c=='0') {
					grid.get(i).get(j).blocked=false;
				}
				else if(c=='1') {
					grid.get(i).get(j).blocked=true;
				}
				j++;
			}
			i++;
		}
	}
}
/*
We may need to backtrack so we can't ignore that path
oooooo
ooxooo
oxoxoo
oxoxoo
ooAoxT
So what will happen is A* will observe the path backwards
but realize it's a dead end and only take it if necessary

*/