package search;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.HashMap;

public class Grid {
	Random rand = new Random();
	public ArrayList<ArrayList<Cell>> grid = new ArrayList<ArrayList<Cell>>();
	int size;
	//Example 5x5 grid
	public Grid() {
		size=5;
		for(int i=0; i<5; i++) {
			grid.add(new ArrayList<Cell>());
			for(int j=0; j<5; j++) {
				grid.get(i).add(new Cell(j,i));
				//grid.get(i).get(j).visited=true;
			}
		}
		grid.get(4).get(3).blocked=true;
		grid.get(3).get(2).blocked=true;
		grid.get(2).get(2).blocked=true;
		grid.get(1).get(2).blocked=true;
		grid.get(3).get(3).blocked=true;
		grid.get(2).get(3).blocked=true;
	}
	//Initialize the grid with the described method
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
		System.out.println("Starting cell :"+Integer.toString(startX)+","+Integer.toString(startY));
		Cell startCell = grid.get(startY).get(startX);
		buildStack.push(startCell);
		//Starting node is visited and unblocked
		startCell.visited=true;
		//Count number of visited Nodes to ensure we reach every single one
		int visitedNodes=1;
		Cell curCell=startCell;
		while(visitedNodes<size*size) {
			//Debugging in x,y
			//print();
			//System.out.print("Moving from: "+Integer.toString(curCell.x)+","+Integer.toString(curCell.y));
			Cell nextCell=nextCell(curCell);
			if(nextCell==null) {
				//System.out.println("Dead end at: "+Integer.toString(curCell.x)+","+Integer.toString(curCell.y));
				if(buildStack.size()!=0) {
					curCell=buildStack.pop();
				}
				else {
					for(int i=0; i<size; i++) {
						for(int j=0; j<size; j++) {
							if(grid.get(i).get(j).visited==false) {
								curCell=grid.get(i).get(j);
								//curCell.visited=true;
								visitedNodes++;
								//System.out.println("Finding new starting point at: "+Integer.toString(curCell.x)+","+Integer.toString(curCell.y));
							}
						}
					}
				}
			}
			else {
				nextCell.visited=true;
				//System.out.println(" To: "+Integer.toString(nextCell.x)+","+Integer.toString(nextCell.y));
				buildStack.push(nextCell);
				curCell=nextCell;
				//curCell.visited=true;
				//From 0 to 2, giving 30 percent to be blocked
				if(rand.nextInt(10)<3) {
					//System.out.println("Blocked cell at :"+Integer.toString(curCell.x)+","+Integer.toString(curCell.y));
					curCell.blocked=true;
				}
				visitedNodes++;
			}
			//System.out.println(visitedNodes);
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
		//And we pick a random one
		/*
		if(potential.size()==1) {
			return potential.get(0);
		}
		*/
		return potential.get(rand.nextInt(potential.size()));
	}
	//Move
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
	//maybe don't check if cell is blocked. we need to update a* on new information
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
	public void repeatedForwardAStar(int startX, int startY, int endX, int endY) {
		Cell start = grid.get(startY).get(startX);
		HashMap<Cell, Cell> path = new HashMap<Cell, Cell>();
		start.visited=true;
		start.visible=true;
		start.values(0, start.hval(endX, endY));
		ArrayList<Cell> gscore = new ArrayList<Cell>();
		ArrayList<Cell> fscore = new ArrayList<Cell>();
		OpenList openlist = new OpenList(size*size);
		openlist.push(start);
		Cell curCell;
		ArrayList<Cell> closedlist = new ArrayList<Cell>();
		int counter=0;
		for(Cell cell : neighbors(start)){
			cell.visible=true;
		}
		while(openlist.size()>0) {
			
			System.out.println("Prepop");
			for(int i=0; i<openlist.size; i++) {
				System.out.println(openlist.heap[i].x+","+openlist.heap[i].y+","+openlist.heap[i].fval);
			}
			
			curCell = openlist.pop();
			print();
			if(curCell.x==endX && curCell.y==endY && curCell.fval<=openlist.top().fval) {
				ArrayList<Cell> shortestPath = new ArrayList<Cell>();
				System.out.println("Shortest path");
				while(path.containsKey(curCell)) {
					shortestPath.add(curCell);
					curCell=path.get(curCell);
				}
				for(Cell cell : shortestPath) {
					System.out.println(cell.x+","+cell.y);
				}
				break;
				/*
				System.out.println("Closed list");
				for(Cell cell : closedlist) {
					System.out.println(Integer.toString(cell.x)+","+Integer.toString(cell.y));
				}
				*/
			}
			System.out.print("Current Location: ");
			System.out.println(Integer.toString(curCell.x)+","+Integer.toString(curCell.y));
			//curCell.visited=true;
			System.out.println("Step "+counter);
			counter++;
			closedlist.add(curCell);
			ArrayList<Cell> neighbors = neighbors(curCell);
			//check neighbors not in list already, and not blocked
			for(Cell nextCell : neighbors) {
				//nextCell.visible=true;
				//use cell.visited?
				//Maybe bugged here
				//if(!openlist.heap.contains(nextCell)) {
				if(!closedlist.contains(nextCell) && !openlist.contains(nextCell)) {
					if(!nextCell.blocked || !nextCell.visible) {
						nextCell.values(curCell.gval+1, nextCell.hval(endX,endY));
						//System.out.println("Pushing cells: "+nextCell.x+","+nextCell.y+","+nextCell.fval);
						openlist.push(nextCell);
						path.put(nextCell, curCell);
					}
				}
			}
		}
	}
	
	//Reset all blocks back to not visited
	public void visitedAll() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if(grid.get(i).get(j).visited=false) {
					System.out.println("Cell not visited!");
				}
				grid.get(i).get(j).visited=false;
			}
		}
	}
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
	//i is y j is x
	public void print() {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				Cell curCell = grid.get(i).get(j);
				if(curCell.blocked==true && curCell.visible==true) {
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
}
