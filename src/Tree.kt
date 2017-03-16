/**
* Created by yaroslav on 27.02.17.
*/
interface Tree <K : Comparable<K>, V> {
	
	public fun addByKey(key: K, value: V)
	public fun searchByKey(key: K): Node<K, V>?
		
}