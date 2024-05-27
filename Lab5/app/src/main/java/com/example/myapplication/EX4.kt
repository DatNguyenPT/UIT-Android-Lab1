package com.example.myapplication

import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EX4 : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: Button
    private lateinit var stopButton: Button
    private var isPrepared: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex4)

        mediaPlayer = MediaPlayer.create(this, R.raw.wokeup)
        mediaPlayer.setOnPreparedListener { onMediaPrepared() }
        mediaPlayer.setOnCompletionListener { onMediaCompletion() }

        playButton = findViewById(R.id.playButton)
        stopButton = findViewById(R.id.stopButton)

        playButton.setOnClickListener {
            PlayMusicTask().execute()
        }

        stopButton.setOnClickListener {
            StopMusicTask().execute()
        }
    }

    private fun onMediaPrepared() {
        isPrepared = true
    }

    private fun onMediaCompletion() {
        isPrepared = false
    }

    inner class PlayMusicTask : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            try {
                if (!mediaPlayer.isPlaying && !isPrepared) {
                    mediaPlayer.prepare()
                }
                mediaPlayer.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    inner class StopMusicTask : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            try {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.reset() // Reset the MediaPlayer after stopping
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
