package ru.ibobrov.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.ibobrov.backend.model.dto.NewUserRequest;
import ru.ibobrov.backend.model.entity.User;
import ru.ibobrov.backend.security.UserDetailsImpl;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUser(NewUserRequest newUserRequest);

    UserDetailsImpl toUserDetails(User user);
}
