package com.example.albumsearch.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsearch.R
import com.example.albumsearch.databinding.FragmentAlbumDetailBinding
import com.example.albumsearch.model.network.dto.Album
import com.example.albumsearch.view.adapters.LookupEntityAdapter
import com.example.albumsearch.viewmodel.AlbumDetailViewModel
import com.example.albumsearch.viewmodel.AlbumDetailViewModelFactory

private const val ARG_ALBUM = "album"

/**
 * [Fragment] displaying the results of an album
 *
 */
class AlbumDetailFragment : Fragment() {

    private val viewModel: AlbumDetailViewModel by lazy {
        ViewModelProvider(
            this, AlbumDetailViewModelFactory(
                arguments?.getParcelable(ARG_ALBUM)!!
            )
        ).get(AlbumDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlbumDetailBinding.inflate(inflater)
        binding.viewModel = viewModel

        setupTrackRecycler(binding.trackList)
        setupToaster()

        return binding.root
    }

    /**
     * Sets up toast displaying
     */
    private fun setupToaster() {
        viewModel.toastMessage.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                viewModel.onToashShown()
            }
        })
    }

    /**
     * Initializes [trackList] to display tracks provided by [viewModel]
     *
     * @param trackList
     */
    private fun setupTrackRecycler(trackList: RecyclerView) {
        val adapter = LookupEntityAdapter()
        trackList.adapter = adapter
        viewModel.tracks.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    /**
     * Check passed arguments and navigate back with error message if required argument is null
     */
    private fun validateArguments() {
        if (arguments?.getParcelable<Album>(ARG_ALBUM) == null) {
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
         * @param album [Album] to show details for
         * @return A new instance of fragment [AlbumDetailFragment]
         */
        @JvmStatic
        fun newInstance(album: Album) =
            AlbumDetailFragment().apply {
                arguments = Bundle().apply { putParcelable(ARG_ALBUM, album) }
            }
    }
}