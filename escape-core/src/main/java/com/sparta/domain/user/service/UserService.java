package com.sparta.domain.user.service;

import com.sparta.domain.user.dto.request.SignupRequestDto;
import com.sparta.domain.user.dto.response.SignupResponseDto;
import com.sparta.domain.user.entity.OAuthProvider;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserStatus;
import com.sparta.domain.user.entity.UserType;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원가입
     *
     * @param requestDto 회원가입 요청 데이터를 담은 Dto
     * @return 회원가입된 사용자의 정보를 담은 Dto
     * @throws UserException 유저 중복 예외처리
     */
    @Transactional
    public SignupResponseDto createUser(SignupRequestDto requestDto) {

        // 이메일로 유저 중복검사
        duplicateUserEmail(requestDto.getEmail());

        //암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getName(), requestDto.getEmail(), encodedPassword,
                OAuthProvider.ORIGIN, UserType.USER, UserStatus.DEACTIVE);
        // ORIGIN 일단 구현 -> 나중에 @kakao, @google 등으로 이메일 확인해서 swtich case로 구현 생각중
        userRepository.save(user);

        return new SignupResponseDto(user);
    }

    /**
     * useremail 유효성 검사
     */
    private void duplicateUserEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new UserException(UserErrorCode.USER_DUPLICATION);
        }
    }

    /**
     * 이메일 인증받은 유저 상태 업데이트
     */
    @Transactional
    public void updateUserActive(User user) {
        user.ActiveUser();
        userRepository.save(user);
    }
}