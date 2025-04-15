package com.example.bankProject.transactions


import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class TransactionController(
    private val transactionService: TransactionService,

    ) {


    @PostMapping("/accounts/v1/accounts/transfer")
    fun transferFunds(@RequestBody request: CreateTransactionRequest): ResponseEntity<*> {
        return try {
            transactionService.createTransaction(
                request.sourceAccountNumber,
                request.destinationAccountNumber,
                request.amount
            )
            ResponseEntity.ok("Transaction successful")
        } catch (e: InsufficientFundsException) {
            ResponseEntity.badRequest().body(e.message)
        }

    }
}

data class CreateTransactionRequest(
    val sourceAccountNumber: String,
    val destinationAccountNumber: String,
    val amount: BigDecimal

)