# SFSCourse Server

## Based on:
- `SpringBoot` `JPA` `Spring Security`
- `MongoDB`
- `MySQL`
- `JWT`

## Structure:
```
.
├── java
│   ├── com
│   │   └── dds
│   │       └── sfscourse
│   │           ├── base
│   │           │   ├── AuditorAwareImpl.java
│   │           │   ├── ExceptionHandle.java
│   │           │   ├── ResultBean.java
│   │           │   ├── ResultEnum.java
│   │           │   └── ResultHandler.java
│   │           ├── config
│   │           │   ├── AuditorAwareConfig.java
│   │           │   ├── StaticConfig.java
│   │           │   ├── SwaggerConfig.java
│   │           │   └── WebSecurityConfig.java
│   │           ├── controller
│   │           │   ├── AdminController.java
│   │           │   ├── AdminCourseController.java
│   │           │   ├── CourseController.java
│   │           │   ├── CoursewareController.java
│   │           │   ├── HomeworkController.java
│   │           │   ├── HomeworkSubmitController.java
│   │           │   ├── InstituteController.java
│   │           │   ├── NotificationController.java
│   │           │   └── StudentController.java
│   │           ├── dto
│   │           │   ├── HomeworkDto.java
│   │           │   ├── StudentCommitDto.java
│   │           │   └── StudentCommitInterface.java
│   │           ├── entity
│   │           │   ├── AbstracEntity.java
│   │           │   ├── AdminCourse.java
│   │           │   ├── Admin.java
│   │           │   ├── AdminRole.java
│   │           │   ├── Course.java
│   │           │   ├── Courseware.java
│   │           │   ├── Homework.java
│   │           │   ├── HomeworkSubmit.java
│   │           │   ├── Institute.java
│   │           │   ├── Notification.java
│   │           │   ├── Role.java
│   │           │   ├── StudentCourse.java
│   │           │   └── Student.java
│   │           ├── Exception
│   │           │   ├── BaseException.java
│   │           │   ├── ForbiddenException.java
│   │           │   ├── NullAttrException.java
│   │           │   ├── ResourceExistException.java
│   │           │   └── ResourceNotFoundException.java
│   │           ├── repo
│   │           │   ├── AdminCourseRepo.java
│   │           │   ├── AdminRepo.java
│   │           │   ├── AdminRoleRepo.java
│   │           │   ├── CourseRepo.java
│   │           │   ├── CoursewareRepo.java
│   │           │   ├── HomeworkRepo.java
│   │           │   ├── HomeworkSubmitRepo.java
│   │           │   ├── InstituteRepo.java
│   │           │   ├── NotificationRepo.java
│   │           │   ├── RoleRepo.java
│   │           │   ├── StudentCourseRepo.java
│   │           │   └── StudentRepo.java
│   │           ├── security
│   │           │   ├── GrantedAuthorityImpl.java
│   │           │   ├── JwtAuthenticationFilter.java
│   │           │   ├── JwtAuthenticationProvider.java
│   │           │   ├── JwtAuthenticationToken.java
│   │           │   ├── JwtUserDetails.java
│   │           │   └── UserDetailsServiceImpl.java
│   │           ├── service
│   │           │   └── MongoDBService.java
│   │           ├── SfscourseApplication.java
│   │           ├── util
│   │           │   ├── JwtTokenUtils.java
│   │           │   ├── MediaTypeUtils.java
│   │           │   ├── PasswordEncoder.java
│   │           │   ├── SecurityUtils.java
│   │           │   └── TokenClearLogoutHandler.java
│   │           └── vo
│   │               ├── LoginBean.java
│   │               └── TokenVo.java
│   └── META-INF
│       └── MANIFEST.MF
└── resources
    ├── application.properties
    ├── static
    └── templates
```
