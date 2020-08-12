package Demo.JDBCTemplate.EXJDBCTemplate;

public class Book {
    private String book_code;
    private String title;
    private String publisher_code;
    private String type;
    private Double price;
    private String paperback;

    public Book() {
    }

    public Book(String book_code, String title, String publisher_code, String type, Double price, String paperback) {
        this.book_code = book_code;
        this.title = title;
        this.publisher_code = publisher_code;
        this.type = type;
        this.price = price;
        this.paperback = paperback;
    }

    public String getBook_code() {
        return book_code;
    }

    public void setBook_code(String book_code) {
        this.book_code = book_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher_code() {
        return publisher_code;
    }

    public void setPublisher_code(String publisher_code) {
        this.publisher_code = publisher_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPaperback() {
        return paperback;
    }

    public void setPaperback(String paperback) {
        this.paperback = paperback;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_code='" + book_code + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher_code + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", paperback=" + paperback +
                '}';
    }
}
