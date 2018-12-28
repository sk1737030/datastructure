package ex_06sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * insert_sort for list
 * @author sk173
 * n(n-1) /2 n2이 된다.
 * 
 */

//인자값으로 2개 받는다 list 와 compartor받아서 비교

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms. mergesort는 혼자 못했음. 책보고 참고하였다.
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i = 1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j - 1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		int size = list.size();
		if (size <= 1) {
			return list;
		}

		List<T> first = mergeSort(new LinkedList<T>(list.subList(0, list.size() / 2)), comparator);
		List<T> second = mergeSort(new LinkedList<T>(list.subList(list.size() / 2, size)), comparator);

		//list.subList(int a,b)   list a, 순서 부터 b순서까지 새로운 arraylist에 값을 삽입하는 메소드.
		//sublist는 부모 reference를 참조한다. 그래서 새로운 값을 변경시키면 reference이기때문에
		//parent의 값들도 바뀐다. 203line~212line 보기

		return merge(first, second, comparator);
	}

	private List<T> merge(List<T> first, List<T> second, Comparator<T> comparator) {
		// TODO Auto-generated method stub
		List<T> result = new LinkedList<T>();
		int total = first.size() + second.size();

		for (int i = 0; i < total; i++) {
			List<T> winner = pickwinner(first, second, comparator);
			//winner remove를 함으로써 생성했었던 firts , second 를 삭제시킨다.
			result.add(winner.remove(0));
			//remove함수는 return 함과 동시에 인덱스 삭제한다.

		}

		return result;
	}

	private List<T> pickwinner(List<T> first, List<T> second, Comparator<T> comparator) {
		// TODO Auto-generated method stub
		//	System.out.println(first.size() + " : " + second.size());

		if (first.size() == 0)
			return second;
		if (second.size() == 0)
			return first;
		//remove함수를 사용했기때문에 size == 0 인경우에 다른 함수를 반환 한다.
		//size == 0 인경우는 다른 인자의 값이 더크다는말이다.
		int res = comparator.compare(first.get(0), second.get(0));
		//System.out.println(res + " : " + first.get(0) + " : "+second.get(0));
		//오른쪽 인자가 더크면 res 는 음수 , 왼쪽인자가 더크면 양수이다.
		if (res < 0)
			return first;
		if (res > 0)
			return second;
		return first;
	}

	/*
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * 
	 * @param comparator
	 * 
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		heap.addAll(list);
		list.clear();
		while (!heap.isEmpty()) {
			list.add(heap.poll());
		}
	}

	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		//힙으로 구성되어있다.
		
		
		
		for (T element : list) {
			if (heap.size() < k) {
				heap.offer(element);
				continue;
			}
			int cmp = comparator.compare(element, heap.peek());
			//heap.peak return smallest number in heap
			System.out.println(element + ":"  + heap.peek());
			if (cmp > 0) {
			
				heap.poll();
				//heap 에 가장작은요소를 반환한다.
				heap.offer(element);
				
			}
		}
		List<T> res = new ArrayList<T>();
		while (!heap.isEmpty()) {
			res.add(heap.poll());
		}
		return res;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer elt1, Integer elt2) {
				return elt1.compareTo(elt2);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		/*
		 * sorter.insertionSort(list, comparator); System.out.println(list);
		 */
		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		//	System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		//System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);

		/*
		 * List<Integer> lit = new ArrayList<Integer>(Arrays.asList(2,1));
		 * System.out.println(lit); List<Integer> lis = lit.subList(0, 2); //toindex는
		 * 포함하지않는다. System.out.println(lis); int i = lis.get(0) ; lis.set(0,
		 * lis.get(1)); lis.set(1, i);
		 * 
		 * System.out.println(lit);
		 */

	}
}