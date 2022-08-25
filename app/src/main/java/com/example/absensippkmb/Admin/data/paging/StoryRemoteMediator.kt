package com.example.absensippkmb.Admin.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.absensippkmb.Admin.data.model.AttendenceModel
import com.example.absensippkmb.Admin.data.network.ApiService
import com.example.absensippkmb.Admin.database.RemoteKeys
import com.example.absensippkmb.Admin.database.UserScanDatabase
import retrofit2.awaitResponse

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val userScanDatabase: UserScanDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, AttendenceModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AttendenceModel>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val response = apiService.getUserAbsensi(location = 0, page = page, size = state.config.pageSize).awaitResponse().body()
            val responseData = response?.data as List<AttendenceModel>
            val endOfPaginationReached = responseData.isEmpty()

            Log.i("StoryRemoteMediator", "inserting: $response")

            userScanDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userScanDatabase.remoteKeysDao().deleteAllRemoteKeys()
                    userScanDatabase.userStoryDao().deleteAllAttedence()
                }

                val nextKey = if (endOfPaginationReached) null else page + 1
                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val keys = responseData.map {
                    RemoteKeys(
                        id = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                userScanDatabase.remoteKeysDao().insertAll(keys)

                userScanDatabase.userStoryDao().insertUserAttedenceModel(responseData)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AttendenceModel>) : RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            userScanDatabase.remoteKeysDao().getRemoteKey(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AttendenceModel>) : RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            userScanDatabase.remoteKeysDao().getRemoteKey(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, AttendenceModel>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                userScanDatabase.remoteKeysDao().getRemoteKey(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}