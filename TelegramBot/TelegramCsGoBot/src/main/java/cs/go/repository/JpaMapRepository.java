package cs.go.repository;

import cs.go.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMapRepository extends JpaRepository<Map, Long> {


}
