package projectscope.com.scope.service;

import com.mysema.commons.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectscope.com.scope.dto.request.AuthRequest;
import projectscope.com.scope.dto.request.UserRequest;
import projectscope.com.scope.dto.response.AuthResponse;
import projectscope.com.scope.dto.response.UserResponse;
import projectscope.com.scope.entity.FileEntity;
import projectscope.com.scope.entity.User;
import projectscope.com.scope.entity.UserType;
import projectscope.com.scope.exception.UserExistsException;
import projectscope.com.scope.exception.UserNotFoundException;
import projectscope.com.scope.mapper.UserMapper;
import projectscope.com.scope.repository.UserRepository;
import projectscope.com.scope.security.service.SecurityService;
import projectscope.com.scope.security.util.JwtUtil;
import projectscope.com.scope.utils.FileUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${project-scope.user.images.path}")
    private String filePath;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final SecurityService securityService;
    private final FileService fileService;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserExistsException(request.getEmail());
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User actualUser = userRepository.save(UserMapper.toEntity(request));
        return UserMapper.toDto(actualUser);

    }

    public AuthResponse login(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new UserNotFoundException(authRequest.getEmail());
        }
        return generateTokenAndIsActive(authRequest.getEmail());
    }

    private AuthResponse generateTokenAndIsActive(String email) {
        final String token = jwtUtil.generateToken(email);
        final Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        final User user = userRepository.save(userByEmail.get());
        return AuthResponse.builder()
                .user(UserMapper.toDto(user))
                .token(token)
                .build();

    }

    public UserResponse addProfilePhoto(MultipartFile multipartFile) {
        final Pair<String, String> pair = FileUtil.insertFile(filePath, multipartFile);
        final User user = securityService.fetchCurrentUser();
        FileEntity file = FileEntity.builder()
                .name(pair.getFirst())
                .fileType(pair.getSecond())
                .build();
        FileEntity saveFile = fileService.saveFile(file);
        user.setProfilePicture(saveFile);
        User actualUser = userRepository.save(user);
        return UserMapper.toDto(actualUser);

    }

    public Page<UserResponse> getAllMembers(Pageable pageable) {
            Page<User> allByUserType = userRepository.findAllByUserType(UserType.MEMBER,pageable);
        List<UserResponse> userList = allByUserType.stream().map(UserMapper::toDto).collect(Collectors.toList());
        return new PageImpl(userList);

    }

    public User getById(Long userId){
       return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
