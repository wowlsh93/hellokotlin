/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.tools

/*** A simple implementation of java.util.Iterator that enumerates
 * over arrays. You can use this class to pass arrays to
 * methods that normally accept [Iterator] arguments.
 * (It's an example of the Adapter design pattern in that it
 * makes an array appear to implement the `Iterator`
 * interface.)
 *
 * @include /etc/license.txt
 */
class ArrayIterator(items: Array<Object>) : Iterator {
    private var position = 0
    private val items: Array<Object>
    operator fun hasNext(): Boolean {
        return position < items.size
    }

    operator fun next(): Object {
        if (position >= items.size) throw NoSuchElementException()
        return items[position++]
    }

    fun remove() {
        throw UnsupportedOperationException(
            "ArrayIterator.remove()"
        )
    }

    /** Not part of the Iterator interface, returns the data
     * set in array form. A clone of the wrapped array
     * is actually returned, so modifying the returned array
     * will not affect the iteration at all.
     */
    fun toArray(): Array<Object> {
        return items.clone() as Array<Object>
    }

    /**
     * Create and `ArrayIterator`.
     * @param items the array whose elements will be returned,
     * in turn, by each [.next] call.
     */
    init {
        this.items = items
    }
}