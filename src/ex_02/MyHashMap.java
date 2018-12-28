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
		//maps.size() 리스트의 방 개수를 말한다
		//size() 안에 entry 개수를 말한다.
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
		//이렇게 함으로써 옛날 주소를 oldMap이 가지고 있게된다.
		makeMaps(maps.size() * 2);
		//maps는 새로운 공간을 할당받고 전에 있는 주소는 잊어먹는다.

		for (MyLinearMap<K, V> map : oldMap)
			for (Entry<K, V> entry : map.getEntries())
				put(entry.getKey(), entry.getValue());
		//for 문을 돌면서 oldMap이 가지고있는 주소의 key,value를 새로운 hashmap.put에 넣는다 
		//hashmap.put은 상속을 받기때문에 super.put이된다,  즉 새롭게 할당 받은 newMaps에 for문이 돌면서 값을 새롭게 집어넣는다.

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