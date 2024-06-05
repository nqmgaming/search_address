package com.nqmgaming.searchaddresslab.data.repository

import android.util.Log
import com.nqmgaming.searchaddresslab.core.util.Resources
import com.nqmgaming.searchaddresslab.data.mapper.toDomainResponse
import com.nqmgaming.searchaddresslab.data.remote.ApiService
import com.nqmgaming.searchaddresslab.domain.model.Response
import com.nqmgaming.searchaddresslab.domain.repository.HereRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HereRepositoryImpl @Inject constructor(
    private val hereApi: ApiService
) : HereRepository {

    private val TAG = "HereRepositoryImpl"

    override fun getAddresses(
        q: String
    ): Flow<Resources<Response>> {
        return flow {
            emit(Resources.Loading(true))
            val hereList = try {
                hereApi.getAddresses(q)
            } catch (e: IOException) {
                Log.e(TAG, "Get addresses: ${e.stackTraceToString()}")
                emit(Resources.Error("An error occurred while fetching data"))
                null
            } catch (e: HttpException) {
                Log.e(TAG, "Get addresses: ${e.stackTraceToString()}")
                emit(Resources.Error("An error occurred while fetching data"))
                null
            }

            if (hereList != null) {
                emit(Resources.Loading(false))
            }

            hereList.let { listing ->
                println("HereRepositoryImpl: get addresses: $listing")
                emit(
                    Resources.Success(
                        data = listing?.toDomainResponse()
                    )
                )
                emit(Resources.Loading(false))
            }

        }
    }
}