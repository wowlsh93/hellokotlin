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

/** Thrown in the event of a Scanner (or parser) failure
 *
 * @include /etc/license.txt
 */
class ParseFailure(
    message: String?,
    private val inputLine: String,
    private val inputPosition: Int,
    private val inputLineNumber: Int
) : Exception(message) {
    /** Returns a String that shows the current input line and a
     * pointer indicating the current input position.
     * In the following sample, the input is positioned at the
     * &#64; sign on input line 17:
     * <PRE>
     * Line 17:
     * a = b + &#64; c;
     * ________^
    </PRE> *
     *
     * Note that the official "message"  [returned from
     * [Throwable.getMessage]] is not included in the
     * error report.
     */
    val errorReport: String
        get() {
            val b = StringBuffer()
            b.append("Line ")
            b.append("$inputLineNumber:\n")
            b.append(inputLine)
            b.append("\n")
            for (i in 0 until inputPosition) b.append("_")
            b.append("^\n")
            return b.toString()
        }
}