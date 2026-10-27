package com.crudProject.crud.service;

import com.crudProject.crud.dto.StudentRequestDto;
import com.crudProject.crud.dto.StudentResponseDto;

import com.crudProject.crud.entity.Student;
import com.crudProject.crud.exception.ResourceNotFound;
import com.crudProject.crud.repository.StudentRepo;

import io.github.kaptandanuj.anyexception_logger_spring_boot_starter.annotation.ExceptionLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepo studentRepo;
    private final JdbcTemplate jdbcTemplate;

    public StudentService(StudentRepo studentRepo, JdbcTemplate jdbcTemplate) {
        this.studentRepo = studentRepo;
        this.jdbcTemplate = jdbcTemplate;
    }
    @ExceptionLog(message = "GET_STUDENT_ERROR", printStackTrace = true)
    public  StudentResponseDto getById(long id) {
//        Student student = studentRepo.getById(id);
//        Student savedStudent = studentRepo.save(student);
//        return mapToResponse(savedStudent);

        return studentRepo.findById(id).map(this::mapToResponse).orElseThrow(()-> new ResourceNotFound("Student Not Found with id: "+id));
    }


    @Transactional
    public StudentResponseDto createStudent(StudentRequestDto studentReq) {
   Student student= mapToEntity(studentReq);
        Student savedStudent = studentRepo.save(student);
      return mapToResponse(savedStudent);

    }

    private StudentResponseDto mapToResponse(Student student) {


        StudentResponseDto studentResponseDto = new StudentResponseDto();
        String firstName = "";
        String lastName = "";

        if (student.getName() != null && !student.getName().trim().isEmpty()) {
            // limit = 2 rakhne se agar middle name bhi ho toh wo lastName ke sath safe rahega
            String[] parts = student.getName().trim().split("\\s+", 2);
            firstName = parts[0];
            if (parts.length > 1) {
                lastName = parts[1];
            }
        }
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setFirstName(firstName);
        studentResponseDto.setLastName(lastName);
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setUpdatedAt(student.getUpdatedAt());
        studentResponseDto.setCreatedAt(student.getCreatedAt());
        studentResponseDto.setMessage("Your Operation Is Successful");
        studentResponseDto.setId(student.getId());
        return studentResponseDto;
    }

    private Student mapToEntity(StudentRequestDto studentReq) {
    Student student = new Student();
    student.setEmail(studentReq.getEmail());
    student.setName(studentReq.getFirstName()+" "+studentReq.getLastName());
    student.setAge(studentReq.getAge());

           return student;
    }

    public List<StudentResponseDto> getAll(){
        return studentRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional
    public StudentResponseDto updateStudent(StudentRequestDto studentReq,long id) {
     Student existingStudent = studentRepo.findById(id).orElseThrow(()-> new ResourceNotFound("Cannot Update The Student Of Id : "+id));


     existingStudent.setName(studentReq.getFirstName()+" "+studentReq.getLastName());
     existingStudent.setAge(studentReq.getAge());
     existingStudent.setEmail(studentReq.getEmail());

     Student savedStudent = studentRepo.save(existingStudent);
     return mapToResponse(savedStudent);

    }

    @Transactional
    public boolean delById(long id) {
      if(!studentRepo.existsById(id)){
         throw new ResourceNotFound("Student Not Found with id: "+id);
      }
     studentRepo.hardDeleteById(id);
        return true;

    }



    @Transactional
    public boolean softDelete(long id){
        if(!studentRepo.existsById(id)){
            return false;
        }
        studentRepo.deleteById(id);

        return true;

    }
}
