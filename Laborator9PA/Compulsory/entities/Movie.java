package entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "MOVIES", schema = "C##MEDIC", catalog = "")
public class Movie {
    private Long id;
    private String title;
    private Time releaseDate;
    private Byte duration;
    private Long score;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE", nullable = true, length = 256)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "RELEASE_DATE", nullable = true)
    public Time getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Time releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "DURATION", nullable = true, precision = 0)
    public Byte getDuration() {
        return duration;
    }

    public void setDuration(Byte duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "SCORE", nullable = true, precision = 0)
    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(releaseDate, movie.releaseDate) && Objects.equals(duration, movie.duration) && Objects.equals(score, movie.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseDate, duration, score);
    }
}
