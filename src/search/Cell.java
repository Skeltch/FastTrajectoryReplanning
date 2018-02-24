package search;

public class Cell {
	public int x,y;
	public int fval,gval,hval, priority;
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
	public void values(int gval, int hval, int size, boolean tieBreaker) {
		fval = hval+gval;
		this.hval=hval;
		this.gval=gval;
		//Priority tie breaker
		if(tieBreaker) {
			priority=size*2*fval-gval;
		}
		else{
			priority=size*2*fval+gval;
		}
	}
}
