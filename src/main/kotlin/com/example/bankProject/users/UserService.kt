package com.example.bankProject.users

import jakarta.inject.Named

@Named
class UserService(
    private val userRepository: UsersRepository
) {

    fun listUsers(): List<User> = userRepository.findAll().map {
        User(
            username = it.username,
            password=it.password

        )
    }

    fun createUser(username: String,password:String){

        val newUser = UserEntity( username=username, password=password)
        userRepository.save(newUser)
    }

}

data class User(

    val username: String,
    val password: String,
)