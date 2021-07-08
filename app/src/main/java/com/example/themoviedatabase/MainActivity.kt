package com.example.themoviedatabase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.themoviedatabase.models.MovieModel
import com.example.themoviedatabase.models.Popular
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val movieList = ArrayList<MovieModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "The Movie Database"
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        moviesAdapter = MoviesAdapter(movieList, getApplicationContext())
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = moviesAdapter
//        prepareMovieData()
        getPopularMovies()


        moviesAdapter.setOnItemClickListener(object : MoviesAdapter.ClickListener {
            override fun onItemClick(v: View, position: Int) {

                Log.v("TAG", "onItemClick ${position}")
                val intent = Intent(this@MainActivity, DetailsPage::class.java)
                intent.putExtra("movie_id", moviesAdapter.getItem(position))
                intent.putExtra("movie_poster_path", moviesAdapter.getItemPoster_path(position))
                startActivity(intent)

            }
        })
    }


    fun getPopularMovies() {

        val APIKey = "19d59df8bab2849be6b35744d0c1f520"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String =   "https://api.themoviedb.org/3/movie/popular?api_key=$APIKey&language=en-US&page=1"
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var strResp = response.toString()
                val PopularGson = Gson().fromJson(strResp, Popular::class.java)
                for (item in PopularGson.results.indices)
                {
                    var movie = MovieModel(PopularGson.results[item].title, PopularGson.results[item].vote_average.toString(), PopularGson.results[item].poster_path, PopularGson.results[item].id)
                    movieList.add(movie)
                }
                moviesAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
//                textView!!.text = "That didn't work!"
            })
        queue.add(stringReq)
    }

}