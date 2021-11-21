package hyunju.com.searchimgpr.search.model


interface SearchRepository {
    fun loadSearchList(searchText: String): Any
}