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
    var orders = mutableMapOf<UUID, OrderEntity>()
    var children = mutableMapOf<UUID,ChildEntity>()
}

data class OrderEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val itemsAssigned: MutableSet<UUID>
)
data class ChildEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String
)
data class DeliveryAddress(
    val address: String,
    val preference: Boolean
)
data class PaymentMethod(
    val cardNumber: String,
    val preference: Boolean
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