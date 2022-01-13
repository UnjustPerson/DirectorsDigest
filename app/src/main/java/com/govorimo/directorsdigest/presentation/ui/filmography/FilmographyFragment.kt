package com.govorimo.directorsdigest.presentation.ui.filmography

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
import com.govorimo.directorsdigest.databinding.FragmentFilmographyBinding
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.presentation.MainViewModel
import com.govorimo.directorsdigest.presentation.ui.directors.DirectorsFragment
import com.govorimo.directorsdigest.util.getRoomList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmographyFragment : Fragment() {


    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!

    private var filmsAdapter: FilmographyAdapter? = null

    interface Callbacks{
        fun onFilmSelected(film: Film)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFilmographyBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.makeFilmsCall()

        val directorName = arguments?.getString("directorName")

        binding.directorNameText.text = directorName

        initRecyclerView()
        subscribeObservers()

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, DirectorsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

    }

    private fun initRecyclerView(){
        binding.filmRecycler.apply {
            layoutManager = LinearLayoutManager(this@FilmographyFragment.context!!)
            filmsAdapter = FilmographyAdapter(requireActivity())
            adapter = filmsAdapter
        }
    }

    private fun subscribeObservers(){
        val directorName = arguments?.getString("directorName")
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
                filmsAdapter?.films = it.filter { it.director == directorName }
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

    companion object {
        fun newInstance(directorName: String): FilmographyFragment {
            val args = Bundle().apply {
                putString("directorName", directorName)
            }
            return FilmographyFragment().apply {
                arguments = args
            }
        }
    }

}