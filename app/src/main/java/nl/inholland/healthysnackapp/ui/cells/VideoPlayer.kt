package nl.inholland.healthysnackapp.ui.cells

import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    // Update ExoPlayer when videoUrl changes
    LaunchedEffect(videoUrl) {
        if (videoUrl.isNotEmpty()) {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    // Ensure ExoPlayer is released when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Render the PlayerView
    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                this.player = exoPlayer
            }
        }
    )
}