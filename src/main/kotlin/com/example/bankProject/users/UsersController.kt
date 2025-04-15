package com.example.bankProject.users

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UsersController(
    val usersRepository: UsersRepository,
    val usersService: UserService

) {
    @GetMapping("/users/v1/users")
    fun getUsers() = usersService.listUsers()

    @PostMapping("/users/v1/register")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<*> {
        return try {
            usersService.createUser(request.username, request.password)
            ResponseEntity.ok("User created successfully")
        } catch (e: InvalidPasswordException) {
            ResponseEntity.badRequest().body(e.message)
        } catch (e: InvalidUsernameException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}


data class CreateUserRequest(
var username: String,
    var password: String

)

