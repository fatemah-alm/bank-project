package com.example.bankProject.kyc

import com.example.bankProject.transactions.TransactionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
class KycController(
    private val kycService: KycService,

    ){


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