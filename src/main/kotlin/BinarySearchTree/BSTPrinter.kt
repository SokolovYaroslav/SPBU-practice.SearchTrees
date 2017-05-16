package BinarySearchTree
import Printer
import Tree

/**
* Created by Yaroslav Sokolov on 14.03.17.
*/
class BSTPrinter <K : Comparable<K>, V> : Printer<K, V> {
	override fun print(tree: Tree<K, V>) {
		tree as BST<K, V>
		for (node in tree) {
			for (i in 1..tree.getHeightByKey(node.key) * 5) {
				print(' ')
			}
			println(node.getValue())
		}
	}
}