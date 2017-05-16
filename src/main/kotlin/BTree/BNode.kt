package BTree
import Node

/**
* Created by Yaroslav Sokolov on 31.03.17.
*/
class BNode <K : Comparable<K>, V>() : Node<K, V> {
	
	var leaf: Boolean = true
	var pairs = ArrayList<Pair<K, V>>()
	var children = ArrayList<BNode<K, V>>()
}