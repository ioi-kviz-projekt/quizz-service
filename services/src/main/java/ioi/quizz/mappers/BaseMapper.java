package ioi.quizz.mappers;

import ioi.quizz.lib.BaseType;
import ioi.quizz.persistence.BaseEntity;

import java.util.UUID;

public class BaseMapper {
    
    private BaseMapper() {
    
    }
    
    public static <T extends BaseType, E extends BaseEntity> T fromEntity(E entity, Class<T> typeClass) {
        try {
            T instance = typeClass.getDeclaredConstructor().newInstance();
            return fromEntity(entity, instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static <T extends BaseType, E extends BaseEntity> T fromEntity(E entity, T type) {
        type.setId(UUID.fromString(entity.getId()));
        if (entity.getCreatedAt() != null) {
            type.setCreatedAt(entity.getCreatedAt().toInstant());
        }
        if (entity.getUpdatedAt() != null) {
            type.setUpdatedAt(entity.getUpdatedAt().toInstant());
        }
        return type;
    }
    
}
