package projectscope.com.scope.endpoint;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectscope.com.scope.dto.request.AuthRequest;
import projectscope.com.scope.dto.request.UserRequest;
import projectscope.com.scope.dto.response.AuthResponse;
import projectscope.com.scope.dto.response.UserResponse;
import projectscope.com.scope.entity.User;
import projectscope.com.scope.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEndpoint {

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @ApiOperation(value = "User registration")
    public UserResponse register(@RequestBody UserRequest user) {
        return userService.createUser(user);

    }

    @PostMapping("/login")
    @ApiOperation(value = "User login")
    public AuthResponse login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        final AuthResponse login = userService.login(authRequest);
        request.getSession().setAttribute("firebaseToken", authRequest.getFirebaseToken());
        return login;
    }

    @PostMapping("/addProfilePhoto")
    @ApiOperation(value = "Add Picture")
    public ResponseEntity<UserResponse> addProfilePicture(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(userService.addProfilePhoto(file));
    }

    @GetMapping("/members")
    @ApiOperation(value = "Add Picture")
    public Page<UserResponse> getMembers(Pageable pageable) {
        return userService.getAllMembers(pageable);
    }
}
