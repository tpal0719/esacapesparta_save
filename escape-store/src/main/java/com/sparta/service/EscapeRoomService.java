package com.sparta.service;

import com.sparta.domain.escapeRoom.entity.EscapeRoom;
import com.sparta.domain.escapeRoom.entity.EscapeRoomStatus;
import com.sparta.domain.escapeRoom.repository.EscapeRoomRepository;
import com.sparta.domain.store.entity.Store;
import com.sparta.domain.store.repository.StoreRepository;
import com.sparta.domain.user.entity.User;
import com.sparta.dto.request.EscapeRoomCreateRequestDto;
import com.sparta.dto.request.EscapeRoomModifyRequestDto;
import com.sparta.dto.response.EscapeRoomDetailResponseDto;
import com.sparta.dto.response.EscapeRoomsGetResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EscapeRoomService {
    private final EscapeRoomRepository escapeRoomRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public EscapeRoomDetailResponseDto createEscapeRoom(EscapeRoomCreateRequestDto requestDto, User manager) {
        Store store = storeRepository.findByIdOrElseThrow(requestDto.getStoreId());
        store.checkManager(manager);

        EscapeRoom escapeRoom = EscapeRoom.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .level(requestDto.getLevel())
                .duration(requestDto.getDuration())
                .theme(requestDto.getTheme())
                .escapeRoomStatus(EscapeRoomStatus.ACTIVE)
                .store(store)
                .build();

        escapeRoomRepository.save(escapeRoom);
        return new EscapeRoomDetailResponseDto(escapeRoom);
    }

    public EscapeRoomsGetResponseDto getEscapeRooms(Long storeId, User manager) {
        Store findStore = storeRepository.findByIdOrElseThrow(storeId);
        findStore.checkManager(manager);

        List<EscapeRoom> escapeRoomList = escapeRoomRepository.findAllByStoreId(storeId);
        return new EscapeRoomsGetResponseDto(escapeRoomList);
    }

    @Transactional
    public EscapeRoomDetailResponseDto modifyEscapeRoom(Long themeId, EscapeRoomModifyRequestDto requestDto, User manager) {
        EscapeRoom escapeRoom = escapeRoomRepository.findByIdOrElseThrow(themeId);
        escapeRoom.verifyEscapeRoomIsActive();

        Store store = escapeRoom.getStore();
        store.verifyStoreIsActive();
        store.checkManager(manager);

        escapeRoom.updateEscapeRoom(
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getLevel(),
                requestDto.getDuration(),
                requestDto.getTheme(),
                requestDto.getPrice()
        );

        escapeRoomRepository.save(escapeRoom);
        return new EscapeRoomDetailResponseDto(escapeRoom);
    }

    @Transactional
    public void deleteEscapeRoom(Long themeId, User manager) {
        EscapeRoom escapeRoom = escapeRoomRepository.findByIdOrElseThrow(themeId);
        escapeRoom.verifyEscapeRoomIsActive();

        Store store = escapeRoom.getStore();
        store.verifyStoreIsActive();
        store.checkManager(manager);

        escapeRoom.deactivateEscapeRoom();
    }
}
