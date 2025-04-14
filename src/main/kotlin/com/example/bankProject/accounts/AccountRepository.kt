package com.example.bankProject.accounts

import com.example.bankProject.users.UserEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal

@Named
interface AccountRepository : JpaRepository<AccountEntity, Long>{
    fun findByAccountNumber(accountNumber: String): AccountEntity
}


@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val user: UserEntity,

    val account_number: String,

    val name: String,

    val initial_balance: BigDecimal

//    @OneToMany(mappedBy = "order_id")
//    val items: List<ItemEntity>? = null


){
    constructor() : this(null, UserEntity(),"","",BigDecimal(0.01))
}