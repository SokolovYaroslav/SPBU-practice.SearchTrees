/**
 * Created by yaroslav on 27.02.17.
 */
class BinaryNode<K : Comparable<K>, V>(internal var key: K, internal var value: V) : Node<K, V> {
	
	override fun equals(other: Any?): Boolean {
		if (other is BinaryNode<*, *>) {
			if (this.isRed == other.isRed &&
					this.key == other.key &&
					this.value == other.value) {
				return true
			}
		}
		return false
	}
	
	internal var isRed: Colour = Colour.Black
	internal var leftChild: BinaryNode<K, V>? = null
	internal var rightChild: BinaryNode<K, V>? = null
	internal var parent: BinaryNode<K, V>? = null
	
//	public fun addValue(newValue: MutableList<Any>) = this.value.addAll(newValue)
//	public fun deleteValue(deletingValue: MutableList<Any>) = this.value.removeAll { it in deletingValue }
	public fun getValue() = this.value
	public fun getColour() = isRed.bool
	
	internal fun recoloring() {
		if (this.getColour()) {
			this.isRed = Colour.Black
		}
		else {
			this.isRed = Colour.Red
		}
	}
	
	internal fun rotateLeft(tree: RBT<K, V>) {
		if (this.rightChild == null) {
			throw UnsupportedOperationException()
		}
		else {
			val newTop: BinaryNode<K, V> = this.rightChild!!
			
			this.rightChild = this.rightChild!!.leftChild
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
			val newTop: BinaryNode<K, V> = this.leftChild!!
			
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
	
	internal fun brother(): BinaryNode<K, V>? {
		if (this.parent == null) return null
		if (this.parent!!.leftChild == this) {
			return this.parent!!.rightChild
		}
		else {
			return this.parent!!.leftChild
		}
	}
	
	internal fun uncle(): BinaryNode<K, V>? {
		return this.parent!!.brother()
	}
}