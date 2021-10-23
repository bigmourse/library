package service;

import entity.Book;
import entity.Borrow;

import java.util.List;

public interface BookService {
 public List<Book> findAll(int page);
 public int getPages();
 public void addBorrow(Integer bookid,Integer readerid);
 public  List<Borrow> findAllByReaderId(Integer id,Integer page);
 public int getBorrowPages(Integer readerId);
 public List<Borrow> findAllBorrrowByState(Integer state,Integer page);
 public int getBorrowPagesByState(Integer state);
public void handleBorrow(Integer bookId,Integer state,Integer adminId);
}
