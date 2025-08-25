package barbosa.murilo.envers_example.controller;

import barbosa.murilo.envers_example.entity.BookEntity;
import barbosa.murilo.envers_example.entity.RevisionInfoEntity;
import barbosa.murilo.envers_example.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final EntityManager entityManager;
    private final BookRepository bookRepository;

    @GetMapping
    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public BookEntity saveBook(@RequestBody BookEntity book) {
        return bookRepository.save(book);
    }


    @Transactional
    @GetMapping("/{id}/revisions")
    public List<BookRevisionDTO> getBookRevisions(@PathVariable String id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery query = auditReader.createQuery()
              .forRevisionsOfEntity(BookEntity.class, false, true)
              .add(AuditEntity.id().eq(id));

        List<Object[]> results = query.getResultList();

        return results.stream().map(row -> {
            BookEntity entity = (BookEntity) row[0];       // entity state
            RevisionInfoEntity revision = (RevisionInfoEntity) row[1]; // revision info
            RevisionType revType = (RevisionType) row[2];   // revtype

            return new BookRevisionDTO(
                  entity.getId(),
                  entity.getTitle(),
                  entity.getGenre(),
                  entity.getDirector(),
                  entity.getReleaseDate(),
                  entity.getRating(),
                  entity.getDescription(),
                  revision.getModifiedBy(),
                  Instant.ofEpochMilli(revision.getRevtstmp()),
                  revType
            );
        }).toList();
    }


}