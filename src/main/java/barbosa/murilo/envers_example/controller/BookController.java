package barbosa.murilo.envers_example.controller;

import barbosa.murilo.envers_example.entity.BookEntity;
import barbosa.murilo.envers_example.entity.DirectorEntity;
import barbosa.murilo.envers_example.entity.RevisionInfoEntity;
import barbosa.murilo.envers_example.repository.BookRepository;
import barbosa.murilo.envers_example.repository.DirectorRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final EntityManager entityManager;
    private final BookRepository bookRepository;
    private final DirectorRepository directorRepository;

    @GetMapping
    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    @Transactional
    public BookEntity saveBook(@RequestBody BookEntity book) {
        if (book.getDirector() != null) {
            book.setDirector(directorRepository.save(book.getDirector()));
        }
        return bookRepository.save(book);
    }

    @PostMapping("/all")
    @Transactional
    public List<BookEntity> saveAllBook(@RequestBody List<BookEntity> books) {
        for (BookEntity b : books) {
            if (b.getDirector() != null) {
                b.setDirector(directorRepository.save(b.getDirector()));
            }
        }
        return bookRepository.saveAll(books);
    }

    @Transactional
    @GetMapping("/{id}/revisions")
    public Page<BookRevisionDTO> getBookRevisions(@PathVariable String id,
          @PageableDefault Pageable pageable) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery query = auditReader.createQuery()
              .forRevisionsOfEntity(BookEntity.class, false, true)
              .add(AuditEntity.id().eq(id))
              .addOrder(AuditEntity.revisionNumber().desc());

        // Get total count
        AuditQuery countQuery = auditReader.createQuery()
              .forRevisionsOfEntity(BookEntity.class, false, true)
              .add(AuditEntity.id().eq(id));
        int total = countQuery.getResultList().size();

        // Apply pagination
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        // Fetch results
        List<Object[]> results = query.getResultList();

        List<BookRevisionDTO> content = results.stream().map(row -> {
            BookEntity entity = (BookEntity) row[0];
            RevisionInfoEntity revision = (RevisionInfoEntity) row[1];
            RevisionType revType = (RevisionType) row[2];

            return new BookRevisionDTO(
                  entity.getId(),
                  entity.getTitle(),
                  entity.getGenre(),
                  Optional.ofNullable(entity.getDirector())
                        .map(DirectorEntity::getName)
                        .orElse(null),
                  entity.getReleaseDate(),
                  entity.getRating(),
                  entity.getDescription(),
                  revision.getModifiedBy(),
                  Instant.ofEpochMilli(revision.getRevtstmp()),
                  revType
            );
        }).toList();

        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

    @Transactional
    @GetMapping("/{id}/director/revisions")
    public Page<DirectorRevisionDTO> getBookDirectorRevisions(@PathVariable String id,
          @PageableDefault Pageable pageable) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        var director = bookRepository.findById(id).map(BookEntity::getDirector)
              .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book or Director not found"));
        AuditQuery query = auditReader.createQuery()
              .forRevisionsOfEntity(DirectorEntity.class, false, true)
              .add(AuditEntity.id().eq(director.getId()))
              .addOrder(AuditEntity.revisionNumber().desc());

        // Get total count
        AuditQuery countQuery = auditReader.createQuery()
              .forRevisionsOfEntity(DirectorEntity.class, false, true)
              .add(AuditEntity.id().eq(director.getId()));
        int total = countQuery.getResultList().size();

        // Apply pagination
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        // Fetch results
        List<Object[]> results = query.getResultList();
        List<DirectorRevisionDTO> content = results.stream().map(row -> {
            DirectorEntity entity = (DirectorEntity) row[0];
            RevisionInfoEntity revision = (RevisionInfoEntity) row[1];
            RevisionType revType = (RevisionType) row[2];

            return new DirectorRevisionDTO(
                  entity.getId(),
                  entity.getName(),
                  revision.getModifiedBy(),
                  Instant.ofEpochMilli(revision.getRevtstmp()),
                  revType
            );
        }).toList();
        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

}