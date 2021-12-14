package  com.example.raphamovies.ui.details

import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.raphamovies.appConstants
import com.example.raphamovies.viewmodel.DetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*
import java.lang.Exception
import com.example.raphamovies.MainActivity
import com.example.raphamovies.R
import com.example.raphamovies.databinding.FragmentDetailsBinding
import com.example.raphamovies.domainmappers.toCastDTO
import com.example.raphamovies.domainmodel.Details
import com.example.raphamovies.dto.CastsDTO
import com.example.raphamovies.openYoutube
import com.example.raphamovies.ui.OnMovieClick
import com.movies.raphamovies.ui.details.CastsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel



class DetailsFragment : Fragment(), OnMovieClick, OnCastClick {
    //    lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private var details: Details? = null
    private val args: DetailsFragmentArgs by navArgs()
    private var castsAdapter: CastsAdapter? = null

    private val  viewModel : DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_details, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        attachObservers()
        setupClickListeners()
        setupAdapters()

        viewModel.getDetails(args.id)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun attachObservers() {
        attachMovieObserver()
    }

    private fun attachMovieObserver() {
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            details = it
            displayDetails(it)
            setCastsList(it.casts)
        })
    }


    private fun setupClickListeners() {
        backdropClickListener()
    }

    private fun backdropClickListener() {
        binding.detailBackdrop.setOnClickListener {
            details?.openYoutube(context)
        }
    }

    private fun setupAdapters() {
        setupCastsAdapter()
    }

    private fun setupCastsAdapter() {
        binding.rvCasts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        castsAdapter = CastsAdapter(context, this, ArrayList())
        binding.rvCasts.adapter = castsAdapter
    }

    private fun setCastsList(casts: CastsDTO?) {
        casts?.let { cast ->
            cast.cast?.let { actors ->
                castsAdapter?.setList(actors)
            }
            cast.crew?.let { crew ->
                castsAdapter?.addToList(crew.toCastDTO())
            }
        }
    }

    private fun displayDetails(details: Details?) {
        details?.apply {
            val imagePath = backdrop_path ?: poster_path

            Picasso.get()
                .load("${appConstants.TMDB_IMAGE_BASE_URL_W500}$imagePath")
                .placeholder(R.drawable.placeholder)
                .into(detail_backdrop, object : Callback {
                    override fun onSuccess() {


                    }
                    override fun onError(e: Exception?) {
                        Toast.makeText(context, "Error loading image", Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }

    }

    override fun onClick(id: Int) {
        if (id != 0) {
            Log.d("click", "HomeFragment, onItemClick, id = $id")
            val detailsToDetailsFragment = DetailsFragmentDirections.actionDetailsFragmentSelf(id)
            findNavController().navigate(detailsToDetailsFragment)
        } else {
            Toast.makeText(context, "Sorry. Can not load this movie. :/", Toast.LENGTH_SHORT).show()
        }

    }



    override fun onCastClick(id: Int) {
        TODO("Not yet implemented")
    }


}

