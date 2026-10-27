package com.crudProject.crud.controller;

import com.crudProject.crud.dto.StudentRequestDto;
import com.crudProject.crud.dto.StudentResponseDto;
import com.crudProject.crud.entity.Student;
import com.crudProject.crud.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {



    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

       @PostMapping("/create")
    public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto student){
        StudentResponseDto student1 = studentService.createStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(student1);

    }

    @GetMapping("/getStudent/{id}")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable long id){
        StudentResponseDto student = studentService.getById(id);

        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(student);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents(){
        List<StudentResponseDto> s1 = studentService.getAll();
        if(s1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(s1);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
      boolean student=  studentService.delById(id);

        if(!student){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

  @PutMapping("/update")
    public ResponseEntity<StudentResponseDto> updateStudent(@RequestBody StudentRequestDto student,@PathVariable long id){
        StudentResponseDto student1 = studentService.updateStudent(student,id);

        if(student1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(student1);
  }


  @PatchMapping("/softDelete/{id}")
  public ResponseEntity<Student> softDeleteStudentById(@PathVariable long id){
      boolean student = studentService.softDelete(id);
      if(!student){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
