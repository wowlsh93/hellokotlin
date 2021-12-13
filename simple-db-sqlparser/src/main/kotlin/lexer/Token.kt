package sqlparser.lexer

interface Token {
    fun match(input: String, offset: Int): Boolean
    fun lexeme(): String?
}