package highscore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hsc_highscores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Highscore {
    @Id
    @SequenceGenerator(name = "hsc_highscore_seq", sequenceName = "hsc_highscore_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hsc_highscore_seq")
    private Long id;
    @Column(nullable = false, unique = false)
    private String username;
    @Column(nullable = false, unique = false)
    private Integer score;
    @Column(nullable = false, unique = false)
    private LocalDateTime timestamp;
}
