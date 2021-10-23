package hyunju.com.searchimgpr.search.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchNetworkApi {
    @GET("/v2/search/image")
    suspend fun getImageData(@QueryMap params: Map<String, String>): Response<ResImageData>

    @GET("/v2/search/vclip")
    suspend fun getVclipData(@QueryMap params: Map<String, String>): Response<ResVclipData>
}