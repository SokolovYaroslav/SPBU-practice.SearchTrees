import java.util.*
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer

/**
 * Created by yaroslav on 31.03.17.
 */
class BTree <K : Comparable<K>, V>(internal var root: BNode<K, V> = BNode(), val t: Int = 1000) : Tree<K, V>, Iterable<BNode<K, V>>{
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
	
	override fun insert(key: K, value: V) {
		if (searchPrivate(key) != null) return
		
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
	
	override fun search(key: K): V? {
		return searchPrivate(key)?.second
	}
	
	private fun searchPrivate(key: K): Pair<K, V>? {
		var currentNode: BNode<K, V>? = root?: return null
		
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
	
	override fun delete(key: K) {
		deletePrivate(key, root)
	}
	
	private fun deletePrivate(key: K, startNode: BNode<K, V>? = root) {
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
				deletePrivate(newKey.first, deletingNode.children[i])
				deletingNode.pairs[i] = newKey
			}
			else if (deletingNode.children[i + 1].pairs.size > t - 1) {
				val newKey = minByNode(deletingNode.children[i + 1])
				deletePrivate(newKey.first, deletingNode.children[i + 1])
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
				deletePrivate(key, leftChild)
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
				deletePrivate(key, deletingNode)
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
					deletePrivate(key, deletingNode)
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
					deletePrivate(key, deletingNode)
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
							println("Все плохо!")
						}
					}
					deletePrivate(key, leftBro)
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
							println("Все плохо!")
						}
					}
					deletePrivate(key, deletingNode)
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