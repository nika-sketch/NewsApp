package ge.nlatsabidze.newsapplication.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

interface MyDispatchers {

    fun launchBackground(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit): Job
    suspend fun changeToUi(block: suspend CoroutineScope.() -> Unit)

    class Base : MyDispatchers {
        override fun launchBackground(
            scope: CoroutineScope,
            block: suspend CoroutineScope.() -> Unit
        ) = scope.launch(kotlinx.coroutines.Dispatchers.IO, block = block)

        override suspend fun changeToUi(block: suspend CoroutineScope.() -> Unit) {
            withContext(kotlinx.coroutines.Dispatchers.Main, block)
        }
    }
}

interface ProvideInternetConnectionChecker {

    fun isNetworkConnected(): Boolean

    class NetworkHelper(private val context: Context) : ProvideInternetConnectionChecker {

        override fun isNetworkConnected(): Boolean {
            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
            return result
        }
    }
}