package com.haptikos.gestor_tareas_haptikos_servidor.service;

import com.haptikos.gestor_tareas_haptikos_servidor.dto.CreateRoomRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.dto.UpdateRoomRequest;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Home;
import com.haptikos.gestor_tareas_haptikos_servidor.model.Room;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.HomeRepository;
import com.haptikos.gestor_tareas_haptikos_servidor.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HomeRepository homeRepository;

    public RoomService(RoomRepository roomRepository, HomeRepository homeRepository) {
        this.roomRepository = roomRepository;
        this.homeRepository = homeRepository;
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

        return roomRepository.save(room);
    }

    @Transactional
    public Room updateRoom(String roomId, UpdateRoomRequest request) throws Exception {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new Exception("Habitación no encontrada"));

        if (request.getName() != null) {
            room.setName(request.getName());
        }
        if (request.getIcon() != null) {
            room.setIcon(request.getIcon());
        }
        if (request.getColorHex() != null) {
            room.setColorHex(request.getColorHex());
        }

        return roomRepository.save(room);
    }
}