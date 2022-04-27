package com.example.submission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.submission2.api.ListStory
import com.example.submission2.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    companion object {
        const val OBJECT = "object"
    }
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        val story = intent.getParcelableExtra<ListStory>(OBJECT) as ListStory
        Log.d("DetailStory","$story")

        with(binding) {
            name.text = story.name
            description.text = story.description
            Glide.with(this@DetailStoryActivity)
                .load(story.photoUrl)
                .into(storyPicture)
        }
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }
}