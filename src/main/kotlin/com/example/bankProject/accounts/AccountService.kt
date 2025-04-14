package com.example.bankProject.accounts

import com.example.bankProject.users.UserEntity
import com.example.bankProject.users.UsersRepository
import jakarta.inject.Named
import java.math.BigDecimal

@Named
class AccountsService(
    private val accountRepository: AccountRepository,
    private val usersRepository: UsersRepository,
) {

    fun listAccounts(): List<Account> = accountRepository.findAll().map {
        Account(
            user = it.user,
            accountNumber = it.accountNumber,
            name = it.name,
            initialBalance = it.initialBalance
        )
    }


    fun createAccount(userId: Long, name: String, initialBalance: BigDecimal){
        val user = usersRepository.findById(userId).get()
        val newAccount = AccountEntity(user=user, name = name, initialBalance = initialBalance, accountNumber = (1..10).random().toString())
        accountRepository.save(newAccount)
    }


    fun closeAccount(accountNumber:String){
        val foundAccount = accountRepository.findByAccountNumber(accountNumber)?: throw IllegalArgumentException("Account not found")
        accountRepository.delete(foundAccount)

    }
}


data class Account(
    val user: UserEntity,
    val accountNumber: String,
    val name: String,
    val initialBalance: BigDecimal

    )