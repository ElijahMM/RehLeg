package assist.com.rehleg.network.handlers

import android.util.Log
import assist.com.rehleg.network.models.TopPromotedItemsList
import assist.com.rehleg.network.models.Video
import assist.com.rehleg.network.models.VideoItem
import assist.com.rehleg.network.restclient.RestClient
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by mihai on 01.11.2017.
 */
object ResponseHandler {

    private var responseListener: ResponseListener? = null

    fun setResponseListener(listener: ResponseListener) {
        this.responseListener = listener
    }

    fun getVideosList() {
        if (responseListener == null) throw NetworkInterfaceException("No listener provided")

        RestClient.instance.api?.getVideoItems("androidphone", "7", "en-US")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : MaybeObserver<TopPromotedItemsList> {
                    override fun onComplete() {
                        Log.w("Subs", "onComplete")
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        responseListener?.onErrorReceived(ErrorHandler(e))
                    }

                    override fun onSuccess(t: TopPromotedItemsList) {
                        Log.w("Subs", "onSuccess")
                        responseListener?.onVideoListReceived(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.w("Subs", "onSubscribe")
                    }

                })


    }

    fun getVideoItem(videoId: String) {
        if (responseListener == null) throw NetworkInterfaceException("No listener provided")
        RestClient.instance.api?.getVideo(videoId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : MaybeObserver<VideoItem> {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        responseListener?.onErrorReceived(ErrorHandler(e))
                    }

                    override fun onSuccess(t: VideoItem) {
                        responseListener?.onVideoReceived(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })


    }


    interface ResponseListener {
        fun onVideoListReceived(list: TopPromotedItemsList)
        fun onVideoReceived(item: VideoItem)
        fun onErrorReceived(errorHandler: ErrorHandler)
    }

    class NetworkInterfaceException(override var message: String) : Exception()
}