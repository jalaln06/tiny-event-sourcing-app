package ru.quipy.demo

import org.springframework.stereotype.Service
import ru.quipy.`demo-new`.UserAggregate
import ru.quipy.`demo-new`.UserCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(aggregateClass = UserAggregate::class, subscriberName = "demo-user-stream")
class AnnotationBasedUserEventsSubscriber {

    @SubscribeEvent
    fun userCreatedSubscriber(event: UserCreatedEvent) {
        print(event.userName)
    }
}