package search;
import java.util.ArrayList;

public class OpenList {
	int size;
	Cell[] heap;
	
	public OpenList(int size) {
		this.size=0;
		heap = new Cell[size];
	}
	public void push(Cell cell) {
		heap[size++]=cell;
		heapifyUp(size-1);
	}
	public int size() {
		return size;
	}
	public void heapifyUp(int index) {
		
		Cell tmp = (Cell) heap[index];
		while(index>0 && tmp.priority < heap[(index-1)/2].priority) {
			heap[index] = heap[(index-1)/2];
			index = (index-1)/2;
		}
		heap[index]=tmp;
	}
	public Cell pop() {
		Cell top=heap[0];
		delete(0);
		return top;
	}
	public Cell top() {
		return heap[0];
	}
	public Cell delete(int index) {
		
		Cell cell = heap[index];
		heap[index] = heap[size-1];
		size--;
		heapifyDown(index);
		return cell;
	}
	public void heapifyDown(int index) {
		
		int child;
		Cell tmp = heap[index];
		while(2*index+1<size) {
			child = minChild(index);
			if(heap[child].priority < tmp.priority) {
				heap[index] = heap[child];
			}
			else {
				break;
			}
			index = child;
		}
		heap[index] = tmp;
	}
	public int minChild(int index) {
		int best = 2*index+1;
		int k=2;
		int pos = 2*index+2;
		while(k<=2 && pos<size) {
			if(heap[pos].priority < heap[best].priority) {
				best = pos;
			}
			pos = (2*index+(k++));
		}
		return best;
	}
	public boolean contains(Cell cell) {
		for(Cell curCell : heap) {
			if(curCell!=null) {
				if(cell.x==curCell.x && cell.y==curCell.y) {
					return true;
				}
			}
		}
		return false;
	}
}