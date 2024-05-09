package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@Builder(toBuilder = true)
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Nullable private Long id;
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank private String name;
    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Email @NonNull private String email;
}