package com.example.themoviedatabase

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.themoviedatabase.databinding.ActivityDetailsPageBinding
import com.example.themoviedatabase.models.Details
import com.example.themoviedatabase.models.DetailsModel
import com.example.themoviedatabase.models.GenreM
import com.example.themoviedatabase.models.MovieGenres
import com.google.gson.Gson


class DetailsPage : AppCompatActivity() {

    var movie_id: Int = 0
    lateinit var movie_poster_path_string: String
    lateinit var imageViewPicture: ImageView
    lateinit var mainBinding : ActivityDetailsPageBinding
    private var ListMovieGenres = ArrayList<GenreM>()
    var userModel = DetailsModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_page)
        userModel.DeatilsMovieTitle = ""
        userModel.DeatilsMovieOverview = ""
        userModel.DeatilsMovieGenres = ""
        mainBinding.detailsModel = userModel

        val movie_id_string:String = intent.getStringExtra("movie_id").toString()
        movie_poster_path_string = intent.getStringExtra("movie_poster_path").toString()
        movie_id = movie_id_string.toInt()
        imageViewPicture = findViewById<ImageView>(R.id.imageViewPicture)

        getMoviePicture()
        getMovieGenres()
    }

    fun getMovieDetiles() {

        val APIKey = "19d59df8bab2849be6b35744d0c1f520"

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String =   "https://api.themoviedb.org/3/movie/$movie_id?api_key=$APIKey&language=en-US"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                var strResp = response.toString()
                val DetailsGson = Gson().fromJson(strResp, Details::class.java)
                Log.i("JSON", DetailsGson.title)

                userModel.DeatilsMovieTitle = DetailsGson.title
                userModel.DeatilsMovieOverview = DetailsGson.overview
                var StrGenres: String = ""
                for (item in DetailsGson.genres.indices)
                {
                    for (ListMovieGenresItem in ListMovieGenres.indices)
                    {
                        if(DetailsGson.genres[item].id == ListMovieGenres.get(ListMovieGenresItem).id)
                        {
                            StrGenres = StrGenres  + DetailsGson.genres[item].name + "\n"
                        }
                    }
                }
                userModel.DeatilsMovieGenres = StrGenres
                mainBinding?.invalidateAll()
            },
            Response.ErrorListener {
//                textView!!.text = "That didn't work!"
            })
        queue.add(stringReq)
    }

    fun getMovieGenres() {

        val APIKey = "19d59df8bab2849be6b35744d0c1f520"
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String =   "https://api.themoviedb.org/3/genre/movie/list?api_key=$APIKey&language=en-US"
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var strResp = response.toString()
                val MovieGenres = Gson().fromJson(strResp, MovieGenres::class.java)
                for (item in MovieGenres.genres.indices)
                {
                    ListMovieGenres.add(MovieGenres.genres[item])
                }
                getMovieDetiles()
            },
            Response.ErrorListener {
//                textView!!.text = "That didn't work!"
            })
        queue.add(stringReq)
    }

    fun getMoviePicture() {
        val queue = Volley.newRequestQueue(this)
        val imageUrl = "https://image.tmdb.org/t/p/w300" + movie_poster_path_string
        // request a image response from the provided url
        val imageRequest = ImageRequest(
            imageUrl,
            {bitmap -> // response listener
                imageViewPicture.setImageBitmap(bitmap)
            },
            0, // max width
            0, // max height
            ImageView.ScaleType.CENTER_CROP, // image scale type
            Bitmap.Config.ARGB_8888, // decode config
            {error-> // error listener
            }
        )
        queue.add(imageRequest)

    }

}