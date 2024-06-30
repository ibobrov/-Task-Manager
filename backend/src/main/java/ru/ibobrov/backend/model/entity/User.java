package ru.ibobrov.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import ru.ibobrov.backend.security.UserDetailsImpl;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "backend")
public class User {

    @Id
    private Long id;
    private String email;
    private String fullName;
    private String password;
    private Boolean confirmedRegistration;
    private Boolean active;
    private UserDetailsImpl.Role role;
}
