package com.example.back.service.implement;

import com.example.back.dto.request.auth.user.PatchNicknameRequestDto;
import com.example.back.dto.request.auth.user.PatchProfileImageRequestDto;
import com.example.back.dto.response.ResponseDto;
import com.example.back.dto.response.user.GetSignInUserResponseDto;
import com.example.back.dto.response.user.GetUserResponseDto;
import com.example.back.dto.response.user.PatchNicknameResponseDto;
import com.example.back.dto.response.user.PatchProfileImageResponseDto;
import com.example.back.entity.UserEntity;
import com.example.back.repository.UserRepository;
import com.example.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String userId) {

        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return GetUserResponseDto.notExistUser();

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String userId) {

        UserEntity userEntity = null;

        try{
            userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return GetSignInUserResponseDto.notExistUser();

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String userId) {

        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) PatchNicknameResponseDto.notExistUser();

            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if(existedNickname) return PatchNicknameResponseDto.duplicateNickname();

            userEntity.setNickname(nickname);
            userRepository.save(userEntity);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchNicknameResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchProfileImageResponseDto> patchProfileImage(PatchProfileImageRequestDto dto, String userId) {

        try {
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return PatchProfileImageResponseDto.notExistUser();

            String profileImage = dto.getProfileImage();
            userEntity.setProfileImage(profileImage);
            userRepository.save(userEntity);

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PatchProfileImageResponseDto.success();
    }
}
