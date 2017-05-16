/**
* Created by Yaroslav Sokolov on 16.05.17.
*/
interface Printer <K : Comparable<K>, V> {
    fun print(tree: Tree<K, V>)
}