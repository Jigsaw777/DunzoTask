package com.example.dunzotask.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dunzotask.R
import com.example.dunzotask.domain.entities.PhotoEntity
import com.example.dunzotask.ui.adapters.SearchItemAdapter
import com.example.dunzotask.ui.viewmodels.MainViewModel
import com.example.dunzotask.utils.DisplayUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var adapter: SearchItemAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseVariables()
        observeEvents()
    }

    private fun initialiseVariables() {
        adapter = SearchItemAdapter()
        layoutManager = GridLayoutManager(this, DisplayUtils.calculateNumberOfColumns(this))
        rv_search_items.layoutManager = layoutManager
        rv_search_items.itemAnimator = DefaultItemAnimator()
        rv_search_items.adapter = adapter
    }

    private fun showLoader(isPaginationProgress: Boolean = false) {
        if (isPaginationProgress)
            pb_pagination.visibility = View.VISIBLE
        else
            pb_main.visibility = View.VISIBLE
    }

    private fun hideLoader(isPaginationProgress: Boolean = false) {
        if (isPaginationProgress)
            pb_pagination.visibility = View.GONE
        else
            pb_main.visibility = View.GONE
    }

    private fun observeEvents() {
        btn_search.setOnClickListener {
            hideKeyboard()
            adapter.clear()
            mainViewModel.savedList.clear()
            val searchTerm = et_search.text.toString()
            if (searchTerm.trim().isEmpty()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.empty_search_term),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mainViewModel.searchTerm = searchTerm
                mainViewModel.getSearchResults()
                showLoader()
                tv_hint_text_search_result.visibility = View.GONE
            }
        }

        mainViewModel.errorLiveData.observe(this, Observer {
            hideLoader()
            rv_search_items.visibility = View.GONE
            tv_hint_text_search_result.visibility = View.VISIBLE
            tv_hint_text_search_result.text = resources.getString(R.string.app_name)
        })

        mainViewModel.searchItemsResult.observe(this, Observer {
            if (mainViewModel.savedList.isNotEmpty() && !mainViewModel.isNetworkFetched) {
                setDataInRecyclerView(mainViewModel.savedList)
                rv_search_items.visibility = View.VISIBLE
                tv_hint_text_search_result.visibility = View.GONE
            } else {
                if (it.photos.isEmpty() && adapter.isEmpty()) {
                    tv_hint_text_search_result.text = resources.getString(R.string.no_results_text)
                    tv_hint_text_search_result.visibility = View.VISIBLE
                } else if (it.photos.isNotEmpty()) {
                    setDataInRecyclerView(it.photos)
                    rv_search_items.visibility = View.VISIBLE
                    tv_hint_text_search_result.visibility = View.GONE
                }
            }
            hideLoader()
            hideLoader(true)
        })
    }

    private fun setDataInRecyclerView(items: List<PhotoEntity>) {
        adapter.setAttributes(items)
        rv_search_items.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val currentItems = layoutManager.childCount
                val totalItems = layoutManager.itemCount
                val scrollItems = layoutManager.findFirstVisibleItemPosition()

                if (mainViewModel.isScrolling && (currentItems + scrollItems == totalItems)) {
                    mainViewModel.isScrolling = false
                    hideLoader(true)
                    mainViewModel.getSearchResults(shouldIncrementPageNumber = true)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    mainViewModel.isScrolling = true
                    showLoader((true))
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mainViewModel.rvLastPosition = layoutManager.findFirstVisibleItemPosition()
        mainViewModel.savedList = adapter.getPhotosList()
        mainViewModel.isNetworkFetched = false
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        rv_search_items.scrollToPosition(mainViewModel.rvLastPosition)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }
}