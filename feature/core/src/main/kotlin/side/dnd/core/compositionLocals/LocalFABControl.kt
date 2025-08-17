package side.dnd.core.compositionLocals

import androidx.compose.runtime.compositionLocalOf

val LocalFABControl = compositionLocalOf<(Boolean) -> Unit> { error("No FABController provided") }