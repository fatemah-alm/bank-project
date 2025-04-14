package com.example.bankProject.transactions

import com.example.bankProject.accounts.AccountEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal

@Named
interface TransactionRepository : JpaRepository<TransferEntity, Long>
@Entity
@Table(name = "transfers")
data class TransferEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,


    @ManyToOne
    val sourceAccountNumber: AccountEntity,

    @ManyToOne
    val destinationAccountNumber: AccountEntity,


    val amount: BigDecimal

//    @OneToMany(mappedBy = "order_id")
//    val items: List<ItemEntity>? = null


){
    constructor() : this(null,AccountEntity(), AccountEntity(),BigDecimal(0.01))


}