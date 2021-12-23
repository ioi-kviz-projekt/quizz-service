package ioi.quizz.services;

import ioi.quizz.lib.GroupWithMembers;
import ioi.quizz.persistence.GroupEntity;

import java.util.Optional;

public interface GroupService {
    
    void assignUserToGroup(String deviceId, String roomId);
    
    GroupWithMembers getUserGroup(String deviceId);
    
    Optional<GroupEntity> getGroupEntity(String id);
    
}
