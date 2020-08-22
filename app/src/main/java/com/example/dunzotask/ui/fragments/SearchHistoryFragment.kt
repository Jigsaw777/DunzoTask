package com.example.dunzotask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dunzotask.R
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.utils.ObjectBox

class SearchHistoryFragment : Fragment() {

    companion object{
        fun newInstance():SearchHistoryFragment{
            return SearchHistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchHistoryBox = ObjectBox.boxStore.boxFor(SearchHistoryEntity::class.java).all.size
        Toast.makeText(context, searchHistoryBox.toString(), Toast.LENGTH_SHORT).show()
    }
}