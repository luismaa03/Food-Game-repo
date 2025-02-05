package com.example.foodgame

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class AudioActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekBar: SeekBar
    private lateinit var currentTimeTextView: TextView
    private lateinit var totalTimeTextView: TextView
    private lateinit var ivMute: ImageView
    private lateinit var ivPlayPause: ImageView
    private var isMuted = false
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)

        seekBar = findViewById(R.id.seekBar)
        currentTimeTextView = findViewById(R.id.currentTimeTextView)
        totalTimeTextView = findViewById(R.id.totalTimeTextView)
        ivMute = findViewById(R.id.ivMute)
        ivPlayPause = findViewById(R.id.ivPlayPause)

        mediaPlayer = MediaPlayer.create(this, R.raw.musica) // Reemplaza 'musica' con el nombre de tu archivo de audio

        seekBar.max = mediaPlayer.duration
        totalTimeTextView.text = formatTime(mediaPlayer.duration)

        ivPlayPause.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                ivPlayPause.setImageResource(R.drawable.ic_play)
            } else {
                mediaPlayer.start()
                ivPlayPause.setImageResource(R.drawable.ic_pause)
                updateSeekBar()
            }
            isPlaying = !isPlaying
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
                currentTimeTextView.text = formatTime(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.seekTo(0)
            seekBar.progress = 0
            currentTimeTextView.text = formatTime(0)
            ivPlayPause.setImageResource(R.drawable.ic_play)
            isPlaying = false
        }
        ivMute.setOnClickListener {
            isMuted = !isMuted
            mediaPlayer.setVolume(if (isMuted) 0f else 1f, if (isMuted) 0f else 1f)
            ivMute.setImageResource(if (isMuted) R.drawable.ic_volume_off else R.drawable.ic_volume_up)
        }
    }

    private fun updateSeekBar() {
        seekBar.progress = mediaPlayer.currentPosition
        currentTimeTextView.text = formatTime(mediaPlayer.currentPosition)

        if (mediaPlayer.isPlaying) {
            handler.postDelayed({ updateSeekBar() }, 1000)
        }
    }

    private fun formatTime(millis: Int): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}