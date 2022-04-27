package com.example.submission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.adapter.StoriesAdapter
import com.example.submission2.databinding.ActivityHomeBinding
import com.example.submission2.model.LoginSession
import androidx.activity.viewModels
import com.example.submission2.adapter.LoadingAdapter
import com.example.submission2.viewmodel.HomeViewModel
import com.example.submission2.viewmodel.ViewModelFactory


class HomeActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_RESULT="extra_person"
    }
//    private val mHomeViewModel: HomeViewModel by viewModels {
//        ViewModelFactory(this)
//    }

    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val mHomeViewModel : HomeViewModel by viewModels{
        factory
    }

//    private lateinit var mStoriesAdapter: StoriesAdapter
    private lateinit var mSession: SessionPreference
    private var token: String? = ""

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "list story"
        binding.listStories.layoutManager = LinearLayoutManager(this)

        val loginSession = intent.getParcelableExtra<LoginSession>(EXTRA_RESULT) as LoginSession
        token = loginSession.token

        getStories(loginSession)


        binding.addStory.setOnClickListener{
            val move = Intent(this@HomeActivity, AddStoryActivity::class.java)
            move.putExtra(AddStoryActivity.LOGIN_SESSION, loginSession)
            startActivity(move)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        itemResponse(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun itemResponse(selectedItem: Int) {
        when (selectedItem) {
            R.id.logout ->{
                logOut()
            }
            R.id.maps_mode->{
                    moveToMaps()
            }
        }
    }

    private fun moveToMaps() {
        val intent = Intent(this@HomeActivity, MapsActivity::class.java)
        mSession = SessionPreference(this)
        intent.putExtra(MapsActivity.TOKEN, mSession.getSession())
        startActivity(intent)
    }

    private fun logOut() {
        mSession = SessionPreference(this)
        mSession.deleteSession()
        Log.d(".HomeActivity", "lihat : ${mSession.getSession()}")
        val moveToLogin = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(moveToLogin)
    }

    private fun getStories(loginSession: LoginSession) {
        showLoading(true)
        val storiesAdapter = StoriesAdapter()
        binding.listStories.adapter = storiesAdapter.withLoadStateFooter(
            footer = LoadingAdapter{
                storiesAdapter.retry()
            }
        )
        mHomeViewModel.getStories(loginSession).observe(this, {
            Log.d(".HomeActivity", "data story : $it")
            storiesAdapter.submitData(lifecycle, it)
        })
        showLoading(false)
    }

    private fun getToken(): String?{
        return token
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }
}