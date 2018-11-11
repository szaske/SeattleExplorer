package com.loc8r.seattleexplorer.presentation.collectionsList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.SharedViewModel
import com.loc8r.seattleexplorer.presentation.interfaces.OnFragmentInteractionListener
import com.loc8r.seattleexplorer.presentation.models.CollectionPresentation
import com.loc8r.seattleexplorer.presentation.utils.Resource
import com.loc8r.seattleexplorer.presentation.utils.ResourceState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.collections_list_fragment.*
import javax.inject.Inject

class CollectionsListFragment : Fragment() {

    // Here I'm injecting the viewModelFactory and NOT the viewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory
    // private lateinit var collectionsListViewModel: CollectionsListViewModel
    private lateinit var sharedViewModel: SharedViewModel

    // RecyclerView variables
    private var mRecyclerView: RecyclerView? = null
    @Inject lateinit var colListAdapter: CollectionListAdapter
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    // To be used for fragment communications
    private var listener: OnFragmentInteractionListener? = null

    companion object {
        fun newInstance() = CollectionsListFragment()
    }


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.collections_list_fragment, container, false)

        // Configure the RecyclerView
        mRecyclerView = rootView.findViewById(R.id.recycler_colList)
        mLayoutManager = LinearLayoutManager(this.context)
        mRecyclerView?.layoutManager = mLayoutManager
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.adapter = colListAdapter

        // Inflate the layout for this fragment
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // assigning the viewModel according to the map in the viewModelFactory
        sharedViewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(SharedViewModel::class.java)
        // collectionsListViewModel = ViewModelProviders.of(this,viewModelFactory).get(CollectionsListViewModel::class.java)

        //Start collections Observable
        sharedViewModel.fetchAllCollections()

        // This Requests from the Presentation layer the liveData stream which is nothing
        sharedViewModel.getAllCollections().observe(this,Observer<Resource<List<CollectionPresentation>>> { it ->
                    // let is used to test against null
                    it?.let {
                        handleData(it)
                    }
                })
    }

    // A function for showing or hiding the progress circle
    private fun handleData(resource: Resource<List<CollectionPresentation>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                onResourceSuccess(resource.data)
            }
            ResourceState.LOADING -> {
                // While loading make progress view visible
                progress_col_list.visibility = View.VISIBLE
                recycler_colList.visibility = View.GONE
            }
            ResourceState.ERROR -> {
                Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onResourceSuccess(projects: List<CollectionPresentation>?) {
        progress_col_list.visibility = View.GONE
        projects?.let {
            colListAdapter.collections= it
            colListAdapter.notifyDataSetChanged()
            recycler_colList.visibility = View.VISIBLE
        }
    }


}
