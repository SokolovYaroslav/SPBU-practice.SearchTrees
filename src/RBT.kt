/**
 * Created by yaroslav on 05.03.17.
 */
class RBT <K : Comparable<K>, V>(internal var root: Node<K, V>? = null) : Tree<K, V>{
	override fun getRoot(): Node<K, V>? {
		return this.root
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
		newNode.isRed = true
		fixupAfterAdd(newNode)
	}
	
	override fun deleteNodeByKey(key: K) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	private fun fixupAfterAdd(newNode: Node<K, V>) {
		if (newNode.parent == null) {
			newNode.isRed = false
			
			return
		}
		else {
			var node = newNode
			while (node.parent!!.isRed == true) {
				if (node.parent == node.parent!!.parent!!.leftChild) {
					val uncle = node.parent!!.parent!!.rightChild
					if (uncle == null || uncle.isRed == false) {
						if (node == node.parent!!.rightChild) {
							node = node.parent!!
							node.rotateLeft(this)
						}
						node.parent!!.isRed = false
						node.parent!!.parent!!.isRed = true
						node.parent!!.parent!!.rotateRight(this)
					}
					else {
						node.parent!!.isRed = false
						uncle.isRed = false
						node.parent!!.parent!!.isRed = true
						node = node.parent!!.parent!!
					}
				}
				else {
					val uncle = node.parent!!.parent!!.leftChild
					if (uncle == null || uncle.isRed == false) {
						if (node == node.parent!!.leftChild) {
							node = node.parent!!
							node.rotateRight(this)
						}
						node.parent!!.isRed = false
						node.parent!!.parent!!.isRed = true
						node.parent!!.parent!!.rotateLeft(this)
					}
					else {
						node.parent!!.isRed = false
						uncle.isRed = false
						node.parent!!.parent!!.isRed = true
						node = node.parent!!.parent!!
					}
				}
			}
		}
	}
	
}