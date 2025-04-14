package com.example.bankProject.kyc

import com.example.bankProject.users.UserEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal
import java.util.*

@Named
interface KycRepository : JpaRepository<KycEntity, Long>{
}


@Entity
@Table(name = "kyc")
data class KycEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    val user: UserEntity,

    val firstName: String,
    val lastName: String,
    var dataOfBirth: Date,
    var salary: BigDecimal



){
    constructor() : this(null, UserEntity(),"","",Date(),BigDecimal(0.01))
}