package com.govorimo.directorsdigest.presentation.ui.filmography

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.govorimo.directorsdigest.R
import com.govorimo.directorsdigest.databinding.FilmItemBinding
import com.govorimo.directorsdigest.persistence.models.Film


class FilmographyAdapter(val context: Context): RecyclerView.Adapter<FilmographyAdapter.FilmographyViewHolder>() {


    var callbacks : FilmographyFragment.Callbacks? = null

    private val requestOptionsFilmPhoto = RequestOptions
        .placeholderOf(R.drawable.the_room_still)
        .error(R.drawable.the_room_still)
        .transform(RoundedCorners(45))

    private val requestOptionsFilmLogo = RequestOptions
        .placeholderOf(R.drawable.the_room_logo)
        .error(R.drawable.the_room_logo)



    var films: List<Film> = listOf<Film>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyViewHolder {
        val binding = FilmItemBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        return FilmographyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
        val film = films[position]

        holder.bind(film)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    inner class FilmographyViewHolder(val binding: FilmItemBinding, ): RecyclerView.ViewHolder(binding.root) {

        private lateinit var film: Film

        fun bind(film: Film){
            this.film = film
            Glide.with(binding.root)
                .setDefaultRequestOptions(requestOptionsFilmPhoto)
                .load(film.film_photo)
                .into(binding.filmImage)
            if (film.film_logo != null) {
                Glide.with(binding.root)
                    .setDefaultRequestOptions(requestOptionsFilmLogo)
                    .load(film.film_logo)
                    .into(binding.filmLogo)
            }
            else{
                binding.logoBackupText.text = film.film_name
            }
            binding.filmImage.setOnClickListener {
                callbacks = context as FilmographyFragment.Callbacks?
                callbacks?.onFilmSelected(film)
            }

        }




    }




}