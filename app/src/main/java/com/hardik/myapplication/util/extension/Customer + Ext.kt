package com.hardik.myapplication.util.extension

import com.hardik.myapplication.database.OrderEntity
import com.hardik.myapplication.network.beans.Customer

const val ORDER_IN_PLACE = "AT"

fun Customer.mapToDB(): OrderEntity {
    return OrderEntity(
        orderId = this.orderRef,
        orderStatus = if (status == ORDER_IN_PLACE) 0 else 1,
        address = this.customer.address,
        city = this.customer.city,
        phone = this.customer.phoneNumber,
        scheduledDate = this.scheduleDate,
        imageUrl = this.imageUrl,
        firstName = this.customer.firstName,
        lastName = this.customer.lastName,
        instruction = this.serviceSpecialInstructions,
        serviceType = this.serviceRequested
    )
}