package assist.com.rehleg.network.restclient

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import assist.com.rehleg.network.models.*
import io.reactivex.Maybe

/**
 * Created by mihai on 01.11.2017.
 */
interface API {

    @GET("/services/contentpromotionapi/api/v1/contentpromotion/Video/com.lego.common.life/{platform}/{minimumVersion}/")
    fun getVideoItems(@Path("platform") platform: String, @Path("minimumVersion") minimumVersion: String, @Query("languageVersion") culture: String): Maybe<TopPromotedItemsList>

    @GET("/en-US/mediaplayer/api/video.json/{videoId}")
    fun getVideo(@Path("videoId") videoId: String): Maybe<VideoItem>
}