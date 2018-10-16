/**
 * The Fragment acts as the View in the Model, View, ViewModel design pattern.  It is responsible for
 *
 * 1. Instantiating the View and the items that make up the view.
 * 2. Attaching the viewmodel data to the view components
 *
 * Ideally the view would be made up completely of XML.  Data-binding seems to be the preferred method of accomplishing #2 above, but the approach doesn't
 *
 */

package com.loc8r.seattleexplorer.presentation.poiList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.interfaces.OnFragmentInteractionListener
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PoiListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PoiListFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    // Here I'm injecting the viewModelFactory and NOT the viewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var poiListViewModel: PoiListViewModel

    // RecyclerView variables
    private var mRecyclerView: RecyclerView? = null
    @Inject lateinit var poiListAdapter: PoiListAdapter
    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onAttach(context: Context) {
        // Here's I'm kicking off the injection process for the Fragment
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.poi_list_fragment,container,false)

        // Configure the RecyclerView
        mRecyclerView = rootView.findViewById(R.id.recycler_poisList)
        mLayoutManager = LinearLayoutManager(this.context)
        mRecyclerView?.layoutManager = mLayoutManager
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.adapter = poiListAdapter

        // Inflate the layout for this fragment
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // assigning the viewmodel according to the map in the viewModelFactory
        poiListViewModel = ViewModelProviders.of(this,viewModelFactory).get(PoiListViewModel::class.java)

        // This Requests from the Presentation layer the liveData stream which is nothing
        poiListViewModel.getAllPois().observe(this,
                Observer<List<PoiPresentation>> { it ->

                    // let is used to test against null
                    it?.let {
                        poiListAdapter.pois = it
                        poiListAdapter.notifyDataSetChanged()
                    }
                })
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PoiListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PoiListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
