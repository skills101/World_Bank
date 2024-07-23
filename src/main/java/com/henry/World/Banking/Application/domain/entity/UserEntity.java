package com.henry.World.Banking.Application.domain.entity;

import com.henry.World.Banking.Application.domain.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends BaseClass{

    private String firstName;

    private String lastName;

    private String otherName;

    private String email;

    private String password;

    private String gender;

    private String address;

    private String stateOfOrigin;

    private String phoneNumber;

    private BigDecimal accountBalance;

    private String BVN;

    private String pin;

    private String accountNumber;

    private String bankName;

    private String profilePicture;

    private String status;

    private Role role;
}
