package barbosa.murilo.envers_example.controller;

import java.time.Instant;
import org.hibernate.envers.RevisionType;

public record DirectorRevisionDTO(
      String id,
      String name,
      String modifiedBy,
      Instant revisionTimestamp,
      RevisionType revisionType
) {
}
