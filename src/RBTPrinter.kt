/**
 * Created by yaroslav on 05.03.17.
 */
class RBTPrinter <K : Comparable<K>, V> {
	public fun print(tree: RBT<K, V>) {
		for (node in tree) {
			for (i in 1..tree.getHeightByKey(node.key) * 5) {
				print(' ')
			}
			if (!node.getColour()) {
				println(node.getValue())
			}
			else {
				println(27.toChar() + "[31m"+ node.getValue() +27.toChar() + "[0m")
			}
		}
	}
}