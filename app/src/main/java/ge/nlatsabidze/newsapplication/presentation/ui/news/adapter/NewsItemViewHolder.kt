package ge.nlatsabidze.newsapplication.presentation.ui.news.adapter

import androidx.recyclerview.widget.RecyclerView
import ge.nlatsabidze.newsapplication.common.dateFormatter
import ge.nlatsabidze.newsapplication.common.koinLoad
import ge.nlatsabidze.newsapplication.data.model.Article
import ge.nlatsabidze.newsapplication.databinding.FirstNewsItemBinding
import ge.nlatsabidze.newsapplication.databinding.NewsItemBinding

class NewsItemViewHolder(
    private val binding: NewsItemBinding,
    private val onArticleClicked: ((Article) -> Unit)?
) : BaseViewHolder<Article, NewsItemBinding>(binding) {

    override fun bind(item: Article) = with(binding) {
        item.urlToImage?.let { contentImage.koinLoad(it) }
        publishedDate.text = item.publishedAt.toString().dateFormatter()
        desc.text = item.description.toString()
        newsDescription.text = item.title.toString()

        root.setOnClickListener {
            onArticleClicked?.invoke(item)
        }
    }
}
