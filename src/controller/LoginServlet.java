package controller;

import entity.Admin;
import entity.Book;
import entity.Borrow;
import entity.Reader;
import service.BookService;
import service.Impl.BookServiceImpl;
import service.Impl.LoginServiceImpl;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private LoginService loginService=new LoginServiceImpl();
    private BookService bookService=new BookServiceImpl();
    /**
     * 登录业务控制:接收前端传的用户信息，调用业务处理逻辑（返回数据而不应返回不尔类型，因为用户信息还要使用）查询数据库，判断账号密码是否正确；正确转发到首页，不正确重定向到登录界面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String type=req.getParameter("type");
//        多态，然后再父类转子类（不适用多态会导致登录业务处理service需要两个，一个处理读者登陆一个处理管理员登录）
        Object object=loginService.login(username,password,type);
        if(object !=null){
            HttpSession session =req.getSession();
            switch(type){
                case "reader":
                    Reader reader =(Reader)object;
                    session.setAttribute("reader",reader);
                    // 把图书列表list放入req传给首页，首页进行jstl的解析展示
                    List<Book> list=bookService.findAll(1);
                    //把需要传给首页的参数全部写进req中(需要展示的集合，每页展示数据数，展示第几页，一共几页)
//                    req.setAttribute("list",list);
//                    req.setAttribute("dataPrePage",6);
//                    req.setAttribute("currentPage",1);
//                    req.setAttribute("pages",bookService.getPages());
////                    转到用户首页，
//                    req.getRequestDispatcher("index.jsp").forward(req,resp);
                    resp.sendRedirect("/book?page=1");
                    break;
                case "admin":
                    Admin admin=(Admin)object;
                    session.setAttribute("admin",admin);
//                    转到管理员首页\拿需要审核的数据（从borrow表中）
                    //这里重定向到adminServlet进行查找数据，从而显示管理员首页
                    resp.sendRedirect("/admin?method=findAllBorrow&page=1");
                    break;
            }
        }else{
            resp.sendRedirect("login.jsp");
        }
    }

}
