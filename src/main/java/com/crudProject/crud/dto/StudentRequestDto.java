package com.crudProject.crud.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDto {
   private String email;
   private String firstName;
    private String lastName;
    private Integer age;
   private enum  Gender {
        MALE, FEMALE, OTHER
    }

}
