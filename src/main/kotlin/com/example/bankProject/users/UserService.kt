package com.example.bankProject.users

import jakarta.inject.Named



@Named
class UserService(
    private val userRepository: UsersRepository
) {

    fun listUsers(): List<User> = userRepository.findAll().map {
        User(
            id=it.id,
            username = it.username,
            password=it.password

        )
    }

    fun createUser(username: String,password:String){
        if (password.length < 6) throw InvalidPasswordException()
        if (username == "admin") throw InvalidUsernameException("Username 'admin' is reserved")
        val newUser = UserEntity( username=username, password=password)

        userRepository.save(newUser)
    }

}

data class User(
    val id: Long?,

    val username: String,
    val password: String,
)



class InvalidPasswordException(message: String = "Password must be at least 6 characters") : RuntimeException(message)
class InvalidUsernameException(message: String = "Username is not allowed") : RuntimeException(message)
