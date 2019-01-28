package com.mdud.backendboard24.user

import com.mdud.backendboard24.user.authority.Authority
import javax.persistence.*

data class UserAuthorities (
        @ManyToOne(cascade = [CascadeType.MERGE])
        @JoinColumn(name = "authority_id")
        val authority: Authority
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}