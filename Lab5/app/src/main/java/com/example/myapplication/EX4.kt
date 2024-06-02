package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EX4 : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: Button
    private lateinit var stopButton: Button
    private var isPrepared: Boolean = false
    private var playThread: Thread? = null
    private var stopThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex4)

        mediaPlayer = MediaPlayer.create(this, R.raw.wokeup)
        mediaPlayer.setOnPreparedListener { onMediaPrepared() }
        mediaPlayer.setOnCompletionListener { onMediaCompletion() }

        playButton = findViewById(R.id.playButton)
        stopButton = findViewById(R.id.stopButton)

        playButton.setOnClickListener {
            playThread = Thread(Runnable {
                try {
                    if (!mediaPlayer.isPlaying && !isPrepared) {
                        mediaPlayer.prepare()
                    }
                    mediaPlayer.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
            playThread?.start()
        }

        stopButton.setOnClickListener {
            stopThread = Thread(Runnable {
                try {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                        mediaPlayer.reset() // Reset the MediaPlayer after stopping
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
            stopThread?.start()
        }
    }

    private fun onMediaPrepared() {
        isPrepared = true
    }

    private fun onMediaCompletion() {
        isPrepared = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
