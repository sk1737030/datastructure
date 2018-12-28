package ex_02;

import java.util.ArrayList; 
import java.util.List;
import java.util.Map;

public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

	// average number of entries per map before we rehash
	protected static final double FACTOR = 1.0;

	@Override
	public V put(K key, V value) {
		V oldValue = super.put(key, value);
		//System.out.println("Put " + key + " in " + size() + " size now " + maps.size());
		//maps.size() ����Ʈ�� �� ������ ���Ѵ�
		//size() �ȿ� entry ������ ���Ѵ�.
		// check if the number of elements per map exceeds the threshold
		if (size() > maps.size() * FACTOR) {
			rehash();
		}
		return oldValue;
	}

	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	protected void rehash() {
		// TODO: FILL THIS IN!
		List<MyLinearMap<K, V>> oldMap = maps;
		//�̷��� �����ν� ���� �ּҸ� oldMap�� ������ �ְԵȴ�.
		makeMaps(maps.size() * 2);
		//maps�� ���ο� ������ �Ҵ�ް� ���� �ִ� �ּҴ� �ؾ�Դ´�.

		for (MyLinearMap<K, V> map : oldMap)
			for (Entry<K, V> entry : map.getEntries())
				put(entry.getKey(), entry.getValue());
		//for ���� ���鼭 oldMap�� �������ִ� �ּ��� key,value�� ���ο� hashmap.put�� �ִ´� 
		//hashmap.put�� ����� �ޱ⶧���� super.put�̵ȴ�,  �� ���Ӱ� �Ҵ� ���� newMaps�� for���� ���鼭 ���� ���Ӱ� ����ִ´�.

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i = 0; i < 10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}