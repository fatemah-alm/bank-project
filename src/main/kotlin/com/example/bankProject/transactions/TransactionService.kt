package com.example.bankProject.transactions

import com.example.bankProject.accounts.AccountEntity
import com.example.bankProject.accounts.AccountRepository
import jakarta.inject.Named
import java.math.BigDecimal

@Named
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
) {

    fun listTransactions(): List<Transaction> = transactionRepository.findAll().map {
        Transaction(
            sourceAccountNumber = it.sourceAccountNumber,
            destinationAccountNumber = it.destinationAccountNumber,
            amount = it.amount,
        )
    }


    fun createTransaction(sourceAccountNumber: String, destinationAccountNumber:String, amount: BigDecimal){
        val srcAccount = accountRepository.findByAccountNumber(sourceAccountNumber)
        val destAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
        val newTransaction = TransferEntity(sourceAccountNumber = srcAccount,  destinationAccountNumber= destAccount, amount=amount)

        if(srcAccount.balance < amount){
            throw InsufficientFundsException()
        }
            srcAccount.balance -= amount
            accountRepository.save(srcAccount)
            destAccount.balance += amount
            accountRepository.save(destAccount)
            transactionRepository.save(newTransaction)

    }

}


data class Transaction(
    val sourceAccountNumber: AccountEntity,
    val destinationAccountNumber: AccountEntity,
    val amount: BigDecimal,
)

class InsufficientFundsException(message: String = "Insufficient Funds!") : RuntimeException(message)