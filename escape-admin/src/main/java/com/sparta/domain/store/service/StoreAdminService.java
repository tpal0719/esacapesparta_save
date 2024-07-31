package com.sparta.domain.store.service;

import com.sparta.domain.s3.S3Uploader;
import com.sparta.domain.store.dto.request.StoreCreateRequestDto;
import com.sparta.domain.store.dto.request.StoreModifyRequestDto;
import com.sparta.domain.store.dto.response.StoreDetailResponseDto;
import com.sparta.domain.store.dto.response.StoreResponseDto;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.entity.StoreStatus;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.repository.UserRepository;
import com.sparta.global.exception.customException.StoreException;
import com.sparta.global.exception.errorCode.StoreErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreAdminService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    /**
     * TODO : 방탈출 카페 강제 등록 for Admin
     *
     * @param requestDto
     * @return StoreResponseDto : 방탈출 카페 정보
     * @author SEMI
     */
    @Transactional
    public StoreDetailResponseDto createStoreByAdmin(MultipartFile file, StoreCreateRequestDto requestDto) {

        User manager = userRepository.findByIdOrElseThrow(requestDto.getManagerId());

        Store store = Store.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .workHours(requestDto.getWorkHours())
                .storeRegion(requestDto.getStoreRegion())
                .manager(manager)
                .storeStatus(StoreStatus.ACTIVE)
                .build();

        storeRepository.save(store);
        String storeImage = s3Uploader.uploadStoreImage(file, store.getId());
        store.updateStoreImage(storeImage);

        return new StoreDetailResponseDto(store);
    }

    /**
     * TODO : 모든 방탈출 카페 조회 (모든상태: 대기중,활성화,비활성화 ) for Admin
     *
     * @return List<StoreResponseDto> 모든 방탈출카페
     * @author SEMI
     */
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllStore() {
        List<Store> stores = storeRepository.findAll();

        return stores.stream()
                .map(StoreResponseDto::new)
                .collect(Collectors.toList());
    }


    /**
     * TODO : 방탈출 카페 등록 승인 ( PENDING -> ACTIVE ) for Admin
     *
     * @param storeId
     * @author SEMI
     */
    @Transactional
    public void approveStore(Long storeId) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        if (store.getStoreStatus() == StoreStatus.PENDING) {
            store.setStoreStatus(StoreStatus.ACTIVE);
        } else {
            throw new StoreException(StoreErrorCode.STORE_ALREADY_EXIST);
        }
    }

    @Transactional
    public StoreDetailResponseDto modifyStore(Long storeId, StoreModifyRequestDto requestDto) {
        Store store = storeRepository.findByActiveStore(storeId);

        store.updateStore(
                requestDto.getName(),
                requestDto.getAddress(),
                requestDto.getPhoneNumber(),
                requestDto.getWorkHours(),
                requestDto.getStoreRegion()
        );

        storeRepository.save(store);
        return new StoreDetailResponseDto(store);
    }

    @Transactional
    public String modifyStoreImage(Long storeId, MultipartFile file) {
        Store store = storeRepository.findByActiveStore(storeId);
        s3Uploader.deleteFileFromS3(store.getStoreImage());
        String storeImage = s3Uploader.uploadStoreImage(file, store.getId());
        store.updateStoreImage(storeImage);

        return storeImage;
    }

    @Transactional
    public void deleteStoreImage(Long storeId) {
        Store store = storeRepository.findByActiveStore(storeId);
        s3Uploader.deleteFileFromS3(store.getStoreImage());
        store.deleteStoreImage();
    }

    @Transactional
    public void deactivateStore(Long storeId) {
        Store store = storeRepository.findByActiveStore(storeId);
        store.deactivateStore();
    }

    /**
     * TODO : 방탈출 카페 완전 삭제 for Admin
     *
     * @param storeId
     * @author SEMI
     */
    @Transactional
    public void deleteStore(Long storeId) {
        //validateAuthority(user);
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        storeRepository.delete(store);
    }

    /**
     * TODO : Admin 또는 Manager가 방탈출 카페 활성화 ( DEACTIVE -> ACTIVE )
     *
     * @param storeId
     * @author SEMI
     */
    @Transactional
    public void activeStore(Long storeId) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        store.setStoreStatus(StoreStatus.ACTIVE);
    }

    /**
     * TODO : Admin 또는 Manager가 방탈출 카페 비활성화 ( ACTIVE -> DEACTIVE )
     *
     * @param storeId
     * @author SEMI
     */
    @Transactional
    public void deactiveStore(Long storeId) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        store.setStoreStatus(StoreStatus.DEACTIVE);
    }

}
