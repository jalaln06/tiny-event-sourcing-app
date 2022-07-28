package ru.quipy.`demo-new`

import org.slf4j.LoggerFactory
import ru.quipy.core.AggregateRegistry
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.streams.AggregateSubscriptionsManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.demo.AnnotationBasedProjectEventsSubscriber
import ru.quipy.streams.AggregateEventStreamManager
import javax.annotation.PostConstruct

@Configuration
class EventSourcingApplicationConfiguration {

    private val logger = LoggerFactory.getLogger(EventSourcingApplicationConfiguration::class.java)

    @Autowired
    private lateinit var aggregateRegistry: AggregateRegistry

    @Autowired
    private lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    private lateinit var projectEventSubscriber: AnnotationBasedProjectEventsSubscriber

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Autowired
    private lateinit var eventStreamManager: AggregateEventStreamManager

    @PostConstruct
    fun init() {
        aggregateRegistry.register(UserAggregate::class) {
        }

        subscriptionsManager.subscribe<UserAggregate>(projectEventSubscriber)

        eventStreamManager.maintenance {
            onRecordHandledSuccessfully { streamName, eventName ->
                logger.info("Stream $streamName successfully processed record of $eventName")
            }

            onBatchRead { streamName, batchSize ->
                logger.info("Stream $streamName read batch size: $batchSize")
            }
        }
    }

    @Bean
    fun demoESService() = eventSourcingServiceFactory.getOrCreateService(UserAggregate::class)

}