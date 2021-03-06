package com.example.albumsearch.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsearch.R
import com.example.albumsearch.databinding.FragmentAlbumDetailBinding
import com.example.albumsearch.model.database.entities.AlbumEntity
import com.example.albumsearch.view.adapters.TrackAdapter
import com.example.albumsearch.viewmodel.AlbumDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_ALBUM = "album"

/** [Fragment] displaying the results of an album */
@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {

    @Inject
    lateinit var albumDetailViewModelFactory: AlbumDetailViewModel.AssistedFactory

    private val viewModel: AlbumDetailViewModel by viewModels {
        AlbumDetailViewModel.provideFactory(
            albumDetailViewModelFactory, arguments?.getParcelable(ARG_ALBUM)!!
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        validateArguments()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.reload_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlbumDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupTrackRecycler(binding.trackList)
        setupToaster()

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                viewModel.onRefreshClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /** Sets up toast displaying */
    private fun setupToaster() {
        viewModel.toastMessage.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    /** Initializes [trackRecycler] to display tracks provided by [viewModel] */
    private fun setupTrackRecycler(trackRecycler: RecyclerView) {
        val adapter = TrackAdapter()
        trackRecycler.adapter = adapter
        viewModel.tracks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    /** Checks passed arguments and navigates back with error message if required argument is null */
    private fun validateArguments() {
        if (arguments?.getParcelable<AlbumEntity>(ARG_ALBUM) == null) {
            parentFragmentManager.popBackStack()
            Toast.makeText(
                activity,
                getString(R.string.error_opening_album_details),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment
         *
         * @param album album to show details for
         */
        @JvmStatic
        fun newInstance(album: AlbumEntity) =
            AlbumDetailFragment().apply {
                arguments = Bundle().apply { putParcelable(ARG_ALBUM, album) }
            }
    }
}