package entity;

public class Borrow {
    private Integer id;
    private  Book book;//可以定义书名、作者、出版社，但是并不好，书本来就是一个对象，可以在Book类中加一个三个参数的构造器
    private Reader reader;//面向对象的思想
//    抽象成一个对象
    private String borrowtime;
    private String returntime;
    private Integer state;

    public Borrow(Integer id, Book book, Reader reader, String borrowtime, String returntime, Integer state) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.borrowtime = borrowtime;
        this.returntime = returntime;
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Borrow(Integer id, Book book, Reader reader, String borrowtime, String returntime) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.borrowtime = borrowtime;
        this.returntime = returntime;
    }

    public String getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(String borrowtime) {
        this.borrowtime = borrowtime;
    }

    public String getReturntime() {
        return returntime;
    }

    public void setReturntime(String returntime) {
        this.returntime = returntime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }


}
