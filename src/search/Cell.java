package search;

public class Cell {
	public int x,y;
	public int fval,gval,hval;
	public boolean visited,blocked,visible,agent,target;
	public Cell(int x, int y) {
		visited=false;
		blocked=false;
		this.x=x;
		this.y=y;
	}
	public int hval(int endX, int endY) {
		return Math.abs(endX-x)+Math.abs(endY-y);
	}
	public void values(int gval, int hval) {
		fval = hval+gval;
		this.hval=hval;
		this.gval=gval;
	}
}
