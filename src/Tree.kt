/**
* Created by yaroslav on 27.02.17.
*/
interface Tree <K : Comparable<K>, V> {
	public fun getRoot(): Node<K, V>?
	
	public fun addByKey(key: K, value: V)
	public fun searchByKey(key: K): Node<K, V>?
	public fun deleteNodeByKey(key: K, nodeStart: Node<K, V>? = getRoot())
}