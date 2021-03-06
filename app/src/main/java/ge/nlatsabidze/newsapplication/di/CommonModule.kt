package ge.nlatsabidze.newsapplication.di

import dagger.Module
import dagger.Provides
import javax.inject.Named
import dagger.hilt.InstallIn
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import ge.nlatsabidze.newsapplication.common.*
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ge.nlatsabidze.newsapplication.presentation.ui.news.NewsUi
import ge.nlatsabidze.newsapplication.presentation.ui.details.Details
import ge.nlatsabidze.newsapplication.presentation.ui.news.ResultToNewsUiMapper

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    fun provideResourceManager(@ApplicationContext context: Context): ProvideResources =
        ProvideResources.Base(context)

    @Provides
    fun provideInternetConnection(@ApplicationContext context: Context): InternetConnection =
        InternetConnection.NetworkHelper(context)

    @Provides
    fun provideCommunication(): Communication<NewsUi> =
        Communication.BaseNews(NewsUi.Loading())

    @Provides
    fun provideIo(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideDispatchers(): ge.nlatsabidze.newsapplication.common.Dispatchers =
        ge.nlatsabidze.newsapplication.common.Dispatchers.Base()

    @Provides
    fun provideResultHandler(): HandleResult = HandleResult.Base()

    @Provides
    @Named("firstItem")
    fun provideImageLoader(): LoadImage = LoadImage.GithubImageBase()

    @Provides
    @Named("stringMapper")
    fun provideDateFormat(): Mapper<String, String> = AbstractDateFormat.DateFormatter()

    @Provides
    fun provideDetails(
        @Named("firstItem") imageLoader: LoadImage,
        @Named("stringMapper") mapper: Mapper<String, String>
    ): Details = Details.Base(imageLoader, mapper)

    @Provides
    fun provideResultFactory(): ResultToNewsUiMapper = ResultToNewsUiMapper.Base()
}
