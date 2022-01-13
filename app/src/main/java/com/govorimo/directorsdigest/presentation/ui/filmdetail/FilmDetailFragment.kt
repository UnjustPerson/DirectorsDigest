package com.govorimo.directorsdigest.presentation.ui.filmdetail

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.govorimo.directorsdigest.R
import com.govorimo.directorsdigest.databinding.FragmentFilmDetailBinding
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.presentation.MainViewModel
import com.govorimo.directorsdigest.util.colorText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmDetailBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val film: Film = arguments?.getParcelable("film")!!

        val requestOptionsFilmPhoto = RequestOptions
            .placeholderOf(R.drawable.film_detail_still)
            .error(R.drawable.film_detail_still)

        Glide.with(binding.filmPhoto)
            .setDefaultRequestOptions(requestOptionsFilmPhoto)
            .load(film.film_photo)
            .into(binding.filmPhoto)

        //binding.filmName.text = "${film.film_name} (${film.year})"
        binding.filmSynopsis.text = film.film_synopsis
        binding.directorName.text = film.director
        binding.screenwriterName.text = film.screenplay
        binding.filmName.text = colorText("${film.film_name} (${film.year})", "#FFC10A")

        val filmTrailer = binding.filmTrailer
        filmTrailer.apply{
            this.webViewClient = WebViewClient()
            this.settings.javaScriptEnabled = true
            //this.loadUrl("https://www.youtube.com/embed/${film.trailer_link}")
            this.loadUrl("https://www.dailymotion.com/embed/video/${film.trailer_link}?autoplay=1")
            //this.loadUrl("https://www.dailymotion.com/embed/video/x7z6ceu?autoplay=1")
            this.webChromeClient = WebChromeClient()
        }
    }

    companion object {

        fun newInstance(film: Film): FilmDetailFragment {
            val args = Bundle().apply {
                putParcelable("film", film)
            }
            return FilmDetailFragment().apply {
                arguments = args
            }
        }

    }
}