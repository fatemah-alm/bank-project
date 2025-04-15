package com.example.bankProject.accounts

import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class AccountController(
    private val accountsService: AccountsService,

    ){


    @GetMapping("/accounts/v1/accounts")
    fun getAccounts(): List<Account> =  accountsService.listAccounts()

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: CreateAccountRequest) : Account =
        accountsService.createAccount(request.userId, request.name,request.balance)

    @PostMapping("/accounts/v1/accounts/{accountNumber}/close")
    fun closeAccount (@PathVariable accountNumber: String) = accountsService.closeAccount(accountNumber)


}

data class CreateAccountRequest(
    val userId: Long,
    val name: String,
    val balance: BigDecimal


)