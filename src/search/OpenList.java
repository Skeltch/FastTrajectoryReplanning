package search;

public class OpenList {
	int size;
	Cell[] heap;
	
	public OpenList(int size) {
		this.size=0;
		heap = new Cell[size];
	}
	public void insert(Cell cell) {
		heap[size++]=cell;
		heapifyUp(size-1);
	}
	public void heapifyUp(int index) {
		Cell tmp = (Cell) heap[index];
		while(index>0 && tmp.fval < heap[(index-1)/2].fval) {
			heap[index] = heap[(index-1)/2];
			index = (index-1)/2;
		}
		heap[index]=tmp;
	}
	public Cell deleteTop() {
		Cell top=heap[0];
		delete(0);
		return top;
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
			if(heap[child].fval < tmp.fval) {
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
			if(heap[pos].fval < heap[best].fval) {
				best = pos;
			}
			pos = (2*index+(k++));
		}
		return best;
	}
}