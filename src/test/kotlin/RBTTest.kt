@file:Suppress("DEPRECATION")

import org.junit.Test
import junit.framework.Assert.*

import java.util.*

/**
* Created by Yaroslav Sokolov on 16.03.17.
*/
internal class RBTTest {

	val random = Random()

	@Test
	fun colour1SizeTree() {
		val tree = RedBlackTree.RBT<Int, Int>(BinaryNode(2, 90))
		assertEquals(Colour.Black, tree.root!!.isRed)
	}
	@Test
	fun searchInEmptyTree() {
		val tree = RedBlackTree.RBT<Int, Int>()
		assertEquals(null, tree.search(random.nextInt()))
	}
	@Test
	fun searchInSize1Tree() {
		val tree = RedBlackTree.RBT<Int, Int>(BinaryNode(100000, -1))
		assertEquals(null, tree.search(random.nextInt() % 100000))
		assertEquals(-1, tree.search(100000))
	}
	@Test
	fun insert() {
		val iTree = RedBlackTree.RBT<Int, Int>(BinaryNode(4, 4))

		val one = BinaryNode<Int, Int>(1, 1)

		val seven = BinaryNode<Int, Int>(7, 7)
		seven.isRed = Colour.Red

		val three = BinaryNode<Int, Int>(3, 3)
		three.isRed = Colour.Red

		val five = BinaryNode<Int, Int>(5, 5)

		val eight = BinaryNode<Int, Int>(8, 8)

		val six = BinaryNode<Int, Int>(6, 6)
		six.isRed = Colour.Red

		val twelve = BinaryNode<Int, Int>(12, 12)
		twelve.isRed = Colour.Red

		iTree.root!!.leftChild = one
		one.parent = iTree.root

		iTree.root!!.rightChild = seven
		seven.parent = iTree.root

		one.rightChild = three
		three.parent = one

		seven.leftChild = five
		five.parent = seven

		five.rightChild = six
		six.parent = five

		seven.rightChild = eight
		eight.parent = seven

		eight.rightChild = twelve
		twelve.parent = eight

		val tree = RedBlackTree.RBT<Int, Int>(BinaryNode(7, 7))
		tree.insert(1, 1)
		tree.insert(4, 4)
		tree.insert(8, 8)
		tree.insert(3, 3)
		tree.insert(5, 5)
		tree.insert(6, 6)
		tree.insert(12, 12)
		assertEquals(iTree, tree)
	}
	@Test
	fun randomSearch() {
		val tree = RedBlackTree.RBT<Int, Int>()
		for (i in 0..10000) {
			tree.insert(i, i)
		}
		val i = random.nextInt(10000)
		assertEquals(i, tree.search(i))
	}
	@Test
	fun iterator() {
		val tree = RedBlackTree.RBT<Int, Int>()
		for (i in 1..2000)
		{
			val j = random.nextInt()
			tree.insert(j,j)
		}
		var previousKey = tree.maxByNode()!!.key + 1
		for (i in tree) {
			assertTrue(i.key < previousKey)
			previousKey = i.key
		}
	}
	@Test
	fun randomInsert() {
		val tree = RedBlackTree.RBT<Int, Int>()
		for (i in 0..1000) {
			val j = random.nextInt()
			tree.insert(j,j)
			assertEquals(true, tree.isItRbTree())
			iterator()
		}
	}
	@Test
	fun deleteFromEmptyTree() {
		val tree = RedBlackTree.RBT<Int, Int>()
		tree.delete(random.nextInt())
	}
	@Test
	fun randomDelete() {
		val tree = RedBlackTree.RBT<Int, Int>()
		for (i in 0..10000) {
			tree.insert(i, i)
		}
		for (i in 0..10000) {
			tree.delete(random.nextInt() % 10000)
			assertEquals(true, tree.isItRbTree())
			iterator()
		}
	}
	@Test
	fun hardTest() {
		val tree = RedBlackTree.RBT<Int, Int>()
		for (i in 1..20000000)
		{
			tree.insert(i,i)
		}
		for (i in 0..10000) {
			val j = random.nextInt(20000000)
			assertEquals(j, tree.search(j))
		}
	}
}