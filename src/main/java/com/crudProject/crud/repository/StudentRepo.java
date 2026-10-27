package com.crudProject.crud.repository;

import com.crudProject.crud.dto.StudentRequestDto;
import com.crudProject.crud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM student WHERE id = :id", nativeQuery = true)
    void hardDeleteById(@Param("id") Long id);
}
