package assist.com.rehleg.network.restclient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by mihai on 01.11.2017.
 */

class RestClient private constructor() {

    companion object {
        val instance: RestClient by lazy { RestClient() }
    }

    var api: API? = null
        private set(value) {
            field = value
        }

    private val baseURL = "https://wwwsecure.lego.com"

    init {
        val builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.networkInterceptors().add(httpLoggingInterceptor)
        val okHttpClient = builder.build()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseURL)
                .client(okHttpClient)
                .build()

        api = retrofit.create(API::class.java)
    }


}