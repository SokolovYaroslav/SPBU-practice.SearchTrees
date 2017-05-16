package BinarySearchTree
import BinaryNode
import Tree

/**
* Created by Yaroslav Sokolov on 28.02.17.
*/
class BST<K : Comparable<K>, V>(internal var root: BinaryNode<K, V>? = null) : Tree<K, V>, Iterable<BinaryNode<K, V>> {
	
	override fun iterator(): Iterator<BinaryNode<K, V>> {
		return BSTIterator(this)
	}
	
	override fun search(key: K): V? {
		val currentNode = searchByKey(key)
		
		if (currentNode == null) {
			return null
		}
		else {
			return currentNode.value
		}
	}
	
	private fun searchByKey(key: K): BinaryNode<K, V>? {
		var currentNode: BinaryNode<K, V>? = root
		
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
	}
	
	override fun delete(key: K) {
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
}