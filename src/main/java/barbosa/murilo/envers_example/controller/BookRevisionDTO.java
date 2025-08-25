package barbosa.murilo.envers_example.controller;

import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.envers.RevisionType;

public record BookRevisionDTO(
      String id,
      String title,
      String genre,
      String director,
      LocalDate releaseDate,
      double rating,
      String description,
      String modifiedBy,
      Instant revisionTimestamp,
      RevisionType revisionType
) {

}
