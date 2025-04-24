package com.example.bankProject.kyc

import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*

@RestController
class KycController(
    private val kycService: KycService,

    ){

    @GetMapping("/users/v1/kyc/{userId}")
    fun getKyc (@PathVariable userId: Long) = kycService.getKyc(userId)


    @PostMapping("/users/v1/kyc")
    fun createKyc(@RequestBody request: CreateKycRequest) = kycService.createKyc(request.userId, request.firstName, request.lastName, request.dataOfBirth, request.salary)

}

data class CreateKycRequest(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    var dataOfBirth: Date,
    var salary: BigDecimal
)