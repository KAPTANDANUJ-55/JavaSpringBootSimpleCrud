package com.crudProject.crud.aspect;

import com.crudProject.crud.dto.StudentRequestDto;
import com.crudProject.crud.dto.StudentResponseDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component

public class LoggingAspect {
      @Before("execution(* com.crudProject.crud..*.*(..))")
    public void LogBeforeMethod(JoinPoint joinPoint) {
          Object[] arr = joinPoint.getArgs();


          for (Object arg : arr) {
              if (arg instanceof StudentRequestDto dto) {
                  if(dto.getFirstName()!=null && dto.getLastName()!=null){
                      dto.setFirstName(dto.getFirstName().toUpperCase());
                  }
                  if(dto.getEmail()!=null){
                      dto.setEmail(dto.getEmail().toLowerCase().trim());
                  }
              }
          }
         System.out.println("Log Before Method");
    }

    @AfterReturning(pointcut = "execution(* com.crudProject.crud.service.StudentService.*(..))",
                   returning = "res"
            )
    public void AfterReturning(JoinPoint joinPoint, Object res) {
           if(res instanceof StudentResponseDto responseDto){
               (responseDto).setMessage("Welcome To Tahiti My Friend");

               if(responseDto.getEmail()!=null){
                   responseDto.setEmail("*****"+responseDto.getEmail().toLowerCase().trim());
               }
           }

        System.out.println("Log After Returning");
    }
        @Around("execution(* com.crudProject.crud.service.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
          long start = System.currentTimeMillis();
          Object res = joinPoint.proceed();
          long end = System.currentTimeMillis();
          long duration = end - start;

        System.out.println("Execution Time For : " + joinPoint.getSignature().getName() + " is " + duration + " ms");

        return res;
    }
}
