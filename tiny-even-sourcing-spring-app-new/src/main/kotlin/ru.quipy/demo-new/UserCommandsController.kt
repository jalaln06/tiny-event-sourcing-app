package ru.quipy.`demo-new`

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import ru.quipy.core.EventSourcingService
import java.util.*

@Controller
class UserCommandsController {
    private lateinit var demoESService: EventSourcingService<UserAggregate>
    @GetMapping("/User/add")
    fun createUser(user: UserCreatedDTO) {
        this.demoESService.update(UUID.randomUUID().toString()){
            it.createUserCommand(user.userName,user.userPassword,user.userLogin)
        }
    }
    @GetMapping("/User/{userId}/SetAddress/{addressId}")
    fun setDefaultAddress(
        @PathVariable addressId: String,
        @PathVariable userId: String){
        this.demoESService.update(userId){
            it.setDefaultAddressCommand(addressId)
        }
    }
    @GetMapping("/User/{userId}/address")
    fun addAddress(
        @RequestBody body: AddAddressDTO,
        @PathVariable userId: String
    ){
        this.demoESService.update(userId){
            it.addAddressCommand(body.address)
        }
    }
}