package com.example.bankProject.accounts

import com.example.bankProject.users.UserEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal

@Named
interface AccountRepository : JpaRepository<AccountEntity, Long>{
    fun findByAccountNumber(accountNumber: String): AccountEntity
    fun findByUserId(userId:Long):List<AccountEntity>
}


@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val user: UserEntity,

    val accountNumber: String,
    val name: String,
    var isActive: Boolean,
    var balance: BigDecimal



){
    constructor() : this(null, UserEntity(),"","",true,BigDecimal(0.01))
}