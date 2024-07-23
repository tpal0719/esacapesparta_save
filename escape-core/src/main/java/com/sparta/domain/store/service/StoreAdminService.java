package com.sparta.domain.store.service;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import com.sparta.domain.escapeRoom.entity.EscapeRoomStatus;
import com.sparta.domain.store.dto.StoreCreateRequestDto;
import com.sparta.domain.store.dto.StoreResponseDto;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.entity.StoreStatus;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.EscapeRoomException;
import com.sparta.global.exception.customException.StoreException;
import com.sparta.global.exception.customException.UserException;
import com.sparta.global.exception.errorCode.StoreErrorCode;
import com.sparta.global.exception.errorCode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreAdminService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * TODO : 방탈출 카페 강제 등록 for Admin
     *
     * @param requestDto
     * @param user
     * @author SEMI
     */
    @Transactional
    public StoreResponseDto createStoreByAdmin(StoreCreateRequestDto requestDto, User user) {
        validateAuthority(user);

        User manager = userRepository.findByIdOrElseThrow(requestDto.getManagerId());

        Store store = Store.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .workHours(requestDto.getWorkHours())
                .storeImage(requestDto.getStoreImage())
                .manager(manager)
                .storeStatus(StoreStatus.ACTIVE) //강제 주입
                .build();

        storeRepository.save(store);

        return new StoreResponseDto(store);
    }

    /**
     * TODO : 모든 방탈출 카페 조회 (모든상태: 대기중,활성화,비활성화 ) for Admin
     *
     * @param user
     * @author SEMI
     */
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllStore(User user) {
        validateAuthority(user);
        List<Store> stores = storeRepository.findAll();

        return stores.stream()
                .map(store -> new StoreResponseDto(store))
                .collect(Collectors.toList());
    }



    /**
     * TODO : 방탈출 카페 등록 승인 ( PENDING -> ACTIVE ) for Admin
     *
     * @param storeId
     * @param user
     * @author SEMI
     */
    @Transactional
    public void approveStore(Long storeId, User user) {
        validateAuthority(user);
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        if (store.getStoreStatus().equals(StoreStatus.PENDING)) {
            store.setStoreStatus(StoreStatus.ACTIVE);
        } else {
            throw new StoreException(StoreErrorCode.STORE_ALREADY_EXIST);
        }
    }


    /**
     * TODO : Admin 또는 Manager가 방탈출 카페 활성화 ( DEACTIVE -> ACTIVE )
     *
     * @param storeId
     * @param user
     * @author SEMI
     */
    @Transactional
    public void activeStore(Long storeId, User user) {
        validateAuthority(user);
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        store.setStoreStatus(StoreStatus.ACTIVE);
    }

    /**
     * TODO : Admin 또는 Manager가 방탈출 카페 비활성화 ( ACTIVE -> DEACTIVE )
     *
     * @param storeId
     * @param user
     * @author SEMI
     */
    @Transactional
    public void deactiveStore(Long storeId, User user) {
        validateAuthority(user);
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        store.setStoreStatus(StoreStatus.DEACTIVE);
    }

    /**
     * TODO : 방탈출 카페 완전 삭제 for Admin
     *
     * @param storeId
     * @param user
     * @author SEMI
     */
    @Transactional
    public void deleteStore(Long storeId, User user) {
        validateAuthority(user);
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        storeRepository.delete(store);
    }



    /* Utils */


    /**
     * TODO : 권한 확인
     *
     * @param user
     * @author SEMI
     */
    public void validateAuthority(User user) {
        // admin 이용자
        if (user.getUserType() == UserType.ADMIN) {
            return;
        } else {
            throw new UserException(UserErrorCode.USER_NOT_AUTHORITY);
        }
    }


}
