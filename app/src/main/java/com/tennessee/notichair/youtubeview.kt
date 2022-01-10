package com.tennessee.notichair

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class youtubeview : YouTubeBaseActivity(){

    val VIDEO_ID ="qZefJazKg2o"
    val YOUTUBE_API_KEY = "AIzaSyADAopCoqyFPfk3CLUqR6CZ9R2FoTc0NQ4"

    private  lateinit var  youtubePlayer: YouTubePlayerView
    private  lateinit var btnPlay : Button

    lateinit var youtubePlayerInit : YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        youtubePlayer = findViewById(R.id.youtube_view)
        btnPlay = findViewById(R.id.btnPlay)

        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(VIDEO_ID)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,"Failed",Toast.LENGTH_SHORT).show()
            }
        }

        btnPlay.setOnClickListener {
            youtubePlayer.initialize(YOUTUBE_API_KEY, youtubePlayerInit)
        }

    }

}