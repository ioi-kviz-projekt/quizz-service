package ioi.quizz.mappers;

import ioi.quizz.lib.Group;
import ioi.quizz.persistence.GroupEntity;

public class GroupMapper {
    
    public static Group fromEntity(GroupEntity entity) {
        Group group = BaseMapper.fromEntity(entity, Group.class);
        if (entity.getRoom() != null) {
            group.setRoomId(entity.getRoom().getId());
        }
        if (entity.getTheme() != null) {
            group.setTheme(ThemeMapper.fromEntity(entity.getTheme()));
            group.setThemeId(entity.getTheme().getId());
        }
        return group;
    }
    
}
