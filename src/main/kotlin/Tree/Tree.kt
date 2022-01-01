/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat vat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Tree

interface Tree<K: Comparable<K>, V> {
    fun insert(key: K, value: V)

    fun delete(key: K)

    fun find(key: K): Pair<K, V>?
}

class Node<K: Comparable<K>, V>(var key: K, var value: V, var parent: Node<K, V>? = null) {
    var left: Node<K, V>? = null

    var right: Node<K, V>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Node<*, *>

        if (key != other.key) return false
        if (value != other.value) return false
        if (parent != other.parent) return false
        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }
}

class BinarySearchTree<K: Comparable<K>, V>: Tree<K, V>, Iterable<Pair<K, V>> {
    var root: Node<K, V>? = null

    override fun insert(key: K, value: V) {
        var father: Node<K, V>? = null
        var current: Node<K, V>? = root

        while (current != null) {
            father = current

            when {
                key < current.key -> current = current.left

                key > current.key -> current = current.right

                key == current.key -> {
                    current.value = value
                    return
                }
            }
        }

        if (father == null) {
            root = Node(key, value)
            return
        }

        if (key < father.key)
            father.left = Node(key, value, father)
        else
            father.right = Node(key, value, father)
    }

    override fun delete(key: K) {
        val delNode: Node<K, V> = findNode(key) ?: return

        val delParent: Node<K, V>? = delNode.parent

        if(delNode.left == null && delNode.right == null) {
            if (delNode.parent == null) {
                root = null
                return
            }

            if(delNode == delParent?.left)
                delParent.left = null

            if(delNode == delParent?.right)
                delParent.right = null
        }
        else if (delNode.left == null || delNode.right == null) {
            if (delNode.left == null) {
                if (delParent?.left == delNode)
                    delParent.left = delNode.right
                else
                    delParent?.right = delNode.right

                delNode.right?.parent = delParent
            }
            else {
                if (delParent?.left == delNode)
                    delParent.left = delNode.left
                else
                    delParent?.right = delNode.left

                delNode.left?.parent = delParent
            }
        }
        else {
            val successor: Node<K, V> = min(delNode.right)!!
            delNode.key = successor.key
            delNode.value = successor.value

            if (successor.parent?.left == successor) {
                successor.parent?.left = successor.right

                if (successor.right != null)
                    successor.right!!.parent = successor.parent
            }
            else {
                successor.parent?.right = successor.right

                if (successor.right != null)
                    successor.right!!.parent = successor.parent
            }
        }
    }

    override fun find(key: K): Pair<K, V>? {
        val result = findNode(key)

        if (result == null)
            return null
        else
            return Pair(result.key, result.value)
    }

    private fun findNode(key: K): Node<K, V>? {
        var current = root

        while (current != null ) {
            if (key == current.key)
                return current

            if (key < current.key)
                current = current.left
            else
                current = current.right
        }
        return null
    }

    override fun iterator(): Iterator<Pair<K, V>> {
        return (object: Iterator<Pair<K, V>> {
            var node = max(root)
            var next = max(root)
            val last = min(root)

            override fun hasNext(): Boolean {
                return node != null && node!!.key >= last!!.key
            }

            override fun next(): Pair<K, V> {
                next = node
                node = nextSmaller(node)
                return Pair(next!!.key, next!!.value)
            }
        })
    }

    private fun nextSmaller(node: Node<K, V>?): Node<K, V>? {
        var smaller = node ?: return null

        if (smaller.left != null) {
            return max(smaller.left!!)
        }

        else if (smaller == smaller.parent?.left) {
            while (smaller == smaller.parent?.left)
                smaller = smaller.parent!!
        }
        return smaller.parent
    }

    private fun min(rootNode: Node<K, V>?): Node<K, V>? {
        if (rootNode?.left == null)
            return rootNode
        else
            return min(rootNode.left)
    }

    private fun max(rootNode: Node<K, V>?): Node<K, V>? {
        if (rootNode?.right == null)
            return rootNode
        else
            return max(rootNode.right)
    }
}


class Printer<K: Comparable<K>, V> {
    fun printTree(tree: Tree<K, V>) {
        printNode(node = (tree as BinarySearchTree<K, V>).root)
    }

    fun printNode(height: Int = 0, node: Node<K, V>?) {
        if (node == null)
            return

        printNode(height + 1, node.right)

        for (i in 1..height)
            print(" |")

        println("${node.key}"  + node.value)

        printNode(height + 1, node.left)
    }
}