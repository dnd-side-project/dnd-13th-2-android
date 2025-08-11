package side.dnd.core

class SnackBarMessage(
    val headerMessage: String,
    val contentMessage: String = ""
) {
    companion object {
        val EMPTY = SnackBarMessage(
            headerMessage = "",
            contentMessage = ""
        )
    }
}
