package com.codepath.recyclerviewlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.recyclerviewlab.R.layout
import com.codepath.recyclerviewlab.models.Article
import com.codepath.recyclerviewlab.networking.CallbackResponse
import com.codepath.recyclerviewlab.networking.NYTimesApiClient

class ArticleResultFragment: Fragment() {
    private val client = NYTimesApiClient()
    private var recyclerView: RecyclerView? = null
    private var progressSpinner: ContentLoadingProgressBar? = null
    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.action_search).actionView as SearchView
        item.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                loadNewArticlesByQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.fragment_article_result_list, container, false)
        recyclerView = view.findViewById(R.id.list)
        progressSpinner = view.findViewById(R.id.progress)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = ArticleResultsRecyclerViewAdapter()
        return view
    }

    private fun loadNewArticlesByQuery(query: String) {
        Log.d("ArticleResultFragment", "loading articles for query $query")

        val response = object : CallbackResponse<List<Article>> {
            override fun onSuccess(model: List<Article>) {
                Log.d(ArticleResultFragment::class.java.simpleName, "Success")
                val adapter = recyclerView?.adapter as ArticleResultsRecyclerViewAdapter?
                adapter?.setNewArticles(model)
                adapter?.notifyDataSetChanged()
                progressSpinner?.hide()
            }

            override fun onFailure(error: Throwable?) {
                Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
                Log.d(ArticleResultFragment::class.java.simpleName, "failure")
            }

        }

        Toast.makeText(context, "Loading articles for \'$query\'", Toast.LENGTH_SHORT).show()
        progressSpinner?.show()

        client.getArticlesByQuery(response, query)

        // TODO(Checkpoint 3): Implement this method to populate articles
    }

    private fun loadArticlesByPage(page: Int) {
        // TODO(Checkpoint 4): Implement this method to do infinite scroll
    }
}