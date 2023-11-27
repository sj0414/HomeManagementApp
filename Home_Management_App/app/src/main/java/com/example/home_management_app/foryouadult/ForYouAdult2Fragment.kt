package com.example.home_management_app.foryouadult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_management_app.databinding.FragmentForYouAdult2Binding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.jsoup.Jsoup
import java.lang.Exception


class ForYouAdult2Fragment : Fragment() {
    lateinit var binding: FragmentForYouAdult2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouAdult2Binding.inflate(layoutInflater)

        playYoutube()
        textYoutube()


        return binding.root
    }

    private fun playYoutube() {
        val youTubePlayerView: YouTubePlayerView = binding.flRecipeYoutube
        //getLifecycle().addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "S0Q4gqBUs7c"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun textYoutube() {
        try {
            val doc = Jsoup.connect("https://https://www.youtube.com/watch?v=_oMtk3FaBlo").get()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}


