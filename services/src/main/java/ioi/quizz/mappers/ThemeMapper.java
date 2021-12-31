package ioi.quizz.mappers;

import ioi.quizz.lib.DiscoverableTheme;
import ioi.quizz.lib.Theme;
import ioi.quizz.persistence.ThemeEntity;

public class ThemeMapper {
    
    public static Theme fromEntity(ThemeEntity entity) {
        Theme theme = BaseMapper.fromEntity(entity, Theme.class);
        theme.setTitle(entity.getTitle());
        theme.setPosition(entity.getPosition());
        theme.setArea(entity.getArea());
        theme.setLatitude(entity.getLatitude());
        theme.setLongitude(entity.getLongitude());
        return theme;
    }
    
    public static DiscoverableTheme fromEntityToDiscoverable(ThemeEntity entity, boolean discovered) {
        DiscoverableTheme theme = BaseMapper.fromEntity(entity, DiscoverableTheme.class);
        theme.setTitle(entity.getTitle());
        theme.setDiscovered(discovered);
        theme.setLatitude(entity.getLatitude());
        theme.setLongitude(entity.getLongitude());
        theme.setArea(entity.getArea());
        return theme;
    }
    
}
