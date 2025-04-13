package com.example.bankProject.accounts

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OnlineOrderController(
    private val accountsService: AccountsService,

    ){


    @GetMapping("/accounts/v1/accounts")
    fun getAccounts(): List<Account> = accountsService.listAccounts()

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: CreateAccountRequest) = accountsService.createAccount(request.userId, request.name,request.initial_balance)

}

data class CreateAccountRequest(
    val userId: Long,
    val name: String,
    val initial_balance: Int


)