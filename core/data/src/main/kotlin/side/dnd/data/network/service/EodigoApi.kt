package side.dnd.data.network.service

import retrofit2.http.GET
import side.dnd.core.local.model.CategoryResponse

interface EodigoApi {
    
    @GET("products/hierarchy")
    suspend fun getCategories(): List<CategoryResponse>
}

