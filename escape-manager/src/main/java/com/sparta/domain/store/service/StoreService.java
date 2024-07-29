package com.sparta.domain.store.service;

import com.sparta.domain.s3.S3Uploader;
import com.sparta.domain.store.dto.request.StoreModifyRequestDto;
import com.sparta.domain.store.dto.request.StoreRegisterRequestDto;
import com.sparta.domain.store.dto.response.StoreDetailResponseDto;
import com.sparta.domain.store.dto.response.StoreRegisterResponseDto;
import com.sparta.domain.store.dto.response.StoresGetResponseDto;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.entity.StoreStatus;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public StoreRegisterResponseDto registerStore(MultipartFile file, StoreRegisterRequestDto requestDto, User manager) {
        Store store = Store.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .workHours(requestDto.getWorkHours())
                .manager(manager)
                .storeRegion(requestDto.getStoreRegion())
                .storeStatus(StoreStatus.PENDING)
                .build();

        storeRepository.save(store);

        String storeImage = s3Uploader.uploadStoreImage(file, store.getId());
        store.updateStoreImage(storeImage);

        return new StoreRegisterResponseDto(store);
    }

    public StoresGetResponseDto getMyStore(User manager) {
        List<Store> storeList = storeRepository.findAllByManagerId(manager.getId());
        return new StoresGetResponseDto(storeList);
    }

    @Transactional
    public StoreDetailResponseDto modifyStore(Long storeId, StoreModifyRequestDto requestDto, User user) {
        Store store = storeRepository.findByActiveStore(storeId);
        store.checkManager(user);

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
    public String modifyStoreImage(Long storeId, MultipartFile file, User user) {
        Store store = storeRepository.findByActiveStore(storeId);
        store.checkManager(user);

        s3Uploader.deleteFileFromS3(store.getStoreImage());
        String storeImage = s3Uploader.uploadStoreImage(file, store.getId());
        store.updateStoreImage(storeImage);

        return storeImage;
    }

    @Transactional
    public void deleteStoreImage(Long storeId, User user) {
        Store store = storeRepository.findByActiveStore(storeId);
        store.checkManager(user);

        s3Uploader.deleteFileFromS3(store.getStoreImage());
        store.deleteStoreImage();
    }

    @Transactional
    public void deleteStore(Long storeId, User user) {
        Store store = storeRepository.findByActiveStore(storeId);
        store.checkManager(user);
        store.deactivateStore();
    }
}