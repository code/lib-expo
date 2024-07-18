package expo.modules.video.playbackService

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import java.lang.ref.WeakReference

class PlaybackServiceConnection(val player: WeakReference<ExoPlayer>) : ServiceConnection {
  var playbackServiceBinder: PlaybackServiceBinder? = null

  override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
    val player: ExoPlayer = player.get() ?: return
    playbackServiceBinder = binder as? PlaybackServiceBinder
    playbackServiceBinder?.service?.registerPlayer(player) ?: run {
      Log.w(
        "ExpoVideo",
        "Expo Video could not bind to the playback service. " +
          "This will cause issues with playback notifications and sustaining background playback."
      )
    }
  }

  override fun onServiceDisconnected(componentName: ComponentName) {
    playbackServiceBinder = null
  }

  override fun onNullBinding(componentName: ComponentName) {
    Log.w(
      "ExpoVideo",
      "Expo Video could not bind to the playback service. " +
        "This will cause issues with playback notifications and sustaining background playback."
    )
  }
}
