package com.example.bankProject.accounts

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class AccountController(
    private val accountsService: AccountsService,

    ){


    @GetMapping("/accounts/v1/accounts")
    fun getAccounts(): List<Account> =  accountsService.listAccounts()

    @PostMapping("/accounts/v1/accounts")
    fun createAccount(@RequestBody request: CreateAccountRequest) : ResponseEntity<*> {
        return try {
            accountsService.createAccount(request.userId, request.name,request.balance)
            ResponseEntity.ok("Account created successfully")
        }
        catch (e: MaxAccountsReachedException){
            ResponseEntity.badRequest().body(e.message)
        }

    }

    @PostMapping("/accounts/v1/accounts/{accountNumber}/close")
    fun closeAccount (@PathVariable accountNumber: String) = accountsService.closeAccount(accountNumber)


}

data class CreateAccountRequest(
    val userId: Long,
    val name: String,
    val balance: BigDecimal


)