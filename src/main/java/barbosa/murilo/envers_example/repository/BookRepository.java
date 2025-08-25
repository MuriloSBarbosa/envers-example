package barbosa.murilo.envers_example.repository;

import barbosa.murilo.envers_example.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, String> {

}
