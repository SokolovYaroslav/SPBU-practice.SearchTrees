import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

/**
 * Created by yaroslav on 16.03.17.
 */
//internal class RBTTest {
//
//	@Test
//	fun searchInEmptyTree() {
//		val tree = RBT<Int, Int>()
//		assertEquals(null, tree.searchByKey(89))
//	}
//	@Test
//	fun colour1SizeTree() {
//		val tree = RBT<Int, Int>(BinaryNode(2, 90))
//		assertEquals(Colour.Black, tree.root!!.isRed)
//	}
//	@Test
//	fun searchInSize1Tree() {
//		val tree = RBT<Int, Int>(BinaryNode(2, 90))
//		assertEquals(null, tree.searchByKey(4334))
//		assertEquals(BinaryNode(2, 90), tree.searchByKey(2))
//	}
//	@Test
//	fun equalsTree() {
//		val iTree = RBT<Int, Int>(BinaryNode(4, 4))
//
//		val one = BinaryNode<Int, Int>(1, 1)
//
//		val seven = BinaryNode<Int, Int>(7, 7)
//		seven.isRed = Colour.Red
//
//		val three = BinaryNode<Int, Int>(3, 3)
//		three.isRed = Colour.Red
//
//		val five = BinaryNode<Int, Int>(5, 5)
//
//		val eight = BinaryNode<Int, Int>(8, 8)
//
//		val six = BinaryNode<Int, Int>(6, 6)
//		six.isRed = Colour.Red
//
//		val twelve = BinaryNode<Int, Int>(12, 12)
//		twelve.isRed = Colour.Red
//
//		iTree.root!!.leftChild = one
//		one.parent = iTree.root
//
//		iTree.root!!.rightChild = seven
//		seven.parent = iTree.root
//
//		one.rightChild = three
//		three.parent = one
//
//		seven.leftChild = five
//		five.parent = seven
//
//		five.rightChild = six
//		six.parent = five
//
//		seven.rightChild = eight
//		eight.parent = seven
//
//		eight.rightChild = twelve
//		twelve.parent = eight
//
//		val tree = RBT<Int, Int>(BinaryNode(7, 7))
//		tree.addByKey(1, 1)
//		tree.addByKey(4, 4)
//		tree.addByKey(8, 8)
//		tree.addByKey(3, 3)
//		tree.addByKey(5, 5)
//		tree.addByKey(6, 6)
//		tree.addByKey(12, 12)
//		assertEquals(iTree, tree)
//	}
//	@Test
//	fun iterator() {
//		val tree = RBT<Int, Int>()
//		for (i in 1..2000)
//		{
//			tree.addByKey(i,i)
//		}
//		var previousKey = tree.maxByNode()!!.key + 1
//		for (i in tree) {
//			assertTrue(i.key < previousKey)
//			previousKey = i.key
//		}
//	}
//	@Test
//	fun hardTest() {
//		val tree = RBT<Int, Int>()
//		for (i in 1..20000000)
//		{
//			tree.addByKey(i,i)
//		}
//	}
//}