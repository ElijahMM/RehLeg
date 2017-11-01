package assist.com.rehleg.network.handlers

import android.os.NetworkOnMainThreadException
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by mihai on 01.11.2017.
 */
class ErrorHandler(throwable: Throwable) {

    var errorType: String
    var errorMessage: String

    init {
        when (throwable) {
            is HttpException -> {
                errorType = "HttpException"
                errorMessage = "HttpException"
            }
            is IOException -> {
                errorType = "IOException"
                errorMessage = "Network error"
            }
            is NetworkOnMainThreadException -> {
                errorType = "NetworkOnMainThreadException"
                errorMessage = "NetworkOnMainThreadException"
            }
            else -> {
                errorType = "Unknown error"
                errorMessage = "Unknown error"
            }
        }
    }
}