package ru.quipy.`demo-new`

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import ru.quipy.core.EventSourcingService
import ru.quipy.demo.ProjectAggregate
import java.util.*

@Controller
class UserCommandsController {
    private lateinit var demoESService: EventSourcingService<UserAggregate>
    @GetMapping("/User/add")
    fun createUser(user: UserCreatedDTO) {
        this.demoESService.update(UUID.randomUUID().toString()){
            it.createUser(user.userName,user.userPassword,user.userLogin)
        }
    }
}