public class Book extends CatalogItem {
    private String author;
    private Integer salesNumber;

    public Book(String name, String path, String author, Integer salesNumber) throws MyException {
        super(name, path);
        if(salesNumber < 0) {
            throw new MyException(name + "'s sales number can't be lower than 0");
        }
        this.author = author;
        this.salesNumber = salesNumber;
    }

    public Book(String author, int salesNumber) {
        this.author = author;
        this.salesNumber = salesNumber;
    }

    public Book() {}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(Integer salesNumber) throws MyException {
        if(salesNumber < 0) {
            throw new MyException(name + "'s sales number can't be lower than 0");
        }
        this.salesNumber = salesNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("name='").append(name).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", salesNumber=").append(salesNumber);
        sb.append('}');
        return sb.toString();
    }
}
