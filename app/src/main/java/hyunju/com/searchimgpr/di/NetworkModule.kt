package hyunju.com.searchimgpr.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hyunju.com.searchimgpr.search.model.SearchRepository
import hyunju.com.searchimgpr.search.model.corouine.SearchRepositoryCoroutine
import hyunju.com.searchimgpr.search.network.SearchNetworkApiCoroutine
import hyunju.com.searchimgpr.search.network.SearchNetworkApiRx
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context) : OkHttpClient {
        val networkInterceptor = getOkHttpNetworkInterceptor()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun getOkHttpNetworkInterceptor(): Interceptor {
        return object : Interceptor {
            val HEADER_API_KEY = "Authorization"
            val API_KEY = "KakaoAK 4587fb7d7927308b69eed818cf4f43aa"

            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest =
                    chain.request().newBuilder()
                        .addHeader(HEADER_API_KEY, API_KEY).build()
                return chain.proceed(newRequest)
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        val BASE_URL = "https://dapi.kakao.com"
        val gsonConvertFactory = GsonConverterFactory.create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(gsonConvertFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchNetworkApiCoroutine(retrofit: Retrofit): SearchNetworkApiCoroutine {
        return retrofit.create(SearchNetworkApiCoroutine::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchNetworkApiRx(retrofit: Retrofit): SearchNetworkApiRx {
        return retrofit.create(SearchNetworkApiRx::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(searchNetworkApiCoroutine: SearchNetworkApiCoroutine) : SearchRepository {
        return SearchRepositoryCoroutine(searchNetworkApiCoroutine)
    }

}