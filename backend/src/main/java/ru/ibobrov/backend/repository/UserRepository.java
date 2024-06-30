package ru.ibobrov.backend.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibobrov.backend.model.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(@NotNull String email);

    @Modifying
    @Query("""
            update fond_igra.users
            set full_name = :fullName, email = :email
            where id = :id
            """)
    void updateNameAndEmail(@NotNull Long id, @NotNull String fullName, @NotNull String email);

    @Modifying
    @Query("""
            update fond_igra.users
            set active = :active
            where id = :id
            """)
    void updateActiveStatus(@NotNull Long id, @NotNull Boolean active);
}
