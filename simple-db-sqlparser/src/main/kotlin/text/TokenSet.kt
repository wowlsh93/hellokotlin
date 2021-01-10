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

/***
 * A token set is a collection of tokens that define all possible
 * lexical units of some language. TokenSet objects serve as
 * token factories, and all tokens created by a particular
 * TokenSet are in that set. (see [.create]).
 * [Scanner] ojbects use `TokenSet`s to
 * recognize input tokens. Each [Token] is responsible for
 * deciding whether it comes next in the input, and the
 * tokens examine the input in the order that they were created.
 *
 *
 * See the source code for [com.holub.database.Database]
 * (in the distribution jar) for an example of how a token set
 * is used in conjunction with a Scanner.
 *
 * @include /etc/license.txt
 */
class TokenSet {
    private val members: Collection = ArrayList()

    /** Return an iterator across the Token pool. This iterator
     * is guaranteed to return the tokens in the order that
     * [.create] was called. You can use this iterator
     * to list all the tokens in a given set.
     */
    operator fun iterator(): Iterator {
        return members.iterator()
    }

    /**********************************************************************
     * Create a Token based on a specification and add it to the current
     * set.
     *
     *
     * An appropriate token type is chosen by examining the input
     * specification. In particular, a [RegexToken] is
     * created unless the input string contains no regular-expression
     * metacharacters ({i \\[]{}()$^*+?|}) or starts with a single-quote
     * mark ('). In this case, a
     * [WordToken] is created if the specification ends
     * in any character that could occur in a Java identifier;
     * otherwise a [SimpleToken] is created.
     * If a string that starts with a single-quote mark also
     * ends with a single-quote mark, the end-quote mark
     * is discarded. The end-quote mark is optional.
     *
     *
     * Tokens are always extracted
     * from the beginning of a String, so the characters that
     * precede the token are irrelevant.
     *
     * @see WordToken
     *
     * @see RegexToken
     *
     * @see SimpleToken
     */
    fun create(spec: String): Token {
        val token: Token
        var start = 1
        if (!spec.startsWith("'")) {
            if (containsRegexMetacharacters(spec)) {
                token = RegexToken(spec)
                members.add(token)
                return token
            }
            --start // don't compensate for leading quote

            // fall through to the "quoted-spec" case
        }
        var end: Int = spec.length()
        if (start == 1 && spec.endsWith("'")) // saw leading '
            --end
        token = if (Character.isJavaIdentifierPart(spec.charAt(end - 1))) WordToken(
            spec.substring(
                start,
                end
            )
        ) else (SimpleToken(spec.substring(start, end)) as Token?)!!
        members.add(token)
        return token
    }

    companion object {
        /** Return true if the string argument contains any of the
         * following characters: \\[]{}$^*+?|()
         */
        private fun containsRegexMetacharacters(s: String): Boolean {    // This method could be implemented more efficiently,
            // but its not called very often.
            val m: Matcher = metacharacters.matcher(s)
            return m.find()
        }

        private val metacharacters: Pattern = Pattern.compile("[\\\\\\[\\]{}$\\^*+?|()]")
    }
}