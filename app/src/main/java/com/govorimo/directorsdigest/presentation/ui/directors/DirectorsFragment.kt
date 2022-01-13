package com.govorimo.directorsdigest.presentation.ui.directors

import android.content.Context
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
import com.govorimo.directorsdigest.databinding.FragmentDirectorsBinding
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.presentation.MainViewModel
import com.govorimo.directorsdigest.presentation.ui.filmography.FilmographyAdapter
import com.govorimo.directorsdigest.util.Resource
import com.govorimo.directorsdigest.util.getRoomList
import com.govorimo.directorsdigest.util.getTommyList
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class DirectorsFragment : Fragment() {

    interface Callbacks{
        fun onDirectorSelected(director: Director)
    }

    private var callbacks: Callbacks? = null

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentDirectorsBinding? = null
    private val binding get() = _binding!!

    private var directorsAdapter: DirectorAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDirectorsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.makeDirectorsCall()

        initRecyclerView()
        subscribeObservers()

    }

    private fun initRecyclerView(){
        binding.directorsRecycler.apply {
            directorsAdapter = DirectorAdapter(requireActivity())
            setItemTransformer(ScaleTransformer.Builder().setMinScale(0.75f).build())
            adapter = directorsAdapter
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
        viewModel.directors.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                directorsAdapter?.directors = it
                binding.directorsRecycler.adapter = directorsAdapter
            }
            else{
                directorsAdapter?.directors = getTommyList(resources)
                Toast.makeText(requireContext(), resources.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                binding.directorsRecycler.adapter = directorsAdapter
            }
        })
    }

    override fun onDetach() {
        super.onDetach()
        directorsAdapter = null
        callbacks = null
    }

}