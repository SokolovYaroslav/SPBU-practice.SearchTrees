package BTree
import Printer
import Tree

/**
* Created by Yaroslav Sokolov on 31.03.17.
*/
class BPrinter <K : Comparable<K>, V> : Printer<K, V>{
	override public fun print(tree: Tree<K, V>) {
		tree as BTree
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