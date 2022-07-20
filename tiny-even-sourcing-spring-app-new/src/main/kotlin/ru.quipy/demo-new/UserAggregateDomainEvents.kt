package ru.quipy.`demo-new`

import ru.quipy.core.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val USER_CREATED_EVENT = "USER_CREATED_EVENT"

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