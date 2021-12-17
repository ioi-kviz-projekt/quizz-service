package ioi.quizz.mappers;

import ioi.quizz.lib.Theme;
import ioi.quizz.persistence.ThemeEntity;

public class ThemeMapper {
    
    public static Theme fromEntity(ThemeEntity entity) {
        Theme theme = BaseMapper.fromEntity(entity, Theme.class);
        theme.setTitle(entity.getTitle());
        theme.setPosition(entity.getPosition());
        return theme;
    }
    
}
