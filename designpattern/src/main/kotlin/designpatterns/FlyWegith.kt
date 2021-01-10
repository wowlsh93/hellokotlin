//package designpatterns
//
//interface Glyph {
//    var position: Int
//    fun show(): String
//}
//data class GlyphCode(val code: String)
//
//class GlyphFactory(private val glyphCodes: MutableMap<String, GlyphCode> = mutableMapOf()) {
//    fun retrieveGlyph(code: String): Glyph {
//        // create Glyph Code if it does not exists
//        if (code !in glyphCodes) glyphCodes[code] = GlyphCode(code)
//        // create and return a new Glyph with the shared cached Glyph Code which corresponds to the requested code
//        return GlyphFlyweight(glyphCodes[code] ?: GlyphCode(code))
//    }
//}
//
//class GlyphFlyweight(private val code: GlyphCode, override var position: Int = 0) : Glyph {
//    override fun show(): String = code.code
//}