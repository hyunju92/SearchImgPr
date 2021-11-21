package hyunju.com.searchimgpr.search.network

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchNetworkApiCoroutine {
    @GET("/v2/search/image")
    suspend fun getImageData(@QueryMap params: Map<String, String>): Response<ResImageData>

    @GET("/v2/search/vclip")
    suspend fun getVclipData(@QueryMap params: Map<String, String>): Response<ResVclipData>
}


interface SearchNetworkApiRx {
    @GET("/v2/search/image")
    fun getImageDataByObservable(@QueryMap params: Map<String, String>): Observable<ResImageData>

    @GET("/v2/search/vclip")
    fun getVclipDataByObservable(@QueryMap params: Map<String, String>): Observable<ResVclipData>
}