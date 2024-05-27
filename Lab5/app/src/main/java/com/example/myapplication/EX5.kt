package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class EX5 : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playButton: Button
    private lateinit var stopButton: Button
    private var isPrepared: Boolean = false
    private var mediaPlayerDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex4)

        mediaPlayer = MediaPlayer.create(this, R.raw.wokeup)
        mediaPlayer.setOnPreparedListener { onMediaPrepared() }
        mediaPlayer.setOnCompletionListener { onMediaCompletion() }

        playButton = findViewById(R.id.playButton)
        stopButton = findViewById(R.id.stopButton)

        playButton.setOnClickListener {
            mediaPlayerDisposable?.dispose()
            mediaPlayerDisposable = playMusic()
        }

        stopButton.setOnClickListener {
            mediaPlayerDisposable?.dispose()
            stopMusic()
        }
    }

    private fun onMediaPrepared() {
        isPrepared = true
    }

    private fun onMediaCompletion() {
        isPrepared = false
    }

    private fun playMusic(): Disposable {
        return Observable.create<Unit> { emitter ->
            try {
                if (!mediaPlayer.isPlaying && !isPrepared) {
                    mediaPlayer.prepare()
                }
                mediaPlayer.start()
                emitter.onNext(Unit)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { /* onNext */ },
                { error -> error.printStackTrace() }
            )
    }

    private fun stopMusic() {
        Observable.create<Unit> { emitter ->
            try {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.reset() // Reset the MediaPlayer after stopping
                }
                emitter.onNext(Unit)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { /* onNext */ },
                { error -> error.printStackTrace() }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerDisposable?.dispose()
        mediaPlayer.release()
    }
}
