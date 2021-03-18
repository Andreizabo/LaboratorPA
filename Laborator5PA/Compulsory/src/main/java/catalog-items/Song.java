import java.time.LocalDate;

public class Song extends CatalogItem {
    private String singer;
    private LocalDate releaseDate;
    private Double duration;

    public Song(String name, String path, String singer, LocalDate releaseDate, Double duration) throws MyException {
        super(name, path);
        if(releaseDate.isAfter(LocalDate.now())) {
            throw new MyException("The song " + name + " can't be released in the future");
        }
        if(duration < 0) {
            throw new MyException("The song " + name + " can't have a negative duration");
        }
        this.singer = singer;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Song(String singer, LocalDate releaseDate, Double duration) {
        this.singer = singer;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Song() {}

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) throws MyException {
        if(releaseDate.isAfter(LocalDate.now())) {
            throw new MyException("The song " + name + " can't be released in the future");
        }
        this.releaseDate = releaseDate;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) throws MyException {
        if(duration < 0) {
            throw new MyException("The song " + name + " can't have a negative duration");
        }
        this.duration = duration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Song{");
        sb.append("name='").append(name).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", singer='").append(singer).append('\'');
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", duration=").append(duration);
        sb.append('}');
        return sb.toString();
    }
}
