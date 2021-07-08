package com.example.themoviedatabase.models

class MovieModel(title: String?, vote_average: String?, poster_path: String?, id: Int?) {
    private var title: String
    private var vote_average: String
    private var poster_path: String
    private var id: Int = 0

    init {
        this.title = title!!
        this.vote_average = vote_average!!
        this.poster_path = poster_path!!
        this.id = id!!
    }
    fun getTitle(): String? {
        return title
    }
    fun setTitle(name: String?) {
        title = name!!
    }
    fun getvote_average(): String? {
        return vote_average
    }
    fun setvote_average(vote_average: String?) {
        this.vote_average = vote_average!!
    }
    fun getposter_path(): String? {
        return poster_path
    }
    fun setposter_path(poster_path: String?) {
        this.poster_path = poster_path!!
    }
    fun getId(): Int? {
        return id
    }
    fun setId(id: Int?) {
        this.id = id!!
    }
}