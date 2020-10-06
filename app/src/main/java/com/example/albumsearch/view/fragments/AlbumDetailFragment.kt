package com.example.albumsearch.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.albumsearch.R
import com.example.albumsearch.model.network.dto.Album

private const val ARG_ALBUM = "album"

class AlbumDetailFragment : Fragment() {
    private var album: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { album = it.getParcelable(ARG_ALBUM) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
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