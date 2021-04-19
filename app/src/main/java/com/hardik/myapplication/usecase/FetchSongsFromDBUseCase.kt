package com.hardik.myapplication.usecase

import com.hardik.myapplication.base.BaseUseCase
import com.hardik.myapplication.database.SongsDAO
import com.hardik.myapplication.database.SongsEntity
import com.hardik.myapplication.util.extension.flowSingle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSongsFromDBUseCase @Inject constructor(private val songsDAO: SongsDAO) :
    BaseUseCase<Unit, List<SongsEntity>>() {
    override fun onBuild(params: Unit): Flow<List<SongsEntity>> {
        return flowSingle { songsDAO.getSongs() }
    }
}