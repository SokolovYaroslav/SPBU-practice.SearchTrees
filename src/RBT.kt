/**
 * Created by yaroslav on 05.03.17.
 */
class RBT <K : Comparable<K>, V>(internal var root: BinaryNode<K, V>? = null) : Tree<K, V>, Iterable<BinaryNode<K, V>>{
	
	override fun iterator(): Iterator<BinaryNode<K, V>> {
		return RBTIterator(this)
	}
	
	public fun isItRbTree():Boolean {
		if (root==null) return true
		return blackHeight(root!!)>=0
	}
	private fun  blackHeight(node: BinaryNode<K, V>) : Int//check
	{
		var leftHeight =0
		var rightHeight = 0
		if (node.leftChild != null) leftHeight = blackHeight(node.leftChild!!)
		if (node.rightChild != null) rightHeight = blackHeight(node.rightChild!!)
		if (leftHeight!=rightHeight) {
			return -100
		}
		if (!node.getColour()) leftHeight++
		return leftHeight
	}
		
	override fun search(key: K): V? {
		val currentNode = searchByKey(key, root)
		
		if (currentNode == null) {
			return null
		}
		else {
			return currentNode.value
		}
	}
	
	private fun searchByKey(key: K, nodeStart: BinaryNode<K, V>?): BinaryNode<K, V>? {
		var currentNode: BinaryNode<K, V>? = nodeStart
		
		loop@ while (currentNode != null) {
			when {
				key < currentNode.key -> currentNode = currentNode.leftChild
				key > currentNode.key -> currentNode = currentNode.rightChild
				key == currentNode.key -> break@loop
			}
		}
		
		return currentNode
	}
	
	override fun insert(key: K, value: V) {
		val newNode = BinaryNode<K, V>(key, value)
		var currentNode: BinaryNode<K, V>? = root
		var previousNode: BinaryNode<K, V>? = null
		
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
	
	override fun delete(key: K) {
		deletePrivate(key, root)
	}
	
	private fun deletePrivate(key: K, nodeStart: BinaryNode<K, V>? = root) {
		val node = searchByKey(key, nodeStart) ?: return
		val min = minByNode(node.rightChild)
		
		when {
			((node.rightChild != null) && (node.leftChild != null)) -> {
				node.key = min!!.key
				node.value = min.value
				deletePrivate(min.key, min)
			}
			((node == root) && node.leftChild == null && node.rightChild == null) -> {
				root = null
				return
			}
			(node.getColour() && node.leftChild == null && node.rightChild == null) -> {
				if (node == node.parent!!.leftChild) {
					node.parent!!.leftChild = null
				} else {
					node.parent!!.rightChild = null
				}
				return
			}
			(!node.getColour() && ((node.leftChild != null) && (node.leftChild!!.getColour()))) -> {
				node.key = node.leftChild!!.key
				node.value = node.leftChild!!.value
				node.leftChild = null
				return
			}
			(!node.getColour() && (node.rightChild != null) && (node.rightChild!!.getColour())) -> {
				node.key = node.rightChild!!.key
				node.value = node.rightChild!!.value
				node.rightChild = null
				return
			}
			else -> {
				case1(node)
			}
		}
		
		if (node.key == key) {
			if (node == node.parent!!.leftChild) {
				node.parent!!.leftChild = null
			} else {
				node.parent!!.rightChild = null
			}
		}
		return
	}
	
	private fun case1(node: BinaryNode<K, V>) {
		if (node == root) {
			node.isRed = Colour.Black
			return
		}
		
		if (node == node.parent!!.leftChild) {
			case2Left(node)
		} else {
			case2Right(node)
		}
	}
	
	private fun case2Left(node: BinaryNode<K, V>) {
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
	
	private fun case2Right(node: BinaryNode<K, V>) {
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
	
	private fun case3(node: BinaryNode<K, V>) {
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
		
		if (node == node.parent!!.leftChild) {
			case4Left(node)
		} else {
			case4Right(node)
		}
	}
	
	private fun case4Left(node: BinaryNode<K, V>) {
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
	
	private fun case4Right(node: BinaryNode<K, V>) {
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
	
	private fun case5Left(node: BinaryNode<K, V>) {
		val brother = node.brother()
		
		if ((brother!!.rightChild != null) && brother.rightChild!!.getColour()) {
			brother.isRed = node.parent!!.isRed
			node.isRed = Colour.Black
			node.parent!!.isRed = Colour.Black
			brother.rightChild!!.isRed = Colour.Black
			node.parent!!.rotateLeft(this)
			return
		}
	}
	
	private fun case5Right(node: BinaryNode<K, V>) {
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
	
	
	private fun fixupAfterAdd(newNode: BinaryNode<K, V>) {
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
	
	internal fun maxByNode(subRoot: BinaryNode<K, V>? = root): BinaryNode<K, V>? {
		if (subRoot == null) {
			return null
		}
		else {
			var previousNode: BinaryNode<K, V> = subRoot
			var currentNode: BinaryNode<K, V>? = subRoot
			
			while (currentNode != null) {
				previousNode = currentNode
				currentNode = currentNode.rightChild
			}
			
			return previousNode
		}
	}
	
	internal fun minByNode(subRoot: BinaryNode<K, V>? = root): BinaryNode<K, V>? {
		if (subRoot == null) {
			return null
		}
		else {
			var previousNode: BinaryNode<K, V> = subRoot
			var currentNode: BinaryNode<K, V>? = subRoot
			
			while (currentNode != null) {
				previousNode = currentNode
				currentNode = currentNode.leftChild
			}
			
			return previousNode
		}
	}
	
	internal fun getHeightByKey(key: K): Int {
		var currentNode: BinaryNode<K, V>? = root
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
	
//	override fun equals(other: Any?): Boolean {
//		if (this === other) return true
//		if (other?.javaClass != javaClass) return false
//
//		other as RBT<*, *>
//
//		if (root != other.root) return false
//
//		val otherIterator = other.iterator()
//
//		for (node1 in this) {
//			if (!otherIterator.hasNext()) return false
//			if (node1 != otherIterator.next()) return false
//		}
//		if (otherIterator.hasNext()) return false
//
//		return true
//	}
}