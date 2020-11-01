package projectscope.com.scope.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectscope.com.scope.entity.Project;
import projectscope.com.scope.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    Page<Project> findAllByUser(User user, Pageable pageable);

    Page<Project> findAllByMembers(User user,Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.date >= :dateTo AND p.date <= :dateFrom AND lower(p.name) like :keyword")
    Page<Project> serachProject(String keyword,LocalDate dateTo, LocalDate dateFrom, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.date >= :dateTo AND p.date <= :dateFrom")
    Page<Project> serachProject(LocalDate dateTo, LocalDate dateFrom, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.date <= :dateFrom AND p.name like :keyword")
    Page<Project> serachProjectFrom(LocalDate dateFrom,String keyword, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE p.date >= :dateTo AND p.name like :keyword")
    Page<Project> serachProjectTo(LocalDate dateTo,String keyword, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE lower(p.name) like :keyword")
    Page<Project> serachProject(String keyword, Pageable pageable);

}
