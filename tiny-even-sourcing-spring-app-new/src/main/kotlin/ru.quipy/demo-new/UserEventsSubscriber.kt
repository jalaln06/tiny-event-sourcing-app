package ru.quipy.demo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.`demo-new`.UserAggregate
import ru.quipy.`demo-new`.UserCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = UserAggregate::class, subscriberName = "demo-user-stream"
)
class AnnotationBasedUserEventsSubscriber {

    @SubscribeEvent
    fun UserCreatedSubscriber(event: UserCreatedEvent) {
        print(event.userName)
    }
}