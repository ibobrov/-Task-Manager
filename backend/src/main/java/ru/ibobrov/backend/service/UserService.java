package ru.ibobrov.backend.service;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ibobrov.backend.exception.UserExistException;
import ru.ibobrov.backend.mapper.UserMapper;
import ru.ibobrov.backend.model.dto.ConfirmRegistrationRequest;
import ru.ibobrov.backend.model.dto.NewUserRequest;
import ru.ibobrov.backend.model.dto.UpdateUserRequest;
import ru.ibobrov.backend.model.entity.User;
import ru.ibobrov.backend.repository.UserRepository;
import ru.ibobrov.backend.security.TokenService;
import ru.ibobrov.backend.security.UserDetailsImpl;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final TokenService tokenService;
    private final JavaMailSender emailSender;
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final static String REGISTRATION_SUBJECT = "Регистрация в приложении";
    private final static String REGISTRATION_TEXT = "Подтвердите регистрацию перейдя по ссылке ... Токен ";

    @Transactional
    public void create(@NotNull NewUserRequest request) {
        final User newUser = mapper.toUser(request);
        newUser.setPassword(" ");
        newUser.setActive(true);
        newUser.setConfirmedRegistration(false);

        if (repository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserExistException("Email уже используется");
        }

        repository.save(newUser);
        sendRegistrationLetter(newUser.getEmail());
    }

    @Transactional
    public void confirmRegistration(@NotNull ConfirmRegistrationRequest request) {
        final String username = tokenService.extractUsername(request.getToken());
        final User user = findByEmail(username);

        if (user.getActive() && tokenService.isValid(request.getToken(), username)) {
            user.setConfirmedRegistration(true);
            user.setPassword(encoder.encode(request.getPassword()));

            repository.save(user);
        }
    }

    @Transactional
    public void update(@NotNull Long id, @NotNull UpdateUserRequest updateUserRequest) {
        repository.updateNameAndEmail(id, updateUserRequest.getFullName(), updateUserRequest.getEmail());
    }

    @Transactional
    public void setActiveStatus(@NotNull Long id, @NotNull Boolean active) {
        repository.updateActiveStatus(id, active);
    }

    @NotNull
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        return mapper.toUserDetails(findByEmail(username));
    }

    @NotNull
    public UserDetailsImpl.Role[] getAvailableRoles() {
        return UserDetailsImpl.Role.values();
    }

    @NotNull
    private User findByEmail(@NotNull String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Неверные учетные данные пользователя"));
    }

    private void sendRegistrationLetter(@NotNull String to) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(REGISTRATION_SUBJECT);
        message.setText(REGISTRATION_TEXT + tokenService.generateToken(to));

        emailSender.send(message);
    }
}
