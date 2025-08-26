package barbosa.murilo.envers_example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Getter
@Setter
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String genre;
    private LocalDate releaseDate;
    private double rating;
    private String description;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private DirectorEntity director;
}
