package com.example.bankProject.accounts

import com.example.bankProject.users.UserEntity
import com.example.bankProject.users.UsersRepository
import jakarta.inject.Named
import java.math.BigDecimal
import java.util.UUID

const val MAX_ACCOUNT_LIMIT = 5
@Named
class AccountsService(
    private val accountRepository: AccountRepository,
    private val usersRepository: UsersRepository,
) {

    fun listAccounts(): List<Account> = accountRepository.findAll().map {
        Account(
            user = it.user.id?: throw Exception(" Account was expecting user id not to be null..."),
            accountNumber = it.accountNumber,
            isActive = it.isActive,
            name = it.name,
            balance = it.balance
        )
    }


    fun createAccount(userId: Long, name: String, balance: BigDecimal) : Account{
        val user = usersRepository.findById(userId).get()
        val accounts = accountRepository.findByUserId(userId)

        if (accounts.size >= MAX_ACCOUNT_LIMIT)
        {

            throw MaxAccountsReachedException()
        }
        val newAccount = AccountEntity(user=user, name = name, isActive = true, balance = balance, accountNumber = UUID.randomUUID().toString())
        return accountRepository.save(newAccount).let {
            Account(
                user = it.user.id ?: throw Exception(" Account was expecting user id not to be null..."),
                isActive = it.isActive,
                accountNumber = it.accountNumber,
                name = it.name,
                balance = it.balance
            )
        }
    }


    fun closeAccount(accountNumber:String){
        val foundAccount = accountRepository.findByAccountNumber(accountNumber)?: throw IllegalArgumentException("Account not found")
        foundAccount.isActive=false
        accountRepository.save(foundAccount)

    }
}


data class Account(
    val user: Long,
    val isActive: Boolean,
    val accountNumber: String,
    val name: String,
    val balance: BigDecimal
)

class MaxAccountsReachedException(message: String = "You have reached your maximum accounts") : RuntimeException(message)