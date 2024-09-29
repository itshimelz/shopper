package com.himelz.data.network

import com.himelz.data.model.response.CategoryListResponse
import com.himelz.data.model.response.ProductListResponse
import com.himelz.domain.model.CategoryListModel
import com.himelz.domain.model.ProductListModel
import com.himelz.domain.network.NetworkService
import com.himelz.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import io.ktor.utils.io.errors.IOException

class NetworkServiceImpl(private val client: HttpClient) : NetworkService {

    private val baseUrl = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com"

    //Get Products Web Request
    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {

        val url =
            if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products"

        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { dataModels: ProductListResponse ->
                dataModels.toProductList()
            }
        )
    }

    //Get Categories Web Request
    override suspend fun getCategories(): ResultWrapper<CategoryListModel> {
        return makeWebRequest(
            url = "$baseUrl/categories",
            method = HttpMethod.Get,
            mapper = { categories: CategoryListResponse ->
                categories.toCategoryList()
            }
        )
    }

    //Generic Web Request
    @OptIn(InternalAPI::class)
    private suspend inline fun <reified T, R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method

                // Apply query parameters
                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach { (key, value) ->
                            append(key, value)
                        }
                    })
                }

                // Apply headers
                headers.forEach { (key, value) ->
                    header(key, value)
                }

                // Set body for POST, PUT, etc.
                if (body != null) {
                    this.body = body
                }

                // Set content type
                contentType(ContentType.Application.Json)
            }.body<T>()

            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)

        } catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }
}