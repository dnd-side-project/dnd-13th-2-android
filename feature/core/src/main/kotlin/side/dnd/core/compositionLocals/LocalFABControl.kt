package side.dnd.core.compositionLocals

import androidx.compose.runtime.compositionLocalOf

val LocalFABControl = compositionLocalOf<(Boolean) -> Unit> { error("No FABController provided") }

val LocalFabOnClickedListener = compositionLocalOf<(() -> Unit) -> Unit> { error("No FABOnClickListener provided") }