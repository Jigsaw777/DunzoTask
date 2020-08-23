package com.example.dunzotask.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dunzotask.R
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.ui.adapters.SearchHistoryAdapter
import com.example.dunzotask.ui.viewmodels.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_history.*

@AndroidEntryPoint
class SearchHistoryFragment : Fragment() {

    companion object {
        fun newInstance(): SearchHistoryFragment {
            return SearchHistoryFragment()
        }
    }

    private val searchViewModel: SearchFragmentViewModel by viewModels()
    private lateinit var adapter: SearchHistoryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        adapter = SearchHistoryAdapter { searchEntity: SearchHistoryEntity ->
            showSearchResults(searchEntity)
        }
        layoutManager = LinearLayoutManager(context)
        rv_search_history.adapter = adapter
        rv_search_history.itemAnimator = DefaultItemAnimator()
        rv_search_history.layoutManager = layoutManager

        //fetch list only once and then cache it
        if (!searchViewModel.hasListBeenFetchedOnce)
            searchViewModel.getSearchList()
        
    }

    private fun initListeners() {
        searchViewModel.searchListLiveData.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                tv_empty_items.visibility = View.VISIBLE
                rv_search_history.visibility = View.GONE
            } else {
                tv_empty_items.visibility = View.GONE
                rv_search_history.visibility = View.VISIBLE
                Log.d("history", "came here after config changes")
                if (searchViewModel.cachedListForConfigChange.isEmpty())
                    searchViewModel.cachedListForConfigChange = it.toMutableList()
                adapter.setAttributes(searchViewModel.cachedListForConfigChange)
                rv_search_history.scrollToPosition(searchViewModel.lastPosition)
            }
        })
    }

    private fun showSearchResults(searchEntity: SearchHistoryEntity) {
        val str = searchEntity.time.split(",")
        Toast.makeText(
            context,
            "You searched for ${searchEntity.searchTerm} on ${str[0]} at${str[1]}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        searchViewModel.lastPosition = layoutManager.findFirstVisibleItemPosition()
        Log.d("history", "position : ${searchViewModel.lastPosition}")
        super.onSaveInstanceState(outState)
    }
}