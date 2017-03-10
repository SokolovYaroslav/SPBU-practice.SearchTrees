/**
* Created by yaroslav on 27.02.17.
*/
interface Tree <K : Comparable<K>, V> : Iterable<Node<K, V>> {
	
	override fun iterator(): Iterator<Node<K, V>> {
		return TreeIterator(this)
	}
	
	public fun getRoot(): Node<K, V>?
	public fun addByKey(key: K, value: V)
	public fun deleteNodeByKey(key: K)
	
	public fun getHeightByKey(key: K): Int {
		var currentNode: Node<K, V>? = getRoot()
		var height: Int = 0
		
		loop@ while (currentNode != null) {
			when {
				key < currentNode.key -> currentNode = currentNode.leftChild
				key > currentNode.key -> currentNode = currentNode.rightChild
				key == currentNode.key -> break@loop
			}
			height++
		}
		
		return height
	}
	
	public fun searchByKey(key: K): Node<K, V>? {
		var currentNode: Node<K, V>? = getRoot()
		
		loop@ while (currentNode != null) {
			when {
				key < currentNode.key -> currentNode = currentNode.leftChild
				key > currentNode.key -> currentNode = currentNode.rightChild
				key == currentNode.key -> break@loop
			}
		}
		
		return currentNode
	}
	
//	public fun deleteValueByKey(key: K, value: MutableList<Any>):Int {
//		val deletingNode = searchByKey(key)
//		if (deletingNode != null) {
//			deletingNode.deleteValue(value)
//			return 0
//		}
//		else {
//			return 1
//		}
//	}
	
	public fun minByNode(subRoot: Node<K, V>? = getRoot()): Node<K, V>? {
		if (subRoot == null) {
			return null
		}
		else {
			var previousNode: Node<K, V> = subRoot
			var currentNode: Node<K, V>? = subRoot
			
			while (currentNode != null) {
				previousNode = currentNode
				currentNode = currentNode.leftChild
			}
			
			return previousNode
		}
	}
	
	public fun maxByNode(subRoot: Node<K, V>? = getRoot()): Node<K, V>? {
		if (subRoot == null) {
			return null
		}
		else {
			var previousNode: Node<K, V> = subRoot
			var currentNode: Node<K, V>? = subRoot
			
			while (currentNode != null) {
				previousNode = currentNode
				currentNode = currentNode.rightChild
			}
			
			return previousNode
		}
	}
}