package com.example.bankProject.accounts

import com.example.bankProject.users.UserEntity
import com.example.bankProject.users.UsersRepository
import jakarta.inject.Named
import java.math.BigDecimal
import java.util.UUID

@Named
class AccountsService(
    private val accountRepository: AccountRepository,
    private val usersRepository: UsersRepository,
) {

    fun listAccounts(): List<Account> = accountRepository.findAll().map {
        Account(
            user = it.user,
            accountNumber = it.accountNumber,
            isActive = it.isActive,
            name = it.name,
            balance = it.balance
        )
    }


    fun createAccount(userId: Long, name: String, balance: BigDecimal){
        val user = usersRepository.findById(userId).get()
        val newAccount = AccountEntity(user=user, name = name, isActive = true, balance = balance, accountNumber = UUID.randomUUID().toString())
        accountRepository.save(newAccount)
    }


    fun closeAccount(accountNumber:String){
        val foundAccount = accountRepository.findByAccountNumber(accountNumber)?: throw IllegalArgumentException("Account not found")
        foundAccount.isActive=false
        accountRepository.save(foundAccount)

    }
}


data class Account(
    val user: UserEntity,
    val isActive: Boolean,
    val accountNumber: String,
    val name: String,
    val balance: BigDecimal

    )