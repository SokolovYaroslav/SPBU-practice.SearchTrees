/**
* Created by yaroslav on 27.02.17.
*/
interface Tree <T : Comparable<T>> : Iterable<Node<T>> {
	
	override fun iterator(): Iterator<Node<T>> {
		return TreeIterator(this)
	}
	
	public fun getRoot(): Node<T>?
	public fun addByKey(key: T, value: MutableList<Any>)
	public fun deleteNodeByKey(key: T)
	
	public fun getHeightByKey(key: T): Int {
		var currentNode: Node<T>? = getRoot()
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
	
	public fun searchByKey(key: T): Node<T>? {
		var currentNode: Node<T>? = getRoot()
		
		loop@ while (currentNode != null) {
			when {
				key < currentNode.key -> currentNode = currentNode.leftChild
				key > currentNode.key -> currentNode = currentNode.rightChild
				key == currentNode.key -> break@loop
			}
		}
		
		return currentNode
	}
	
	public fun deleteValueByKey(key: T, value: MutableList<Any>):Int {
		val deletingNode = searchByKey(key)
		if (deletingNode != null) {
			deletingNode.deleteValue(value)
			return 0
		}
		else {
			return 1
		}
	}
	
	public fun minByNode(subRoot: Node<T>? = getRoot()): Node<T>? {
		if (subRoot == null) {
			return null
		}
		else {
			var previousNode: Node<T> = subRoot
			var currentNode: Node<T>? = subRoot
			
			while (currentNode != null) {
				previousNode = currentNode
				currentNode = currentNode.leftChild
			}
			
			return previousNode
		}
	}
	
	public fun maxByNode(subRoot: Node<T>? = getRoot()): Node<T>? {
		if (subRoot == null) {
			return null
		}
		else {
			var previousNode: Node<T> = subRoot
			var currentNode: Node<T>? = subRoot
			
			while (currentNode != null) {
				previousNode = currentNode
				currentNode = currentNode.rightChild
			}
			
			return previousNode
		}
	}
}