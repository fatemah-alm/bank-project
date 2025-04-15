package com.example.bankProject.transactions


import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class TransactionController(
    private val transactionService: TransactionService,

    ){


    @PostMapping("/accounts/v1/accounts/transfer")
    fun transferFunds(@RequestBody request: CreateTransactionRequest) = transactionService.createTransaction(request.sourceAccountNumber, request.destinationAccountNumber,request.amount)

}

data class CreateTransactionRequest(
    val sourceAccountNumber: String,
    val destinationAccountNumber: String,
    val amount: BigDecimal

)