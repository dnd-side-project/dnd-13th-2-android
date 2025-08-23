package network.constants

object NetworkConstants {
    const val BASE_URL = "http://3.34.117.255:8080/api/v1/"
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    object ContentType {
        const val APPLICATION_JSON = "application/json"
        const val APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded"
    }
}
