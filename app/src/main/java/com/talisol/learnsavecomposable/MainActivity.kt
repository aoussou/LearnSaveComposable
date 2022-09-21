package com.talisol.learnsavecomposable

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.applyCanvas
import com.talisol.learnsavecomposable.ui.theme.LearnSaveComposableTheme
import java.io.File
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnSaveComposableTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {


    val view = LocalView.current
    var capturingViewBounds by remember { mutableStateOf<Rect?>(null) }
    Button(onClick = {
        val bounds = capturingViewBounds ?: return@Button
        val image = Bitmap.createBitmap(
            bounds.width.roundToInt(), bounds.height.roundToInt(),
            Bitmap.Config.ARGB_8888
        ).applyCanvas {
            translate(-bounds.left, -bounds.top)
            view.draw(this)
        }
    }) {
        Text("Capture")
    }
    Column(
        modifier = Modifier
            .onGloballyPositioned {
                capturingViewBounds = it.boundsInRoot()
            }
    ) {

        Text("Geki")

        Text("Kawa")

    }


    Column() {
        val view = LocalView.current
        val context = LocalContext.current

        val handler = Handler(Looper.getMainLooper())

        Log.i("DEBUG",context.filesDir.toString())

        handler.postDelayed(Runnable {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LearnSaveComposableTheme {
        Greeting("Android")
    }
}

private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}