package com.hardik.myapplication.usecase

import com.hardik.myapplication.base.BaseUseCase
import com.hardik.myapplication.database.SongsDAO
import com.hardik.myapplication.network.beans.Entry
import com.hardik.myapplication.util.extension.flowSingle
import com.hardik.myapplication.util.extension.mapToDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertSongsUseCase @Inject constructor(private val songsDAO: SongsDAO) :
    BaseUseCase<InsertSongsUseCase.Params, Unit>() {
    override fun onBuild(params: Params): Flow<Unit> {
        return flowSingle { songsDAO.insertSongs(params.entityList.mapToDB()) }
    }

    data class Params(val entityList: List<Entry>)
}