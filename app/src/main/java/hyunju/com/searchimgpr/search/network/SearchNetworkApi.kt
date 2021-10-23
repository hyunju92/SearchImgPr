package hyunju.com.searchimgpr.search.network

import retrofit2.http.Body
import retrofit2.http.GET

interface SearchNetworkApi {
    @GET("/v2/search/image ")
    fun getImageData(@Body body: ReqImageData): ResImageData

    @GET("/v2/search/vclip")
    fun getVclipData(@Body body: ReqVclipData): ResVclipData
}