package RedBlackTree
import Printer
import Tree

/**
* Created by Yaroslav Sokolov on 05.03.17.
*/
class RBTPrinter <K : Comparable<K>, V> : Printer<K, V> {
	override public fun print(tree: Tree<K, V>) {
		tree as RBT
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