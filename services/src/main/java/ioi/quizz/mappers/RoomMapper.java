package ioi.quizz.mappers;

import ioi.quizz.lib.Room;
import ioi.quizz.persistence.RoomEntity;

public class RoomMapper {
    
    public static Room fromEntity(RoomEntity entity) {
        Room room = BaseMapper.fromEntity(entity, Room.class);
        room.setRoomNumber(entity.getRoomNumber());
        room.setAdminId(entity.getAdminId());
        return room;
    }
    
}
