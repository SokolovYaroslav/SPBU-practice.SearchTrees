import java.util.*

/**
 * Created by yaroslav on 31.03.17.
 */
class BTree <K : Comparable<K>, V>(internal var root: BNode<K, V> = BNode(), val t: Int = 1000) : Iterable<BNode<K, V>>{
	override fun iterator(): Iterator<BNode<K, V>> {
		return (object : Iterator<BNode<K, V>> {
			var queue = LinkedList<BNode<K, V>>()
			
			init {
				if (root.children.size + root.pairs.size != 0) {
					queue.add(root)
				}
			}
			override fun hasNext(): Boolean {
				return queue.isNotEmpty()
			}
			
			override fun next(): BNode<K, V> {
				val currentNode = queue.poll()
				if (currentNode.children.isNotEmpty()) {
					queue.addAll(currentNode.children)
				}
				return currentNode
			}
		})
	}
	
	public fun getRoot(): BNode<K, V> {
		return root
	}
	
	public fun insert(key: K, value: V) {
		if (searchByKey(key) != null) return
		
		val oldRoot = root
		if (oldRoot.pairs.size == 2 * t - 1) {
			root = BNode()
			root.leaf = false
			root.children.add(oldRoot)
			splitChild(root, 0)
			insertNonFull(root, key, value)
		}
		else {
			insertNonFull(root, key, value)
		}
	}
	
	private fun insertNonFull(node: BNode<K, V>, key: K, value: V) {
		var i = 0
		if (node.leaf) {
			while (i < node.pairs.size && key > node.pairs[i].first) {
				i++
			}
			node.pairs.add(i, Pair(key, value))
		}
		else {
			while (i < node.pairs.size && key > node.pairs[i].first) {
				i++
			}
			if (node.children[i].pairs.size == 2 * t - 1) {
				splitChild(node, i)
				if (key > node.pairs[i].first) {
					i++
				}
			}
			insertNonFull(node.children[i], key, value)
		}
	}
	
	private fun splitChild(node: BNode<K, V>, index: Int) {
		val newNode = BNode<K, V>()
		val splitingNode = node.children[index]
		newNode.leaf = splitingNode.leaf
		for (uselessIndex in 0..t - 2) {
			newNode.pairs.add(splitingNode.pairs[t])
			splitingNode.pairs.removeAt(t)
		}
		if (!splitingNode.leaf) {
			for (uselessIndex in 0..t - 1) {
				newNode.children.add(splitingNode.children[t])
				splitingNode.children.removeAt(t)
			}
		}
		node.children.add(index + 1, newNode)
		node.pairs.add(index, splitingNode.pairs[t - 1])
		splitingNode.pairs.removeAt(t - 1)
	}
	
	public fun searchByKey(key: K): Pair<K, V>? {
		var currentNode: BNode<K, V>? = getRoot()?: return null
		
		while (currentNode != null) {
			var i = 0
			while (i < currentNode.pairs.size && key > currentNode.pairs[i].first) {
				i++
			}
			if (currentNode.pairs.size > i) {
				if (key == currentNode.pairs[i].first) {
					return currentNode.pairs[i]
				}
			}
			if (currentNode.children.size > i) {
				currentNode = currentNode.children[i]
			}
			else {
				currentNode = null
			}
		}
		return null
	}
	
	public fun delete(key: K, startNode: BNode<K, V>? = getRoot()) {
		var deletingNode: BNode<K, V>? = startNode?: return
		
		if (key in deletingNode!!.pairs.map { it.first } && deletingNode.leaf) {
			var i = 0
			while (i < deletingNode.pairs.size && key > deletingNode.pairs[i].first) {
				i++
			}
			deletingNode.pairs.removeAt(i)
		}
		else if (key in deletingNode.pairs.map { it.first } && !deletingNode.leaf) {
			var i = 0
			while (i < deletingNode.pairs.size && key > deletingNode.pairs[i].first) {
				i++
			}
			if (deletingNode.children[i].pairs.size > t - 1) {
				val newKey = maxByNode(deletingNode.children[i])
				delete(newKey.first, deletingNode.children[i])
				deletingNode.pairs[i] = newKey
			}
			else if (deletingNode.children[i + 1].pairs.size > t - 1) {
				val newKey = minByNode(deletingNode.children[i + 1])
				delete(newKey.first, deletingNode.children[i + 1])
				deletingNode.pairs[i] = newKey
			}
			else {
				val leftChild = deletingNode.children[i]
				val rightChild = deletingNode.children[i + 1]
				leftChild.pairs.add(deletingNode.pairs[i])
				leftChild.children.add(rightChild.children[0])
				rightChild.children.removeAt(0)
				for (uselessIndex in 0..t - 2) {
					leftChild.pairs.add(rightChild.pairs[0])
					rightChild.pairs.removeAt(0)
					leftChild.children.add(rightChild.children[0])
					rightChild.children.removeAt(0)
				}
				if (deletingNode == root && deletingNode.pairs.size == 1) {
					root = leftChild
				}
				delete(key, leftChild)
			}
		}
		else if (!(key in deletingNode.pairs.map { it.first })) {
			if (deletingNode.leaf) {
				return
			}
			var i = 0
			while (i < deletingNode.pairs.size && key > deletingNode.pairs[i].first) {
				i++
			}
			val parentNode = deletingNode
			deletingNode = deletingNode.children[i]
			if (deletingNode.pairs.size > t - 1) {
				delete(key, deletingNode)
			}
			else {
				if (i > 1  && parentNode.children[i - 1].pairs.size > t - 1) {
					val leftBro = parentNode.children[i - 1]
					deletingNode.pairs.add(0, parentNode.pairs[i - 1])
					parentNode.pairs[i - 1] = leftBro.pairs.last()
					if (!deletingNode.leaf) {
						deletingNode.children.add(0, leftBro.children.last())
						leftBro.children.removeAt(leftBro.children.size - 1)
					}
					leftBro.pairs.removeAt(leftBro.pairs.size - 1)
					delete(key, deletingNode)
				}
				else if (i < parentNode.pairs.size && parentNode.children[i + 1].pairs.size > t - 1) {
					val rightBro = parentNode.children[i + 1]
					deletingNode.pairs.add(parentNode.pairs[i])
					parentNode.pairs[i] = rightBro.pairs[0]
					if (!deletingNode.leaf) {
						deletingNode.children.add(deletingNode.children.size, rightBro.children[0])
						rightBro.children.removeAt(0)
					}
					rightBro.pairs.removeAt(0)
					delete(key, deletingNode)
				}
				else if (i > 1) {
					val leftBro = parentNode.children[i - 1]
					
					leftBro.pairs.add(parentNode.pairs[i - 1])
					parentNode.pairs.removeAt(i - 1)
					parentNode.children.removeAt(i)
					for (uselessIndex in 0..t - 2) {
						leftBro.pairs.add(deletingNode.pairs[0])
						deletingNode.pairs.removeAt(0)
					}
					if (!leftBro.leaf) {
						for (uselessIndex in 0..t - 1) {
							leftBro.children.add(deletingNode.children[0])
							deletingNode.children.removeAt(0)
						}
					}
					if (parentNode.pairs.size == 0) {
						if (parentNode == root) {
							root = leftBro
						}
						else {
							var previousNode = root
							var currentNode = root
							var j = 0
							while (currentNode.pairs.size != 0) {
								while (j < currentNode.pairs.size && key > currentNode.pairs[j].first) {
									j++
								}
								previousNode = currentNode
								currentNode = currentNode.children[j]
							}
							previousNode.children[j] = leftBro
						}
					}
					delete(key, leftBro)
				}
				else if (i < parentNode.children.size) {
					val rightBro = parentNode.children[i + 1]
					
					deletingNode.pairs.add(parentNode.pairs[i])
					parentNode.pairs.removeAt(i)
					parentNode.children.removeAt(i + 1)
					for (uselessIndex in 0..t - 2) {
						deletingNode.pairs.add(rightBro.pairs[0])
						rightBro.pairs.removeAt(0)
					}
					if (!deletingNode.leaf) {
						for (i in 0..t - 1) {
							deletingNode.children.add(rightBro.children[0])
							rightBro.children.removeAt(0)
						}
					}
					if (parentNode.pairs.size == 0) {
						if (parentNode == root) {
							root = deletingNode
						}
						else {
							var previousNode = root
							var currentNode = root
							var j = 0
							while (currentNode.pairs.size != 0) {
								while (j < currentNode.pairs.size && key > currentNode.pairs[j].first) {
									j++
								}
								previousNode = currentNode
								currentNode = currentNode.children[j]
							}
							previousNode.children[j] = deletingNode
						}
					}
					delete(key, deletingNode)
				}
			}
		}
	}
	
	private fun maxByNode(node: BNode<K, V>): Pair<K, V> {
		var currentNode = node
		while (!currentNode.leaf) {
			currentNode = currentNode.children.last()
		}
		return currentNode.pairs.last()
	}
	
	private fun minByNode(node: BNode<K, V>): Pair<K, V> {
		var currentNode = node
		while (!currentNode.leaf) {
			currentNode = currentNode.children[0]
		}
		return currentNode.pairs[0]
	}
}