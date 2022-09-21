package com.talisol.learnsavecomposable

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.applyCanvas
import com.talisol.learnsavecomposable.ui.theme.LearnSaveComposableTheme
import java.io.File
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnSaveComposableTheme {

                Greeting("piu")

            }
        }
    }
}

@Composable
fun Greeting(name: String) {


    Column(
        modifier = Modifier
            .fillMaxSize(.25f)
//            .onGloballyPositioned {
//                var bounds = it.boundsInRoot()
//
//                Log.i("DEBUG0", bounds.width.toString())
//                Log.i("DEBUG0", bounds.height.toString())
//            }
    ) {
        val view = LocalView.current
        val context = LocalContext.current

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(Runnable {

                Log.i("DEBUG1", view.width.toString())
                Log.i("DEBUG1", view.height.toString())

                val bmp = Bitmap.createBitmap(view.width, view.height,
                    Bitmap.Config.ARGB_8888).applyCanvas {
                    view.draw(this)
                }
                bmp.let {
                    File(context.filesDir, "screenshot.png")
                        .writeBitmap(bmp, Bitmap.CompressFormat.PNG, 85)
                }
            }, 1000)
            Text(text = "Hello $name!")
        }


}

fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}