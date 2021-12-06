package com.example.raphamovies.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.raphamovies.R
import com.example.raphamovies.viewmodel.HomeViewModel
import com.example.raphamovies.databinding.HomeFragmentBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.raphamovies.MainActivity
import javax.inject.Inject


class HomeFragment : Fragment(), OnMovieClick {

    @Inject
    lateinit var  viewModelFactory: ViewModelProvider.Factory
    private val  viewModel by viewModels<HomeViewModel> { viewModelFactory }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        attachObservers()

        return binding.root
    }

    private fun attachObservers() {
        listsOfMoviesObserver()
        isLoadingObserver()
    }

   fun listsOfMoviesObserver() {
        viewModel.listsOfMovies?.observe(viewLifecycleOwner, Observer { lists->
            lists?.let{
                binding.rvListsOfMovies.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rvListsOfMovies.adapter = ListsOfMoviesAdapter(this,requireContext(),lists)
            }
        })
    }

    private fun isLoadingObserver() {
        viewModel.isLoading?.observe(viewLifecycleOwner, Observer { isLoading ->
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



     override fun onClick(id: Int) {
        goToDetailsFragment(id)
    }


    private fun goToDetailsFragment(id: Int) {
        val homeToDetailsFragment =
            HomeFragmentDirections.actionNavigationHomeToDetailsFragment(id)
        findNavController().navigate(homeToDetailsFragment)
    }

}