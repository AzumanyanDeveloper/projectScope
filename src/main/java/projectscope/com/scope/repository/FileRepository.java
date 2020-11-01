package projectscope.com.scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectscope.com.scope.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity,Long> {
}
