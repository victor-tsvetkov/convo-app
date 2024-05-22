package viktor.tsvetkov.conversations.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.repositories.UserRepository;
import viktor.tsvetkov.conversations.security.dto.AuthenticationRequest;
import viktor.tsvetkov.conversations.security.dto.AuthenticationResponse;
import viktor.tsvetkov.conversations.security.dto.RegisterRequest;
import viktor.tsvetkov.conversations.security.jwt.JwtService;
import viktor.tsvetkov.conversations.services.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

    public AuthenticationResponse register(RegisterRequest request) {
        UserDto userDto = new UserDto(null, request.name(), request.sex(),
                request.username(), passwordEncoder.encode(request.password()));
        User user = userService.save(userDto);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userRepository.findUserByUsername(request.username())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).user(user).build();
    }

}
