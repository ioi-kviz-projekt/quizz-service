package ioi.quizz.services;

import ioi.quizz.lib.Student;
import ioi.quizz.lib.responses.CheckUserResponse;
import ioi.quizz.persistence.StudentEntity;

import java.util.Optional;

public interface StudentService {
    
    Student getStudentByDevice(String deviceId);
    
    Optional<StudentEntity> getStudentEntityByDevice(String deviceId);
    
    Student getStudent(String id);
    
    Optional<StudentEntity> getStudentEntity(String id);
    
    StudentEntity register(String deviceId, String fullName);
    
    CheckUserResponse checkUser(String deviceId);
    
}
