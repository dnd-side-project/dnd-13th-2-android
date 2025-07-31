package side.dnd.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import side.dnd.app.ui.theme.SideprojectTheme

class MainActivity : ComponentActivity() {
    /**
     * Initializes the activity and sets up the main UI content using Jetpack Compose.
     *
     * Enables edge-to-edge display and defines the app's theme and layout, displaying a greeting message.
     *
     * @param savedInstanceState The previously saved instance state, or null if none exists.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SideprojectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Displays a greeting message with the specified name.
 *
 * @param name The name to include in the greeting message.
 * @param modifier Optional modifier to adjust the layout or appearance of the greeting.
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * Displays a preview of the Greeting composable within the SideprojectTheme for IDE inspection.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SideprojectTheme {
        Greeting("Android")
    }
}