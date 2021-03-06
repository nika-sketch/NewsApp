package ge.nlatsabidze.newsapplication.presentation.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ge.nlatsabidze.newsapplication.common.collectFlow
import ge.nlatsabidze.newsapplication.data.model.Article
import ge.nlatsabidze.newsapplication.databinding.NewsFragmentBinding
import ge.nlatsabidze.newsapplication.presentation.ui.base.BaseFragment
import ge.nlatsabidze.newsapplication.presentation.ui.news.adapter.NewsItemAdapter
import ge.nlatsabidze.newsapplication.presentation.ui.news.adapter.OnItemClickListener

@AndroidEntryPoint
class NewsFragment : BaseFragment<NewsFragmentBinding>(NewsFragmentBinding::inflate),
    OnItemClickListener<Article> {

    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {
        newsAdapter = NewsItemAdapter(this@NewsFragment)
        binding.rvNews.adapter = newsAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())

        newsViewModel.collectNews { it.apply(binding, newsAdapter) }
        collectFlow(newsViewModel.navigation) { it.navigate(findNavController()) }
    }

    override fun onItemClick(item: Article) {
        newsViewModel.navigateToDetails(item)
    }
}
