package RedBlackTree
import BinaryNode

/**
* Created by Yaroslav Sokolov on 03.03.17.
*/
class RBTIterator<K : Comparable<K>, V>(val tree: RBT<K, V>): Iterator<BinaryNode<K, V>> {
	
	var next = tree.maxByNode()
	
	override fun hasNext(): Boolean {
		return next != null
	}
	
	override fun next(): BinaryNode<K, V> {
		val previousNode = next
		
		if (next!!.leftChild != null) {
			next = tree.maxByNode(next!!.leftChild)
		}
		else {
			while (next != tree.root && next == next!!.parent!!.leftChild) {
				next = next!!.parent
			}
			next = next!!.parent
		}
			
		return previousNode!!
	}
}