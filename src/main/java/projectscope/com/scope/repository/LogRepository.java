package projectscope.com.scope.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projectscope.com.scope.entity.Log;
import projectscope.com.scope.entity.Project;
import projectscope.com.scope.entity.User;

import java.util.List;

public interface LogRepository extends JpaRepository<Log,Long> {

    List<Log> findAllByProjectId(Long projectId);

    Page<Log> findAllByUser(User user, Pageable pageable);

    Log findByIdAndUser(Long logId,User user);

    List<Log> findAllByProject(Project project);
}
