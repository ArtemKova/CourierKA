package com.ka.courierka.data

import com.ka.courierka.order.Order

class OrderListMapper {
    fun mapOrderToDBOrder(order: Order): OrderItemDBModel = OrderItemDBModel(
        id = order.id,
        name = order.name,
        phone = order.phone,
        adress = order.adress,
        recadress = order.recadress,
        customer_id = order.customer_id,
        time = order.time,
        isPay = order.isPay,
        courier = order.courier,
        city = order.city,
        typeOrder = order.typeOrder,
        delivered = order.delivered
    )

    fun mapDBOrderToOrder(orderItemDBModel: OrderItemDBModel) = Order(
        id = orderItemDBModel.id,
        name = orderItemDBModel.name,
        phone = orderItemDBModel.phone,
        adress = orderItemDBModel.adress,
        recadress = orderItemDBModel.recadress,
        customer_id = orderItemDBModel.customer_id,
        time = orderItemDBModel.time,
        isPay = orderItemDBModel.isPay,
        courier = orderItemDBModel.courier,
        city = orderItemDBModel.city,
        typeOrder = orderItemDBModel.typeOrder,
        delivered = orderItemDBModel.delivered
    )

    fun mapListDBOrderToListOrder(list: List<OrderItemDBModel>) = list.map {
        mapDBOrderToOrder(it)
    }
}


