package ioi.quizz.services;

import ioi.quizz.lib.DiscoverableTheme;
import ioi.quizz.persistence.ThemeEntity;

import java.util.List;
import java.util.Optional;

public interface ThemeService {
    
    List<DiscoverableTheme> getThemes(String deviceId);
    
    void discoverTheme(String themeId, String deviceId);
    
    Optional<ThemeEntity> getThemeEntity(String themeId);
}
