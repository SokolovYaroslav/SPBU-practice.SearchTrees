import BTree.BTree
import BinarySearchTree.BST
import RedBlackTree.RBT
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

/**
* Created by Yaroslav Sokolov on 17.05.17.
*/
internal class CompareTreeTest {

    val random = Random()

    @Test
    fun searchInDegenerateBST() {
        val tree = BST<Int, Int>()
        val nodesAmount = 1000000
        for (i in 0..nodesAmount - 1) {
            tree.insert(i, i)
        }
        var time = 0L
        val key = nodesAmount - 1
        val startTime = System.nanoTime()
        tree.search(key)
        val endTime = System.nanoTime()
        time += endTime - startTime
        println("Search in degenerate BST: $time ns")
    }

    @Test
    fun searchInRandomBST() {
        val tree = BST<Int, Int>()
        val nodesAmount = 1000000
        val randomKeys = ArrayList<Int>()
        for (i in 0..nodesAmount - 1) {
            val key = random.nextInt()
            tree.insert(key, key)
            randomKeys.add(key)
        }
        var time = 0L
        val key = randomKeys[random.nextInt(31) % nodesAmount]
        val startTime = System.nanoTime()
        tree.search(key)
        val endTime = System.nanoTime()
        time += endTime - startTime
        println("Search in random BST: $time ns")
    }

    @Test
    fun searchInRBT() {
        val tree = RBT<Int, Int>()
        val nodesAmount = 1000000
        val randomKeys = ArrayList<Int>()
        for (i in 0..nodesAmount - 1) {
            val key = random.nextInt()
            tree.insert(key, key)
            randomKeys.add(key)
        }
        var time = 0L
        val key = randomKeys[random.nextInt(31) % nodesAmount]
        val startTime = System.nanoTime()
        tree.search(key)
        val endTime = System.nanoTime()
        time += endTime - startTime
        println("Search in random RBT: $time ns")
    }

    @Test
    fun searchInBTree() {
        val tree = BTree<Int, Int>()
        val nodesAmount = 10000000
        val randomKeys = ArrayList<Int>()
        for (i in 0..nodesAmount - 1) {
            val key = random.nextInt()
            tree.insert(key, key)
            randomKeys.add(key)
        }
        var time = 0L
        val key = randomKeys[random.nextInt(31) % nodesAmount]
        val startTime = System.nanoTime()
        tree.search(key)
        val endTime = System.nanoTime()
        time += endTime - startTime
        println("Search in random BTree: $time ns")
    }
}