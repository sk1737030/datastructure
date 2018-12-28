package ex_06sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * insert_sort for list
 * @author sk173
 * n(n-1) /2 n2�� �ȴ�.
 * 
 */

//���ڰ����� 2�� �޴´� list �� compartor�޾Ƽ� ��

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms. mergesort�� ȥ�� ������. å���� �����Ͽ���.
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

		//list.subList(int a,b)   list a, ���� ���� b�������� ���ο� arraylist�� ���� �����ϴ� �޼ҵ�.
		//sublist�� �θ� reference�� �����Ѵ�. �׷��� ���ο� ���� �����Ű�� reference�̱⶧����
		//parent�� ���鵵 �ٲ��. 203line~212line ����

		return merge(first, second, comparator);
	}

	private List<T> merge(List<T> first, List<T> second, Comparator<T> comparator) {
		// TODO Auto-generated method stub
		List<T> result = new LinkedList<T>();
		int total = first.size() + second.size();

		for (int i = 0; i < total; i++) {
			List<T> winner = pickwinner(first, second, comparator);
			//winner remove�� �����ν� �����߾��� firts , second �� ������Ų��.
			result.add(winner.remove(0));
			//remove�Լ��� return �԰� ���ÿ� �ε��� �����Ѵ�.

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
		//remove�Լ��� ����߱⶧���� size == 0 �ΰ�쿡 �ٸ� �Լ��� ��ȯ �Ѵ�.
		//size == 0 �ΰ��� �ٸ� ������ ���� ��ũ�ٴ¸��̴�.
		int res = comparator.compare(first.get(0), second.get(0));
		//System.out.println(res + " : " + first.get(0) + " : "+second.get(0));
		//������ ���ڰ� ��ũ�� res �� ���� , �������ڰ� ��ũ�� ����̴�.
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
		//������ �����Ǿ��ִ�.
		
		
		
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
				//heap �� ����������Ҹ� ��ȯ�Ѵ�.
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
		 * System.out.println(lit); List<Integer> lis = lit.subList(0, 2); //toindex��
		 * ���������ʴ´�. System.out.println(lis); int i = lis.get(0) ; lis.set(0,
		 * lis.get(1)); lis.set(1, i);
		 * 
		 * System.out.println(lit);
		 */

	}
}