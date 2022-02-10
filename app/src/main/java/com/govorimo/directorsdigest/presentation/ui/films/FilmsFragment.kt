package com.govorimo.directorsdigest.presentation.ui.films

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.govorimo.directorsdigest.R
import com.govorimo.directorsdigest.databinding.FragmentFilmsBinding
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.presentation.MainViewModel
import com.govorimo.directorsdigest.presentation.ui.directors.DirectorAdapter
import com.govorimo.directorsdigest.presentation.ui.filmography.FilmographyAdapter
import com.govorimo.directorsdigest.util.getRoomList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmsFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private var filmsAdapter: FilmographyAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFilmsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFilms()

        subscribeObservers()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.filmRecycler.apply {
            layoutManager = LinearLayoutManager(this@FilmsFragment.context!!)
            filmsAdapter = FilmographyAdapter(requireActivity())
            adapter = filmsAdapter
        }
    }

    private fun subscribeObservers(){
        viewModel.isLoading.observe(viewLifecycleOwner, Observer{
            when (it) {
                true -> {
                    binding.loadingAnimation.visibility = View.VISIBLE
                }
                false -> {
                    binding.loadingAnimation.visibility = View.INVISIBLE
                }
            }
        })
        viewModel.films.observe(viewLifecycleOwner, Observer{
            if(it != null){
                filmsAdapter?.films = it
                binding.filmRecycler.adapter = filmsAdapter
            }
            else{
                filmsAdapter?.films = getRoomList(resources)
                binding.filmRecycler.adapter = filmsAdapter
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filmsAdapter = null
    }

}