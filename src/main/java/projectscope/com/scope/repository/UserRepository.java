package projectscope.com.scope.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projectscope.com.scope.entity.User;
import projectscope.com.scope.entity.UserType;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String username);

    Page<User> findAllByUserType(UserType userType, Pageable pageable);

}
