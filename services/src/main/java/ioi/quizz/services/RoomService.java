package ioi.quizz.services;

import ioi.quizz.lib.Room;
import ioi.quizz.lib.requests.RoomRegistrationRequest;
import ioi.quizz.persistence.RoomEntity;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    
    Room createRoom(String teacherId);
    
    Room registerUserToRoom(RoomRegistrationRequest request);
    
    Optional<RoomEntity> getRoomByNumber(String roomNumber);
    
    Optional<RoomEntity> getRoomEntity(String roomId);
    
    List<Room> getTeacherRooms(String teacherId);
    
}
