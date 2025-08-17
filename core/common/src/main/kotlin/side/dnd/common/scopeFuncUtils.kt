package side.dnd.common

fun <T> T.runIf(predicate: Boolean, block: T.() -> T): T = if (predicate) block() else this

fun <T> T.letIf(predicate: Boolean, block: (T) -> T): T = if (predicate) block(this) else this