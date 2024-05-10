package ru.practicum.shareit.user.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName(String name);

    UserEntity findByEmail(String email);

    UserEntity findByNameAndEmail(String name, String email);

}
