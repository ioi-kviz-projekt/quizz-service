package ioi.quizz.mappers;

import ioi.quizz.lib.Student;
import ioi.quizz.persistence.StudentEntity;

public class StudentMapper {
    
    public static Student fromEntity(StudentEntity entity) {
        Student student = BaseMapper.fromEntity(entity, Student.class);
        student.setFullName(entity.getFullName());
        return student;
    }
    
}
