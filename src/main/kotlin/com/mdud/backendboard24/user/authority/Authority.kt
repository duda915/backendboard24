package com.mdud.backendboard24.user.authority

import javax.persistence.*

@Entity
@Table(name = "authority")
data class Authority (
        @Enumerated(EnumType.STRING)
        @Column(name = "authority_name")
        val authorityName: AuthorityName
) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null
}


