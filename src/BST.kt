/**
 * Created by yaroslav on 28.02.17.
 */
class BST<K : Comparable<K>, V>(private var root: Node<K, V>? = null) : Tree<K, V> {
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
	}
	
	override fun deleteNodeByKey(key: K) {
		val deletingNode = searchByKey(key) ?: return
		
		when {
			deletingNode.leftChild == null && deletingNode.rightChild == null -> {
				when {
					deletingNode.parent == null -> root = null
					deletingNode.key < deletingNode.parent!!.key -> deletingNode.parent!!.leftChild = null
					deletingNode.key > deletingNode.parent!!.key -> deletingNode.parent!!.rightChild = null
				}
			}
			deletingNode.leftChild == null -> {
				when {
					deletingNode.parent == null -> {
						root = deletingNode.rightChild
						deletingNode.rightChild!!.parent = null
					}
					deletingNode.key < deletingNode.parent!!.key -> {
						deletingNode.parent!!.leftChild = deletingNode.rightChild
						deletingNode.rightChild!!.parent = deletingNode.parent
					}
					deletingNode.key > deletingNode.parent!!.key -> {
						deletingNode.parent!!.rightChild = deletingNode.rightChild
						deletingNode.rightChild!!.parent = deletingNode.parent
					}
				}
			}
			deletingNode.rightChild == null -> {
				when {
					deletingNode.parent == null -> {
						root = deletingNode.leftChild
						deletingNode.leftChild!!.parent = null
					}
					deletingNode.key < deletingNode.parent!!.key -> {
						deletingNode.parent!!.leftChild = deletingNode.leftChild
						deletingNode.leftChild!!.parent = deletingNode.parent
					}
					deletingNode.key > deletingNode.parent!!.key -> {
						deletingNode.parent!!.rightChild = deletingNode.leftChild
						deletingNode.leftChild!!.parent = deletingNode.parent
					}
				}
			}
			deletingNode.leftChild != null && deletingNode.rightChild != null -> {
				val newNode = minByNode(deletingNode.rightChild)
				
				when {
					deletingNode.parent == null -> {
						when {
							newNode == deletingNode.rightChild -> newNode!!.leftChild = deletingNode.leftChild
							newNode != deletingNode.rightChild -> {
								newNode!!.parent!!.leftChild = null
								newNode.leftChild = deletingNode.leftChild
								newNode.rightChild = deletingNode.rightChild
							}
						}
						root = newNode
					}
					deletingNode.parent != null -> {
						when {
							newNode == deletingNode.rightChild -> {
								newNode!!.leftChild = deletingNode.leftChild
								newNode.parent = deletingNode.parent
							}
							newNode != deletingNode.rightChild -> {
								newNode!!.parent!!.leftChild = null
								newNode.leftChild = deletingNode.leftChild
								newNode.rightChild = deletingNode.rightChild
								newNode.parent = deletingNode.parent
							}
						}
					}
				}
			}
		}
	}
}