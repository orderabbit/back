package com.example.back.entity;

import com.example.back.dto.request.auth.SignUpRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
@Table(name="user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String userId;
    private String password;
    @Column(name = "email")
    private String email;
    private String nickname;
    private String profileImage;
    private String provider;
    private String type;
    private String role;

    public UserEntity(SignUpRequestDto dto){
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.nickname = dto.getNickname();
        this.type = "app";
        this.role = "ROLE_USER";
    }

    public UserEntity(String userId, String email, String type, String nickname, String profileImage){
        this.userId = userId;
        this.password = "Password";
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.type = type;
        this.role = "ROLE_USER";
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
}
