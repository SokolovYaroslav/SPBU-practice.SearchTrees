/**
 * Created by yaroslav on 05.03.17.
 */
class RBT <K : Comparable<K>, V>(internal var root: Node<K, V>? = null) : Tree<K, V>, Iterable<Node<K, V>>{
	override fun iterator(): Iterator<Node<K, V>> {
		return RBTIterator(this)
	}
		
	override fun searchByKey(key: K): Node<K, V>? {
		var currentNode: Node<K, V>? = root
		
		loop@ while (currentNode != null) {
			when {
				key < currentNode.key -> currentNode = currentNode.leftChild
				key > currentNode.key -> currentNode = currentNode.rightChild
				key == currentNode.key -> break@loop
			}
		}
		
		return currentNode
	}
	
	override fun addByKey(key: K, value: V) {
		val newNode = Node<K, V>(key, value)
		var currentNode: Node<K, V>? = root
		var previousNode: Node<K, V>? = null
		
		while (currentNode != null) {
			previousNode = currentNode
			when {
				newNode.key < currentNode.key    -> currentNode = currentNode.leftChild
				newNode.key > currentNode.key -> currentNode = currentNode.rightChild
				newNode.key == currentNode.key -> {
//					currentNode.addValue(newNode.getValue())
					return
				}
			}
		}
		newNode.parent = previousNode
		when {
			previousNode == null -> root = newNode
			newNode.key < previousNode.key -> previousNode.leftChild = newNode
			newNode.key > previousNode.key -> previousNode.rightChild = newNode
		}
		newNode.isRed = Colour.Red
		fixupAfterAdd(newNode)
	}
	
//	override fun deleteNodeByKey(key: K) {
//		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//	}
	
	private fun fixupAfterAdd(newNode: Node<K, V>) {
		if (newNode.parent == null) {
			newNode.isRed = Colour.Black
			
			return
		}
		else {
			var node = newNode
			while (node.parent != null && node.parent!!.isRed == Colour.Red) {
				if (newNode.parent == null) {
					newNode.isRed = Colour.Black
					
					return
				}
				if (node.parent == node.parent!!.parent!!.leftChild) {
					val uncle = node.parent!!.parent!!.rightChild
					if (uncle == null || uncle.isRed == Colour.Black) {
						if (node == node.parent!!.rightChild) {
							node = node.parent!!
							node.rotateLeft(this)
						}
						node.parent!!.isRed = Colour.Black
						node.parent!!.parent!!.isRed = Colour.Red
						node.parent!!.parent!!.rotateRight(this)
					}
					else {
						node.parent!!.isRed = Colour.Black
						uncle.isRed = Colour.Black
						node.parent!!.parent!!.isRed = Colour.Red
						node = node.parent!!.parent!!
					}
				}
				else {
					val uncle = node.parent!!.parent!!.leftChild
					if (uncle == null || uncle.isRed == Colour.Black) {
						if (node == node.parent!!.leftChild) {
							node = node.parent!!
							node.rotateRight(this)
						}
						node.parent!!.isRed = Colour.Black
						node.parent!!.parent!!.isRed = Colour.Red
						node.parent!!.parent!!.rotateLeft(this)
					}
					else {
						node.parent!!.isRed = Colour.Black
						uncle.isRed = Colour.Black
						node.parent!!.parent!!.isRed = Colour.Red
						node = node.parent!!.parent!!
					}
				}
			}
			root!!.isRed = Colour.Black
		}
	}
	
	internal fun maxByNode(subRoot: Node<K, V>? = root): Node<K, V>? {
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
	
	internal fun minByNode(subRoot: Node<K, V>? = root): Node<K, V>? {
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
	
	internal fun getHeightByKey(key: K): Int {
		var currentNode: Node<K, V>? = root
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
}