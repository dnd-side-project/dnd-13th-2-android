package side.dnd.data.network.constants

object NetworkConstants {
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    object ContentType {
        const val APPLICATION_JSON = "application/json"
        const val APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded"
    }
}
