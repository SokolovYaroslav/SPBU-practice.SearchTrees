/**
 * Created by yaroslav on 05.03.17.
 */
class RBT <K : Comparable<K>, V>(internal var root: Node<K, V>? = null) : Tree<K, V>, Iterable<Node<K, V>>{
	override fun getRoot(): Node<K, V>? {
		return root
	}
	
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
	
	override fun deleteNodeByKey(key: K, nodeStart: Node<K, V>?) {
		val node = searchByKey(key) ?: return
		val min = minByNode(node.rightChild)
		
		when {
			((node.rightChild != null) && (node.leftChild != null)) -> {
				node.key = min!!.key
				node.value = min.value
				deleteNodeByKey(min.key, min)
			}
			((node == root) && node.leftChild == null && node.rightChild == null) -> {
				root = null
				return
			}
			(node.getColour() && node.leftChild == null && node.rightChild == null)	-> {
				if (node.key < node.parent!!.key) {
					node.parent!!.leftChild = null
				} else {
					node.parent!!.rightChild = null
				}
				return
			}
			(!node.getColour() && ((node.leftChild != null) && (node.leftChild!!.getColour())))	-> {
				node.key = node.leftChild!!.key
				node.leftChild = null
				return
			}
			(!node.getColour() && (node.rightChild != null) && (node.rightChild!!.getColour()))	-> {
				node.key = node.rightChild!!.key
				node.rightChild = null
				return
			}
			else -> {
				case1(node)
			}
		}
		
		if (node.key == key) {
			if (node.key < node.parent!!.key) {
				node.parent!!.leftChild = null
			} else {
				node.parent!!.rightChild = null
			}
		}
		return
	}
	
	private fun case1(node: Node<K, V>) {
		if (node == root) {
			node.isRed = Colour.Black
			return
		}
		
		if (node.key < node.parent!!.key) {
			case2Left(node)
		} else {
			case2Right(node)
		}
	}
	
	private fun case2Left(node: Node<K, V>) {
		val brother = node.brother()
		
		if (brother!!.getColour()) {
			node.parent!!.recoloring()
			brother.recoloring()
			node.parent!!.rotateLeft(this)
			case1(node)
			return
		}
		
		case3(node)
	}
	
	private fun case2Right(node: Node<K, V>) {
		val brother = node.brother()
		
		if (brother!!.getColour()) {
			node.parent!!.recoloring()
			brother.recoloring()
			node.parent!!.rotateRight(this)
			case1(node)
			return
		}
		
		case3(node)
	}
	
	private fun case3(node: Node<K, V>) {
		val brother = node.brother()
		
		if (((brother!!.leftChild == null) || !brother.leftChild!!.getColour())
				&&
				((brother.rightChild == null) || !brother.rightChild!!.getColour()))
		{
			node.isRed = Colour.Black
			brother.recoloring()
			if (node.parent!!.getColour()) {
				node.parent!!.recoloring()
				return
			}
			case1(node.parent!!)
			return
		}
		
		if (node.key < node.parent!!.key) {
			case4Left(node)
		} else {
			case4Right(node)
		}
	}
	
	private fun case4Left(node: Node<K, V>) {
		val brother = node.brother()
		
		if ((brother!!.rightChild == null) || !brother.rightChild!!.getColour()) {
			brother.recoloring()
			brother.leftChild!!.recoloring()
			brother.rotateRight(this)
			case1(node)
			return
		}
		
		case5Left(node)
	}
	
	private fun case4Right(node: Node<K, V>) {
		val brother = node.brother()
		
		if ((brother!!.leftChild == null) || !brother.leftChild!!.getColour()) {
			brother.recoloring()
			brother.rightChild!!.recoloring()
			brother.rotateLeft(this)
			case1(node)
			return
		}
		
		case5Right(node)
	}
	
	private fun case5Left(node: Node<K, V>) {
		val brother = node.brother()
		
		if ((brother!!.rightChild != null) && brother.rightChild!!.getColour()) {
			brother.isRed = node.parent!!.isRed
			node.isRed = Colour.Black
			node.parent!!.isRed = Colour.Black
			brother.rightChild!!.isRed = Colour.Red
			node.parent!!.rotateLeft(this)
			return
		}
	}
	
	private fun case5Right(node: Node<K, V>) {
		val brother = node.brother()
		
		if ((brother!!.leftChild != null) && brother.leftChild!!.getColour()) {
			brother.isRed = node.parent!!.isRed
			node.isRed = Colour.Black
			node.parent!!.isRed = Colour.Black
			brother.leftChild!!.isRed = Colour.Black
			node.parent!!.rotateRight(this)
			return
		}
	}
	
	
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
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other?.javaClass != javaClass) return false
		
		other as RBT<*, *>
		
		if (root != other.root) return false
		
		val otherIterator = other.iterator()
		
		for (node1 in this) {
			if (!otherIterator.hasNext()) return false
			if (node1 != otherIterator.next()) return false
		}
		if (otherIterator.hasNext()) return false
		
		return true
	}
}