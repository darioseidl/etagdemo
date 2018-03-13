package at.darioseidl.etagdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "children", collectionResourceRel = "children")
public interface ChildRepository extends JpaRepository<Child, Long> {
}
