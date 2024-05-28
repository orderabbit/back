package com.example.back.service.implement;

import com.example.back.dto.request.auth.user.ChangePasswordRequestDto;
import com.example.back.dto.request.auth.user.PatchNicknameRequestDto;
import com.example.back.dto.request.auth.user.PatchProfileImageRequestDto;
import com.example.back.dto.response.ResponseDto;
import com.example.back.dto.response.user.*;
import com.example.back.entity.UserEntity;
import com.example.back.repository.BoardRepository;
import com.example.back.repository.CommentRepository;
import com.example.back.repository.FavoriteRepository;
import com.example.back.repository.UserRepository;
import com.example.back.service.EmailService;
import com.example.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImplement.class);

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String userId) {

        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetUserResponseDto.notExistUser();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId) {

        UserEntity userEntity = null;

        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String userId) {

        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) PatchNicknameResponseDto.notExistUser();

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname) return PatchNicknameResponseDto.duplicateNickname();

            userEntity.setNickname(nickname);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String userId) {

        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return PatchProfileImageResponseDto.notExistUser();

            String profileImage = dto.getProfileImage();
            userEntity.setProfileImage(profileImage);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchProfileImageResponseDto.success();
    }

    @Override
    public ResponseEntity<? super ChangePasswordResponseDto> changePassword(ChangePasswordRequestDto dto, String userId) {
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ChangePasswordResponseDto.notExistUser();

            String currentPassword = dto.getCurrentPassword();
            if (!passwordEncoder.matches(currentPassword, userEntity.getPassword()))
                return ChangePasswordResponseDto.wrongPassword();

            String newPassword = dto.getNewPassword();
            String hashedPassword = passwordEncoder.encode(newPassword);
            userEntity.setPassword(hashedPassword);
            userRepository.save(userEntity);

            log.info("User {} changed password successfully.", userId);
        } catch (Exception exception) {
            log.error("Error occurred while changing password for user {}.", userId, exception);
            return ResponseDto.databaseError();
        }
        return ChangePasswordResponseDto.success();
    }

    @Override
    public ResponseEntity<? super WithdrawalUserResponseDto> withdrawalUser(String userId) {
        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return WithdrawalUserResponseDto.notExistedUser();

//            if (!passwordEncoder.matches(password, userEntity.getPassword())) return WithdrawalUserResponseDto.wrongPassword();

            commentRepository.deleteByUserId(userId);
            favoriteRepository.deleteByUserId(userId);
            boardRepository.deleteByWriterId(userId);
            userRepository.delete(userEntity);
            log.info("User {} deleted successfully.", userId);
        } catch (Exception exception) {
            log.error("Error occurred while deleting user {}.", userId, exception);
            return ResponseDto.databaseError();
        }
        return WithdrawalUserResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PasswordRecoveryResponseDto> passwordRecovery(String email) {
        try {

            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return PasswordRecoveryResponseDto.notExistUser();

            String temporaryPassword = generateTemporaryPassword();

            userEntity.setPassword(temporaryPassword);
            userRepository.save(userEntity);

            String changePasswordUrl = "http://localhost:3000/password";
            String emailText = "Your temporary password is: " + temporaryPassword + ". Please visit " + changePasswordUrl + " to change your password.";

            emailService.sendEmail(email, "Temporary Password", emailText);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PasswordRecoveryResponseDto.success();
    }

    private String generateTemporaryPassword() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            passwordBuilder.append(randomChar);
        }
        return passwordBuilder.toString();
    }
}
