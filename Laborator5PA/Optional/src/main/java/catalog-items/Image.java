import java.io.Serializable;

public class Image extends CatalogItem {
    private Double widthPx;
    private Double heightPx;

    public Image(String name, String path, Double widthPx, Double heightPx) throws MyException {
        super(name, path);
        if(widthPx < 0) {
            throw new MyException(name + "'s width can't be negative");
        }
        if(heightPx < 0) {
            throw new MyException(name + "'s height can't be negative");
        }
        this.widthPx = widthPx;
        this.heightPx = heightPx;
    }

    public Image(Double widthPx, Double heightPx) {
        this.widthPx = widthPx;
        this.heightPx = heightPx;
    }

    public Image() {}

    public Double getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(Double widthPx) throws MyException {
        if(widthPx < 0) {
            throw new MyException(name + "'s width can't be negative");
        }
        this.widthPx = widthPx;
    }

    public Double getHeightPx() {
        return heightPx;
    }

    public void setHeightPx(Double heightPx) throws MyException {
        if(heightPx < 0) {
            throw new MyException(name + "'s height can't be negative");
        }
        this.heightPx = heightPx;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Image{");
        sb.append("name='").append(name).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", widthPx=").append(widthPx);
        sb.append(", heightPx=").append(heightPx);
        sb.append('}');
        return sb.toString();
    }
}
