package side.dnd.core.compositionLocals

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import side.dnd.core.SnackBarMessage

val LocalShowSnackBar: ProvidableCompositionLocal<(SnackBarMessage) -> Unit> =
    staticCompositionLocalOf {
        { snackBarMessage: SnackBarMessage ->
            error("showSnackBar is not initialized")
        }
    }