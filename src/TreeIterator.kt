/**
 * Created by yaroslav on 03.03.17.
 */
class TreeIterator<T : Comparable<T>>(val tree: Tree<T>): Iterator<Node<T>> {
	
	var next = tree.maxByNode()
	
	override fun hasNext(): Boolean {
		return next != null
	}
	
	override fun next(): Node<T> {
		if (next == null) {
			throw NullPointerException()
		}
		val peviousNode = next
		
		if (next!!.leftChild != null) {
			next = tree.maxByNode(next!!.leftChild)
		}
		else {
			while (next != tree.getRoot() && next == next!!.parent!!.leftChild) {
				next = next!!.parent
			}
			next = next!!.parent
		}
			
		return peviousNode!!
	}
}