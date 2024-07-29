package com.sparta.domain.user.service;

import com.sparta.config.EmailConfig;
import com.sparta.domain.user.dto.request.SignupRequestDto;
import com.sparta.domain.user.dto.request.WithdrawRequestDto;
import com.sparta.domain.user.dto.response.SignupResponseDto;
import com.sparta.domain.user.entity.OAuthProvider;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserStatus;
import com.sparta.domain.user.entity.UserType;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.UserErrorCode;
import com.sparta.jwt.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final EmailConfig emailConfig;


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

        //회원가입시 기본 권한을 User로 설정
        UserType role = UserType.USER;

        if (requestDto.getInviteCode() != null) {
            role = emailService.validateInviteCode(requestDto.getInviteCode());
        }

        User user = new User(requestDto.getName(), requestDto.getEmail(), encodedPassword,
                OAuthProvider.ORIGIN, role, UserStatus.DEACTIVE);
        // ORIGIN 일단 구현 -> 나중에 @kakao, @google 등으로 이메일 확인해서 swtich case로 구현 생각중
        userRepository.save(user);

        return new SignupResponseDto(user);
    }

    // TODO : 이메일 중복 검사
    private void duplicateUserEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new UserException(UserErrorCode.USER_DUPLICATION);
        }
    }

    @Transactional
    public String createInviteCode(UserType role) {
        String code = emailService.createInviteCode(role);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(3);

        User user = new User();
        user.sendInviteCode(code, expirationTime);
        userRepository.save(user);

        String inviteEmail = emailConfig.getInviteEmail();
        emailService.sendInviteCode(inviteEmail, code);

        return code;
    }


    // TODO : 이메일 인증받은 유저 상태 업데이트
    @Transactional
    public void updateUserActive(User user) {

        user.activeUser();
        userRepository.save(user);
    }

    // TODO : 로그아웃 전 사용자 조회
    @Transactional(readOnly = true)
    public Long logout(Long userId) {

        User user = userRepository.findByUserId(userId);
        refreshTokenService.deleteToken(user.getEmail());

        return user.getId();
    }

    // TODO : 회원 탈퇴
    @Transactional
    public Long withdraw(WithdrawRequestDto requestDto, Long userId) {

        User user = userRepository.findByUserId(userId);

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UserException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        user.changeStatus(UserStatus.WITHDRAW);
        userRepository.save(user);

        return user.getId();
    }


}