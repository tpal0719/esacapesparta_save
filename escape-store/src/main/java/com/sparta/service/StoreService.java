package com.sparta.service;

import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.entity.StoreStatus;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.dto.request.StoreRegisterRequestDto;
import com.sparta.dto.request.StoreModifyRequestDto;
import com.sparta.dto.response.StoreRegisterResponseDto;
import com.sparta.dto.response.StoresGetResponseDto;
import com.sparta.dto.response.StoreModifyResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional
    public StoreRegisterResponseDto registerStore(StoreRegisterRequestDto requestDto, User manager) {

        Store store = Store.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .phoneNumber(requestDto.getPhoneNumber())
                .workHours(requestDto.getWorkHours())
                .storeImage("temp")
                .manager(manager)
                .storeRegion(requestDto.getStoreRegion())
                .storeStatus(StoreStatus.PENDING)
                .build();

        storeRepository.save(store);
        return new StoreRegisterResponseDto(store);
    }

    public StoresGetResponseDto getMyStore(User manager) {
        List<Store> storeList = storeRepository.findAllByManagerId(manager.getId());
        return new StoresGetResponseDto(storeList);
    }

    @Transactional
    public StoreModifyResponseDto modifyStore(Long storeId, StoreModifyRequestDto requestDto, User manager) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);
        store.verifyStoreIsActive();
        store.checkManager(manager);

        store.updateStore(
                requestDto.getName(),
                requestDto.getAddress(),
                requestDto.getPhoneNumber(),
                requestDto.getWorkHours(),
                requestDto.getStoreRegion()
        );

        storeRepository.save(store);
        return new StoreModifyResponseDto(store);
    }

    @Transactional
    public void deleteStore(Long storeId, User manager) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);
        store.verifyStoreIsActive();
        store.checkManager(manager);
        store.deactivateStore();
    }

}