//package designpatterns
//
//class Dialog private constructor(
//    val title: String,
//    val message: String,
//    val titleColor: Color,
//    val bodyColor: Color,
//    val icon: Image,
//    val onClose: () -> Unit) {
//
//    fun show() {
//        //...
//    }
//
//    class DialogBuilder {
//        val title: String
//        val message: String
//        var titleColor: Color = Color.RED
//        var bodyColor: Color = Color.BLACK
//        var icon: Image = Image.info()
//        var onClose: () -> Unit = {}
//
//        constructor(title: String, message: String, init: DialogBuilder.() -> Unit) {
//            this.title = title
//            this.message = message
//            init()
//        }
//
//        fun build(): Dialog = Dialog(title, message, titleColor, bodyColor, icon, onClose)
//    }
//
//    companion object {
//        fun build(title: String, message: String, init: DialogBuilder.() -> Unit) =
//            DialogBuilder(title, message, init).build()
//
//        fun build(title: String, message: String) = DialogBuilder(title, message, {}).build()
//    }
//}
//
//fun main(args: Array<String>) {
//    val errorDialog = Dialog.build("Uzgunuz", "Beklenmedik bir hata olustu") {
//        titleColor = Color.RED
//        bodyColor = Color.YELLOW
//        icon = Image.error()
//    }
//    errorDialog.show()
//
//    val infoDialog = Dialog.build("Tebrikler", "Buyuk ikramiye size cikti.") {
//        titleColor = Color.GREEN
//        icon = Image.info()
//    }
//    infoDialog.show()
//}