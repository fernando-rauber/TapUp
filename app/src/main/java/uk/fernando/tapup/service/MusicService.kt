package uk.fernando.tapup.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import uk.fernando.tapup.R
import uk.fernando.uikit.ext.playAudio

class MusicService : Service() {
    private var soundPlayer: MediaPlayer? = null
    private var currentMusic: Int = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        soundPlayer?.stop()
        soundPlayer?.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val nextMusic = when (intent!!.action) {
            PLAY_HOME_MUSIC -> R.raw.background_home
            PLAY_GAME_MUSIC -> R.raw.background_game
            else -> R.raw.background_game
        }

        if (nextMusic != currentMusic && soundPlayer?.isPlaying == true) {
            soundPlayer?.stop()
            soundPlayer?.release()
            soundPlayer = null
        }

        currentMusic = nextMusic

        if (soundPlayer == null) {
            soundPlayer = MediaPlayer.create(this, currentMusic).apply {
                isLooping = true
                setVolume(0.2f, .2f)
            }
        }

        soundPlayer?.playAudio()

        return START_STICKY
    }

    companion object {
        const val PLAY_HOME_MUSIC = "uk.fernando.tapup.service.MusicService.HOME_MUSIC"
        const val PLAY_GAME_MUSIC = "uk.fernando.tapup.service.MusicService.GAME_MUSIC"
    }
}