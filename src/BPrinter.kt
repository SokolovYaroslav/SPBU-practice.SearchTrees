/**
 * Created by yaroslav on 31.03.17.
 */
class BPrinter <K : Comparable<K>, V> {
	public fun print(tree: BTree<K, V>) {
		var current = 1
		var next = 0
		for (node in tree) {
			if (current == 0) {
				current = next
				next = 0
				println()
			}
			print(node.pairs.toString() + "   ")
			next += node.children.size
			current--
		}
		println()
	}
}