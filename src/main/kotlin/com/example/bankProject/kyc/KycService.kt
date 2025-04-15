package com.example.bankProject.kyc

import com.example.bankProject.users.UserEntity
import com.example.bankProject.users.UsersRepository
import jakarta.inject.Named
import java.math.BigDecimal
import java.util.*

@Named
class KycService(
    private val kycRepository: KycRepository,
    private val usersRepository: UsersRepository,
) {

    fun listKyc(): List<Kyc> = kycRepository.findAll().map {
        Kyc(
            userId = it.user.id?: throw Exception(" Account was expecting user id not to be null..."),
            firstName = it.firstName,
            lastName = it.lastName,
            dataOfBirth = it.dataOfBirth,
            salary = it.salary
        )
    }

    fun getKyc(userId:Long) : Kyc {
        val kyc = kycRepository.findByUserId(userId)
        return kyc
    }

    fun createKyc(userId: Long, firstName: String, lastName: String,dataOfBirth:Date,salary: BigDecimal){
        val user = usersRepository.findById(userId).get()
        val newKyc = KycEntity(user=user, firstName = firstName, lastName = lastName, dataOfBirth = dataOfBirth, salary =salary)
        kycRepository.save(newKyc)
    }



}


data class Kyc(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    var dataOfBirth: Date,
    var salary: BigDecimal

)