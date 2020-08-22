package com.example.dunzotask.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dunzotask.R
import com.example.dunzotask.data.services.localServices.GetFromLocalDBServices
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.ui.adapters.SearchHistoryAdapter
import kotlinx.android.synthetic.main.fragment_search_history.*

class SearchHistoryFragment : Fragment() {

    companion object {
        fun newInstance(): SearchHistoryFragment {
            return SearchHistoryFragment()
        }
    }

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
    }

    private fun initViews() {
        adapter = SearchHistoryAdapter { searchEntity: SearchHistoryEntity ->
            showSearchResults(searchEntity)
        }
        layoutManager = LinearLayoutManager(context)
        rv_search_history.adapter = adapter
        rv_search_history.itemAnimator = DefaultItemAnimator()
        rv_search_history.layoutManager = layoutManager
        adapter.setAttributes(GetFromLocalDBServices().getSearchHistoryItems())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("frag","onDestroy")
    }

    private fun showSearchResults(searchEntity: SearchHistoryEntity) {}
}