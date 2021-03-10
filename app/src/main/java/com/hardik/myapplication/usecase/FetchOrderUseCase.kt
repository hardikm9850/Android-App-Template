package com.hardik.myapplication.usecase

import com.hardik.myapplication.base.BaseUseCase
import com.hardik.myapplication.database.OrderEntity
import com.hardik.myapplication.network.OrderAPI
import com.hardik.myapplication.util.extension.flowSingle
import com.hardik.myapplication.util.extension.mapToDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchOrderUseCase @Inject constructor(private val orderAPI: OrderAPI) :
    BaseUseCase<Unit, MutableList<OrderEntity>>() {
    override fun onBuild(params: Unit): Flow<MutableList<OrderEntity>> {
        return flowSingle {
            orderAPI.getOrders()
        }.map { order ->
            val list = mutableListOf<OrderEntity>()
            order.customers.forEach {
                list.add(it.mapToDB())
            }
            list
        }
    }

    data class Params(val numberOfData: String)
}