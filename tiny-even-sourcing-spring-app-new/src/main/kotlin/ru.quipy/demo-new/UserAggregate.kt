package ru.quipy.`demo-new`

import ru.quipy.core.AggregateType
import ru.quipy.domain.Aggregate
import java.util.*
import kotlin.NoSuchElementException

@AggregateType(aggregateEventsTableName = "aggregate-user")
data class UserAggregate(
    override val aggregateId: String
) : Aggregate {
    override var createdAt: Long = System.currentTimeMillis()
    override var updatedAt: Long = System.currentTimeMillis()

    var userName: String = ""
    var userLogin: String = ""
    var userPassword: String = ""
    val defaultPaymentId: String = ""
    val defaultAddressId: String = ""
    var paymentMethods = mutableMapOf<UUID,PaymentMethod>()
    var deliveryAddresses = mutableMapOf<UUID,DeliveryAddress>()
}

data class DeliveryAddress(
    val address: String,
    val addressId: UUID
)
data class PaymentMethod(
    val cardNumber: String,
    val paymentMethodId: UUID
)
fun UserAggregate.createUser(
    name: String,
    password:String,
    login:String
): UserCreatedEvent {
    if (createdAt != -1L) {
        throw NoSuchElementException()
    }

    return UserCreatedEvent(
        userId = aggregateId,
        userLogin=login,
        userPassword=password,
        userName=name
    )
}

fun UserAggregate.addAddress(
    address: String,
): UserAddedAddressEvent {
    if (createdAt != -1L) {
        throw NoSuchElementException()
    }

    return UserAddedAddressEvent(
        address=address,
        addressId=UUID.randomUUID(),
        userId = aggregateId
    )
}