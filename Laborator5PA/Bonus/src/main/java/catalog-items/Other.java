public class Other extends CatalogItem {
    public Other(String name, String path) throws MyException {
        super(name, path);
    }

    public Other() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Other{");
        sb.append("name='").append(name).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
