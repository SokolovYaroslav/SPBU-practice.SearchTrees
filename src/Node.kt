/**
 * Created by yaroslav on 27.02.17.
 */
class Node <K : Comparable<K>, V>(val key: K, private var value: V) {
		
	internal var isRed: Colour = Colour.Black
	internal var leftChild: Node<K, V>? = null
	internal var rightChild: Node<K, V>? = null
	internal var parent: Node<K, V>? = null
	
//	public fun addValue(newValue: MutableList<Any>) = this.value.addAll(newValue)
//	public fun deleteValue(deletingValue: MutableList<Any>) = this.value.removeAll { it in deletingValue }
	public fun getValue() = this.value
	public fun getColour() = isRed.bool
	
	internal fun rotateLeft(tree: RBT<K, V>) {
		if (this.rightChild == null) {
			throw UnsupportedOperationException()
		}
		else {
			val newTop: Node<K, V> = this.rightChild!!
			
			this.rightChild = rightChild!!.leftChild
			if (newTop.leftChild != null) {
				newTop.leftChild!!.parent = this
			}
			newTop.parent = this.parent
			when {
				this.parent == null -> tree.root = newTop
				this == this.parent!!.leftChild -> this.parent!!.leftChild = newTop
				this == this.parent!!.rightChild -> this.parent!!.rightChild = newTop
			}
			newTop.leftChild = this
			this.parent = newTop
		}
	}
	
	internal fun rotateRight(tree: RBT<K, V>) {
		if (this.leftChild == null) {
			throw UnsupportedOperationException()
		}
		else {
			val newTop: Node<K, V> = this.leftChild!!
			
			this.leftChild = newTop!!.rightChild
			if (newTop.rightChild != null) {
				newTop.rightChild!!.parent = this
			}
			newTop.parent = this.parent
			when {
				this.parent == null -> tree.root = newTop
				this == this.parent!!.rightChild -> this.parent!!.rightChild = newTop
				this == this.parent!!.leftChild -> this.parent!!.leftChild = newTop
			}
			newTop.rightChild = this
			this.parent = newTop
		}
	}
	
	internal fun brother(): Node<K, V>? {
		if (this.parent == null) return null
		if (this.parent!!.leftChild == this) {
			return this.parent!!.rightChild
		}
		else {
			return this.parent!!.leftChild
		}
	}
	
	internal fun uncle(): Node<K, V>? {
		return this.parent!!.brother()
	}
}