package com.dpreble.flytimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dpreble.flytimer.ui.theme.FlyTimerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlyTimerTheme {
                FlyTimerRoot()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlyTimerAppPreview() {
    FlyTimerTheme {
        FlyTimerRoot()
    }
}
