package controller;

import entity.Book;
import entity.Borrow;
import entity.Reader;
import service.BookService;
import service.Impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private BookService bookService=new BookServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        String method=req.getParameter("method");
        if(method==null){
            method="findAll";
        }
        HttpSession session=req.getSession();
        Reader reader=(Reader) session.getAttribute("reader");//返回对象再进行类型转换
        Integer readerId=reader.getId();
        switch(method){
            case "findAll":
                String pageStr=req.getParameter("page");
                Integer page=Integer.parseInt(pageStr);
                List<Book> list=bookService.findAll(page);
                //把需要传给首页的参数全部写进req中(需要展示的集合，每页展示数据数，展示第几页，一共几页)
                req.setAttribute("list",list);
                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages",bookService.getPages());
//                    转到用户首页，
                try {
                    req.getRequestDispatcher("index.jsp").forward(req,resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "addBorrow":
//                借书需要知道：书本id。借书人id
//                其它的参数，业务处理逻辑是可以自己拿到,直接存进数据库中即可
//                借书一共需要知道的参数是borrow数据中的前四个，借哪本书、谁借的、结束时间、还书时间
                String bookIdStr=req.getParameter("bookId");
                Integer bookId =Integer.parseInt(bookIdStr);
                bookService.addBorrow(bookId,readerId);
                try {
                    resp.sendRedirect("/book?method=findAllBorrow&page=1");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                page=1;
//                List<Borrow> borrowList=bookService.findAllByReaderId(readerId,page);
//                req.setAttribute("borrowList",borrowList);//转发给borrow界面
//                req.setAttribute("dataPrePage",6);
//                req.setAttribute("currentPage",page);
//                req.setAttribute("pages",bookService.getBorrowPages(readerId));
//                try {
//                    req.getRequestDispatcher("borrow.jsp").forward(req,resp);
//                } catch (ServletException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
            case "findAllBorrow":
                pageStr=req.getParameter("page");
                page=Integer.parseInt(pageStr);
//                完成一个借书功能后续功能展示、展示某个用户借书记录(展示的记录需要人性化能看懂)
                List<Borrow> borrowList2=bookService.findAllByReaderId(readerId,page);
                req.setAttribute("borrowList",borrowList2);//转发给borrow界面
                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages",bookService.getBorrowPages(readerId));
                try {
                    req.getRequestDispatcher("borrow.jsp").forward(req,resp);
//                    System.out.println("转发borrow.jsp");
                } catch (ServletException e) {
                    e.printStackTrace();
//                    System.out.println("发生错误");
                } catch (IOException e) {
                    e.printStackTrace();
//                    System.out.println("发生错误");
                }
//                System.out.println("break");
                break;
        }

    }

}
