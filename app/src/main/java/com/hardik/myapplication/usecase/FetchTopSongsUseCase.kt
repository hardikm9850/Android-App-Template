package com.hardik.myapplication.usecase

import com.hardik.myapplication.base.BaseUseCase
import com.hardik.myapplication.network.AppleSongsApi
import com.hardik.myapplication.network.beans.Feed
import com.hardik.myapplication.util.extension.flowSingle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTopSongsUseCase @Inject constructor(private val songsApi: AppleSongsApi) :
    BaseUseCase<FetchTopSongsUseCase.Params, Feed>() {
    override fun onBuild(params: Params): Flow<Feed> {
        return flowSingle { songsApi.getTopSongs(params.limit) }
    }

    data class Params(val limit: String)
}