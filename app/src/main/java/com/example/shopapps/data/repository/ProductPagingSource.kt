import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.shopapps.data.remote.ApiService
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.repository.ProductRepository

class ProductPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ProductItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItem> {
        return try {
            val page = params.key ?: 1  // Default to first page
            val limit = params.loadSize  // Number of items to fetch in one load
            val response = apiService.getAllProducts(limit)
            val nextKey = if (response.size < limit) null else page + 1

            LoadResult.Page(
                data = response.map { it.toDomainProductItem() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductItem>): Int? {
        return state.anchorPosition
    }
}
