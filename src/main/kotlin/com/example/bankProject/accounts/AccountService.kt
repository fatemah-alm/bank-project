package com.example.bankProject.accounts

import com.example.bankProject.users.UserEntity
import com.example.bankProject.users.UsersRepository
import jakarta.inject.Named

@Named
class AccountsService(
    private val accountRepository: AccountRepository,
    private val usersRepository: UsersRepository,
) {

    fun listAccounts(): List<Account> = accountRepository.findAll().map {
        Account(
            user = it.user,
            account_number = it.account_number,
            name = it.name,
            initial_balance = it.initial_balance
        )
    }


    fun createAccount(userId: Long, name: String, initial_balance: Int){
        val user = usersRepository.findById(userId).get()
        val newAccount = AccountEntity(user=user, name = name, initial_balance = initial_balance, account_number = (1..10).random().toString())
        accountRepository.save(newAccount)
    }
}


data class Account(
    val user: UserEntity,
    val account_number: String,
    val name: String,
    val initial_balance: Int

    )