package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateRoomRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.UpdateRoomRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.exception.RoomNotFoundException;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Room;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.HomeRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HomeRepository homeRepository;
    private final NotificationService notificationService;

    public RoomService(RoomRepository roomRepository, HomeRepository homeRepository, NotificationService notificationService) {
        this.roomRepository = roomRepository;
        this.homeRepository = homeRepository;
        this.notificationService = notificationService; // 👇 NUEVO
    }

    @Transactional
    public Room createRoom(CreateRoomRequest request) throws Exception {

        Home home = homeRepository.findById(request.getHomeId())
                .orElseThrow(() -> new Exception("Hogar no encontrado con el ID proporcionado"));

        Room room = new Room();
        room.setId(request.getId());
        room.setName(request.getName());
        room.setIcon(request.getIcon());
        room.setColorHex(request.getColorHex());
        room.setHome(home);

        Room savedRoom = roomRepository.save(room);

        notificationService.sendSilentSyncToHome(home, "SYNC_ROOMS", request.getUserId());

        return savedRoom;
    }

    @Transactional
    public Room updateRoom(String roomId, UpdateRoomRequest request) throws Exception {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        if (request.getName() != null) {
            room.setName(request.getName());
        }
        if (request.getIcon() != null) {
            room.setIcon(request.getIcon());
        }
        if (request.getColorHex() != null) {
            room.setColorHex(request.getColorHex());
        }

        Room updatedRoom = roomRepository.save(room);

        if (updatedRoom.getHome() != null) {
            notificationService.sendSilentSyncToHome(updatedRoom.getHome(), "SYNC_ROOMS", "");
        }

        return updatedRoom;
    }

    public List<Room> getRoomsByHome(String homeId) {
        return roomRepository.findByHomeId(homeId);
    }

    @Transactional
    public void deleteRoom(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        String homeId = room.getHome().getId();
        roomRepository.delete(room);
        homeRepository.findById(homeId).ifPresent(home ->
                notificationService.sendSilentSyncToHome(home, "SYNC_ROOMS", "")
        );
    }

}