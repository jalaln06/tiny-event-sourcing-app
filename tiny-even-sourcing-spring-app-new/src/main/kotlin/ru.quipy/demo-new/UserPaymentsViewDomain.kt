package ru.quipy.`demo-new`

import org.springframework.data.annotation.*
import org.springframework.data.mongodb.core.mapping.*
import org.springframework.data.mongodb.repository.*
import org.springframework.stereotype.*
import ru.quipy.domain.*
import ru.quipy.streams.*
import java.util.UUID
import javax.annotation.*

class UserPaymentsViewDomain {
    @Document("user-payment-view")
    data class UserPayments(
            @Id
            override val id: String, // userId
            var favoritePaymentId: UUID? = null, //Id of favorite payment
            val payments: MutableMap<UUID, Payment> = mutableMapOf() // map to hold all payments
    ) : Unique<String>

    data class Payment(
            val paymentId: UUID,
            val payment: String
    )
}
@Service
class UserPaymentsViewService(
        val userPaymentsRepository: UserPaymentsRepository,
        private val subscriptionsManager: AggregateSubscriptionsManager,
){
    @Repository
    interface UserPaymentsRepository : MongoRepository<UserPaymentsViewDomain.UserPayments, String> {
    }
    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "userPayments-payment-event-publisher-stream") {
            `when`(UserCreatedEvent::class) { event ->
                createUserPayment(event.userId, event.userName)
            }
            `when`(UserAddedPaymentEvent::class){ event->
                addPayment(event.paymentMethodId,event.userId,event.paymentMethod)
            }
            `when`(UserSetDefaultPaymentEvent::class){event->
                setDefaultPayment(event.userId,event.paymentMethodId)
            }
        }
    }

    private fun setDefaultPayment(userId: String, paymentMethodId: UUID) {
        val userPayments = userPaymentsRepository.findById(userId).orElse(
                UserPaymentsViewDomain.UserPayments(userId)
        )
        userPayments.favoritePaymentId = paymentMethodId
    }


    private fun createUserPayment(userId: String, userName: String) {
        val userPayments = UserPaymentsViewDomain.UserPayments(userId)
        userPaymentsRepository.save(userPayments)
    }

    private fun addPayment(paymentMethodId: UUID, userId: String, paymentMethod: String) {
        val userPayments = userPaymentsRepository.findById(userId).orElse(
                UserPaymentsViewDomain.UserPayments(userId)
        )
        userPayments.payments.put(paymentMethodId,UserPaymentsViewDomain.Payment(paymentMethodId,paymentMethod))
    }
}