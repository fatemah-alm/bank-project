package com.example.bankProject.transactions

import com.example.bankProject.accounts.Account
import com.example.bankProject.accounts.AccountsService


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class TransactionController(
    private val transactionService: TransactionService,

    ){


    @GetMapping("/accounts/v1/accounts")
    fun getAccounts(): List<Account> = accountsService.listAccounts()

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: CreateAccountRequest) = accountsService.createAccount(request.userId, request.name,request.initial_balance)

}

data class CreateAccountRequest(
    val userId: Long,
    val name: String,
    val initial_balance: BigDecimal


)