package com.codepath.recyclerviewlab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepath.recyclerviewlab.models.Article

class ArticleResultsRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val articleList: MutableList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_article_result, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            val article: Article = articleList[position]
            holder.tvHeadline.text = article.headline!!.main
            holder.tvSnippet.text = article.snippet
            holder.tvPubDate.text = article.publishDate
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun setNewArticles(articles: List<Article>) {
        articleList.clear()
        articleList.addAll(articles)
    }

    fun addArticles(articles: List<Article>) {
        articleList.addAll(articles)
    }

    internal class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPubDate = itemView.findViewById<TextView>(R.id.article_pub_date)
        val tvHeadline = itemView.findViewById<TextView>(R.id.article_headline)
        val tvSnippet = itemView.findViewById<TextView>(R.id.article_snippet)
    }
}