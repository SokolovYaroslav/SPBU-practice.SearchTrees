/**
 * Created by yaroslav on 14.03.17.
 */
class BSTIterator<K : Comparable<K>, V>(val tree: BST<K, V>): Iterator<Node<K, V>> {
	
	var next = tree.maxByNode()
	
	override fun hasNext(): Boolean {
		return next != null
	}
	
	override fun next(): Node<K, V> {
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