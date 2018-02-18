package search;

public class BinaryHeap {
	int size;
	int[] heap;
	
	public BinaryHeap(int size) {
		this.size=0;
		heap = new int[size];
	}
	public void insert(int val) {
		heap[size++]=val;
		heapifyUp(size-1);
	}
	public void heapifyUp(int index) {
		int tmp = heap[index];
		while(index>0 && tmp < heap[(index-1)/2]) {
			heap[index] = heap[(index-1)/2];
			index = (index-1)/2;
		}
		heap[index]=tmp;
	}
	public int deleteTop() {
		int top=heap[0];
		delete(0);
		return top;
	}
	public int delete(int index) {
		int val = heap[index];
		heap[index] = heap[size-1];
		size--;
		heapifyDown(index);
		return val;
	}
	public void heapifyDown(int index) {
		int child;
		int tmp = heap[index];
		while(2*index+1<size) {
			child = minChild(index);
			if(heap[child] < tmp) {
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
			if(heap[pos] < heap[best]) {
				best = pos;
			}
			pos = (2*index+(k++));
		}
		return best;
	}
}