package barbosa.murilo.envers_example.repository;

import barbosa.murilo.envers_example.entity.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<DirectorEntity, String> {

}
