package com.yxhelzvl.umsnh_id

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yxhelzvl.umsnh_id.core.navigation.NavigationWrapper
import com.yxhelzvl.umsnh_id.ui.theme.UMSNHIDTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            UMSNHIDTheme {
                KoinAndroidContext {
                    NavigationWrapper()
                }
            }
        }
    }
}