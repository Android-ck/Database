package com.zerir.sqldelight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zerir.sqldelight.ui.theme.SqldelightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SqldelightTheme(darkTheme = false) {
                PersonsListScreen()
            }
        }
    }
}