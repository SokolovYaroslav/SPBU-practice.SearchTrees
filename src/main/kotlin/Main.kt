import BTree.BTree
import BinarySearchTree.BST
import BinarySearchTree.BSTPrinter
import RedBlackTree.RBT
import RedBlackTree.RBTPrinter
import BTree.BPrinter

/**
* Created by Yaroslav Sokolov on 05.03.17.
*/
public  fun main(args: Array<String>) {
	val tree: Tree<Int, Int>
	val printer: Printer<Int, Int>
	var line: String?

	println("""Choose type fo tree (just type a word):
BST - Binary search tree
RBT - Red black tree
B - B tree.""")

	chooseTree@ while (true) {
		line = readLine()
		when (line) {
			"BST" -> {
				tree = BST<Int, Int>() as Tree<Int, Int>
				printer = BSTPrinter<Int, Int>() as Printer<Int, Int>
				break@chooseTree
			}
			"RBT" -> {
				tree = RBT<Int, Int>() as Tree<Int, Int>
				printer = RBTPrinter<Int, Int>() as Printer<Int, Int>
				break@chooseTree
			}
			"B" -> {
				tree = BTree<Int, Int>() as Tree<Int, Int>
				printer = BPrinter<Int, Int>() as Printer<Int, Int>
				break@chooseTree
			}
			else -> {
				println("Invalid tree. Try again.")
				continue@chooseTree
			}

		}
	}

	println("""Use Int as argument. There are list of commands:
A %argument% for add
S %argument% for serch
D %argument% for delete
P for print
Q to quit""")
	mainLoop@ while (true) {
		line = readLine()
		when (line) {
			null -> {
				println("Input is null. Try again.")
				continue@mainLoop
			}
			else -> {
				val splited: List<String> = line.trim().split(" ")
				val key: Int?
				when  {
					splited.size < 2 -> key = null
					else -> {
						try {
							key = splited[1].toInt()
						}
						catch (exception: NumberFormatException) {
							println("Invalid key, try again.")
							continue@mainLoop
						}
					}
				}
				when (splited[0]) {
					"A" -> {
						if (key == null) {
							println("Key is null. Try again.")
							continue@mainLoop
						}
						tree.insert(key, key)
					}
					"S" -> {
						if (key == null) {
							println("Key is null. Try again.")
							continue@mainLoop
						}
						println(tree.search(key))
					}
					"D" -> {
						if (key == null) {
							println("Key is null. Try again.")
							continue@mainLoop
						}
						tree.delete(key)
					}
					"P" -> {
						printer.print(tree)
					}
					"Q" -> break@mainLoop
					else -> {
						println("Invalid operation with tree. Try again.")
						continue@mainLoop
					}
				}
			}
		}
	}
}