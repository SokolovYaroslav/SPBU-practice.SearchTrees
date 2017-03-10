/**
 * Created by yaroslav on 05.03.17.
 */
class TreePrinter <K : Comparable<K>, V> {
	public fun print(tree: Tree<K, V>) {
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