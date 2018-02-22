package search;
import java.util.ArrayList;

public class OpenList {
	int size;
	//Cell[] heap;
	ArrayList<Cell> heap;
	
	public OpenList() {
		this.size=0;
		//heap = new Cell[size];
		heap = new ArrayList<Cell>();
	}
	public void push(Cell cell) {
		//heap[size++]=cell;
		if(size==0 && heap.size()!=0) {
			heap.set(0, cell);
		}
		heap.add(cell);
		size++;
		heapifyUp(size-1);
	}
	public int size() {
		return size;
	}
	public void heapifyUp(int index) {
		/*
		Cell tmp = (Cell) heap[index];
		while(index>0 && tmp.fval < heap[(index-1)/2].fval) {
			heap[index] = heap[(index-1)/2];
			index = (index-1)/2;
		}
		heap[index]=tmp;
		*/
		Cell tmp = heap.get(index);
		while(index>0 && tmp.fval < heap.get((index-1)/2).fval) {
			heap.set(index, heap.get((index-1)/2));
			index = (index-1)/2;
		}
		heap.set(index, tmp);
	}
	public Cell pop() {
		//Cell top=heap[0];
		Cell top = heap.get(0);
		delete(0);
		return top;
	}
	public Cell delete(int index) {
		/*
		Cell cell = heap[index];
		heap[index] = heap[size-1];
		size--;
		heapifyDown(index);
		return cell;
		*/
		Cell cell = heap.get(index);
		heap.set(index, heap.get(size-1));
		size--;
		heapifyDown(index);
		return cell;
	}
	public void heapifyDown(int index) {
		/*
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
		*/
		int child;
		Cell tmp = heap.get(index);
		while(2*index+1<size) {
			child = minChild(index);
			if(heap.get(child).fval < tmp.fval) {
				heap.set(index,  heap.get(child));
			}
			else {
				break;
			}
			index = child;
		}
		heap.set(index, tmp);
	}
	public int minChild(int index) {
		int best = 2*index+1;
		int k=2;
		int pos = 2*index+2;
		while(k<=2 && pos<size) {
			//if(heap[pos].fval < heap[best].fval) {
			if(heap.get(pos).fval < heap.get(best).fval) {
				best = pos;
			}
			pos = (2*index+(k++));
		}
		return best;
	}
}