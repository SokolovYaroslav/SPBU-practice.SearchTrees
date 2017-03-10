/**
 * Created by yaroslav on 03.03.17.
 */
class TreeIterator<K : Comparable<K>, V>(val tree: Tree<K, V>): Iterator<Node<K, V>> {
	
	var next = tree.maxByNode()
	
	override fun hasNext(): Boolean {
		return next != null
	}
	
	override fun next(): Node<K, V> {
//		if (next == null) {
//			throw NullPointerException()
//		}
		val previousNode = next
		
		if (next!!.leftChild != null) {
			next = tree.maxByNode(next!!.leftChild)
		}
		else {
			while (next != tree.getRoot() && next == next!!.parent!!.leftChild) {
				next = next!!.parent
			}
			next = next!!.parent
		}
			
		return previousNode!!
	}
}