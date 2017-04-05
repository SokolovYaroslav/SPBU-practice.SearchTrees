/**
* Created by yaroslav on 27.02.17.
*/
interface Tree <K : Comparable<K>, V> {
	
	public fun insert(key: K, value: V)
	public fun search(key: K): V?
	public fun delete(key: K)
}