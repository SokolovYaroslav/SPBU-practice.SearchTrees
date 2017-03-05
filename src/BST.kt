/**
 * Created by yaroslav on 28.02.17.
 */
class BST<T : Comparable<T>>(internal var root: Node<T>? = null) : Tree<T> {
	override fun getRoot(): Node<T>? {
		return this.root
	}
	
	override fun addByKey(key: T, value: MutableList<Any>) {
		val newNode = Node<T>(key, value)
		var currentNode: Node<T>? = root
		var previousNode: Node<T>? = null
		
		while (currentNode != null) {
			previousNode = currentNode
			when {
				newNode.key < currentNode.key    -> {
					currentNode = currentNode.leftChild
				}
				newNode.key > currentNode.key -> {
					currentNode = currentNode.rightChild
				}
				newNode.key == currentNode.key -> {
					currentNode.addValue(newNode.getValue())
					return
				}
			}
		}
		newNode.parent = previousNode
		if (previousNode == null) {
			root = newNode
		}
		else if (newNode.key < previousNode.key) {
			previousNode.leftChild = newNode
		}
		else {
			previousNode.rightChild = newNode
		}
	}
	
	override fun deleteNodeByKey(key: T) {
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