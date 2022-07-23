package ru.quipy.`demo-new`

import ru.quipy.core.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val USER_CREATED_EVENT = "USER_CREATED_EVENT"
const val USER_ADDED_PAYMENT_EVENT = "USER_ADDED_PAYMENT_EVENT"
const val USER_ADDED_ADDRESS_EVENT = "USER_ADDED_ADDRESS_EVENT"
@DomainEvent(name = USER_CREATED_EVENT)
class UserCreatedEvent(
    val userLogin: String,
    val userPassword: String,
    val userName: String,
    val userId: String,
    createdAt: Long = System.currentTimeMillis(),
)   : Event<UserAggregate>(
    name = USER_CREATED_EVENT,
    createdAt = createdAt,
    aggregateId = userId,
){
    override fun applyTo(aggregate: UserAggregate) {
        aggregate.userLogin=userLogin
        aggregate.userPassword=userPassword
        aggregate.userName=userName
    }
}
@DomainEvent(name = USER_ADDED_PAYMENT_EVENT)
class UserAddedPaymentEvent(
    val paymentMethod: String,
    val paymentMethodId: UUID,
    val userId:String
) : Event<UserAggregate>(
    name =  USER_ADDED_PAYMENT_EVENT,
    aggregateId = userId
) {
    override fun applyTo(aggregate: UserAggregate) {
        aggregate.paymentMethods[paymentMethodId]= PaymentMethod(paymentMethod,paymentMethodId)
    }
}

@DomainEvent(name = USER_ADDED_ADDRESS_EVENT)
class UserAddedAddressEvent(
    val address: String,
    val addressId: UUID,
    val userId:String
) : Event<UserAggregate>(
    name =  USER_ADDED_ADDRESS_EVENT,
    aggregateId = userId.toString()
) {
    override fun applyTo(aggregate: UserAggregate) {
        aggregate.paymentMethods[addressId]= PaymentMethod(address,addressId)
    }
}