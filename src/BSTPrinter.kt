/**
 * Created by yaroslav on 14.03.17.
 */
class BSTPrinter <K : Comparable<K>, V> {
	public fun print(tree: BST<K, V>) {
		for (node in tree) {
			for (i in 1..tree.getHeightByKey(node.key) * 5) {
				print(' ')
			}
			println(node.getValue())
		}
	}
}