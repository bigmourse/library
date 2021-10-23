package service.Impl;

import entity.Book;
import entity.Borrow;
import repository.BookRepository;
import repository.BorrowRepository;
import repository.impl.BookReposityImpl;
import repository.impl.BorrowRepositoryImpl;
import service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookRepository bookRepository=new BookReposityImpl();
    private BorrowRepository borrowRepository=new BorrowRepositoryImpl();
    private final  int LIMIT=6;
    @Override
    public List<Book> findAll(int page) {
        int index=(page-1)*LIMIT;
//        表示我们要查找的第几页的数据，（从哪个索引开始查，查找几个）
//        第一页对应数据库中limit 0，6；第二页limit 6，6；第三页limit 12，6；第四页limit 18，6；
        return bookRepository.findAll(index,LIMIT);
    }

    /**
     * 获取book数据一共可以显示成几页
     * @return
     */
    @Override
    public int getPages() {
        int count=bookRepository.count();
        int page=0;
        if(count%LIMIT==0){
            page=count/LIMIT;
        }else{
            page=count/LIMIT+1;
        }
        return page;
    }

    @Override
    public int getBorrowPages(Integer readerId) {
        int count=borrowRepository.count(readerId);
        int page=0;
        if(count%LIMIT==0){
            page=count/LIMIT;
        }else{
            page=count/LIMIT+1;
        }
        return page;
    }

    @Override
    public void addBorrow(Integer bookId, Integer readerId) {
        //借书时间
        Date date=new Date();//日期时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        String borrowTime = simpleDateFormat.format(date);//日期时间进行格式化
        //还书时间，借书时间+14天——日期类
        Calendar calendar = Calendar.getInstance();
        int dates = calendar.get(Calendar.DAY_OF_YEAR) + 14;//今天在日期中第几天+14天
        calendar.set(Calendar.DAY_OF_YEAR, dates);  //calendar存储14天后是一年中的第几天
        Date date2 = calendar.getTime();//转化为Date对象
        String returnTime = simpleDateFormat.format(date2);
//        存进数据库中
        borrowRepository.insert(bookId,readerId,borrowTime,returnTime,null,0);

    }

    @Override
    public List<Borrow> findAllByReaderId(Integer readerId,Integer page) {
//        将page换算成数据库中的额索引
        int index=(page-1)*LIMIT;
        return borrowRepository.findAllByReaderId(readerId,index,LIMIT);
    }

    @Override
    public List<Borrow> findAllBorrrowByState(Integer state,Integer page) {
        int index=(page-1)*LIMIT;
        return borrowRepository.findAllByState(state,index,LIMIT);

    }

    @Override
    public int getBorrowPagesByState(Integer state) {
        int count=borrowRepository.countByState(state);
        int page=0;
        if(count%LIMIT==0){
            page=count/LIMIT;
        }else{
            page=count/LIMIT+1;
        }
        return page;

    }

    @Override
    public void handleBorrow(Integer bookId, Integer state, Integer adminId) {
        borrowRepository.handle(bookId,state,adminId);
    }
}
