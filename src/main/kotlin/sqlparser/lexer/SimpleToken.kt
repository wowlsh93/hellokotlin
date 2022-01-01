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
package com.holub.text

import sqlparser.lexer.Token

/** Matches a simple symbol that doesn't have to be on a "word"
 * boundary; punctuation, for example. SimpleToken
 * is very efficient, but does not recognize characters in
 * a case-insensitive way, as does [WordToken] and
 * [RegexToken].
 *
 * @include /etc/license.txt
 */
class SimpleToken(pattern: String) : Token {
    private val pattern: String
    override fun match(input: String, offset: Int): Boolean {
        return input.toLowerCase().startsWith(pattern, offset)
    }

    override fun lexeme(): String {
        return pattern
    }

    override fun toString(): String {
        return pattern
    }

    /** Create a token.
     * @param pattern a string that defines a literal-match lexeme.
     */
    init {
        this.pattern = pattern.toLowerCase()
    }
}