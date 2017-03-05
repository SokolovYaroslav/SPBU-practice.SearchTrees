/**
 * Created by yaroslav on 27.02.17.
 */
class Node <T : Comparable<T>>(val key: T, private var value: MutableList<Any>) {
		
	private var isRed: Boolean = false
	internal var leftChild: Node<T>? = null
	internal var rightChild: Node<T>? = null
	internal var parent: Node<T>? = null
	
	public fun addValue(newValue: MutableList<Any>) = this.value.addAll(newValue)
	public fun deleteValue(deletingValue: MutableList<Any>) = this.value.removeAll { it in deletingValue }
	public fun getValue() = this.value
	public fun getColour() = isRed
}