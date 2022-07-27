package ru.quipy.`demo-new`

import ru.quipy.core.AggregateType
import ru.quipy.domain.Aggregate
import java.util.*
import kotlin.NoSuchElementException
// TODO ID first
// TODO Createdat
@AggregateType(aggregateEventsTableName = "aggregate-user")
data class UserAggregate(
    override val aggregateId: String
) : Aggregate {
    override var createdAt: Long = System.currentTimeMillis()
    override var updatedAt: Long = System.currentTimeMillis()

    var userName: String = ""
    var userLogin: String = ""
    var userPassword: String = ""
    val defaultPaymentId: UUID = TODO()
    var defaultAddressId: UUID
    var paymentMethods = mutableMapOf<UUID, PaymentMethod>()
    var deliveryAddresses = mutableMapOf<UUID, DeliveryAddress>()
}

data class DeliveryAddress(
    val addressId: UUID,
    val address: String
)

data class PaymentMethod(
    val paymentMethodId: UUID,
    val cardNumber: String
)

fun UserAggregate.createUserCommand(
    name: String,
    password: String,
    login: String
): UserCreatedEvent {

    return UserCreatedEvent(
        userId = aggregateId,
        userLogin = login,
        userPassword = password,
        userName = name
    )
}

fun UserAggregate.addAddressCommand(
    address: String,
): UserAddedAddressEvent {

    return UserAddedAddressEvent(
        address = address,
        addressId = UUID.randomUUID(),
        userId = aggregateId
    )
}

fun UserAggregate.changePasswordCommand(
    password: String,
): UserChangedPasswordEvent{
    if( password == this.userPassword){
        throw java.lang.IllegalArgumentException("this password has already been used, try another one!")
    }
    if( password == ""){
        throw java.lang.IllegalArgumentException("there is no password")
    }
    if( password.length<8){
        throw java.lang.IllegalArgumentException("Password is too weak")
    }
    return UserChangedPasswordEvent(
        password = password,
        userId = aggregateId
    )
}

fun UserAggregate.setDefaultAddressCommand(
    addressId: UUID,
): UserSetDefaultAddressEvent{
    if( !this.deliveryAddresses.contains(addressId)){
        throw java.lang.IllegalArgumentException("There is no such address")
    }
    return UserSetDefaultAddressEvent(
        addressId = addressId,
        userId = aggregateId
    )
}