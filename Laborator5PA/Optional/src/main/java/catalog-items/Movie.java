import java.time.LocalDate;

public class Movie extends CatalogItem {
    private String director;
    private LocalDate releaseDate;
    private Double runningTime;

    public Movie(String name, String path, String director, LocalDate releaseDate, Double runningTime) throws MyException {
        super(name, path);
        if(releaseDate.isAfter(LocalDate.now())) {
            throw new MyException("The movie " + name + " can't be released in the future");
        }
        if(runningTime < 0) {
            throw new MyException("The movie " + name + " can't have a negative duration");
        }
        this.director = director;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
    }

    public Movie(String director, LocalDate releaseDate, Double runningTime) {
        this.director = director;
        this.releaseDate = releaseDate;
        this.runningTime = runningTime;
    }

    public Movie() {}

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) throws MyException {
        if(releaseDate.isAfter(LocalDate.now())) {
            throw new MyException("The movie " + name + " can't be released in the future");
        }
        this.releaseDate = releaseDate;
    }

    public Double getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Double runningTime) throws MyException {
        if(runningTime < 0) {
            throw new MyException("The movie " + name + " can't have a negative duration");
        }
        this.runningTime = runningTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("name='").append(name).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", director='").append(director).append('\'');
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", runningTime=").append(runningTime);
        sb.append('}');
        return sb.toString();
    }
}
