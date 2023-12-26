package com.assignment.cabservice.model;

import com.assignment.cabservice.audit.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class User extends Auditable<String> {
    @Id
    private String userId;
    @Indexed(unique = true)
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

//    private LocalDateTime createdAt;
//    private LocalDateTime lastLogin;
}
