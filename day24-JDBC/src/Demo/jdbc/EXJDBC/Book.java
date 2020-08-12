package Demo.jdbc.EXJDBC;

public class Book {
    private String book_code;
    private String title;
    private String publisher_code;
    private String type;
    private double price;
    private String paperback;


    public Book(String book_code, String title, String publisher_code, String type, double price, String paperback) {
        this.book_code = book_code;
        this.title = title;
        this.publisher_code = publisher_code;
        this.type = type;
        this.price = price;
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
