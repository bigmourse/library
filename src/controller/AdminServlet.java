package controller;

import com.sun.jdi.IntegerType;
import entity.Admin;
import entity.Borrow;
import service.BookService;
import service.Impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private BookService bookService=new BookServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method=req.getParameter("method");
        if(method==null){
            method="findAllBorrow";
        }
        HttpSession session=req.getSession();
        Admin admin=(Admin)session.getAttribute("admin");//类型强转，把对象类型变成具体的类型
        switch (method){
            case "findAllBorrow":
                String pageStr=req.getParameter("page");
                Integer page=Integer.parseInt(pageStr);

                List<Borrow> borrowList=bookService.findAllBorrrowByState(0,page);//查询所有待审核的
                req.setAttribute("list",borrowList);

                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages",bookService.getBorrowPagesByState(0));
                req.getRequestDispatcher("admin.jsp").forward(req,resp);
                break;
            case "handle":
                String idStr=req.getParameter("id");
                String stateStr=req.getParameter("state");
                Integer id=Integer.parseInt(idStr);
                Integer state=Integer.parseInt(stateStr);
                bookService.handleBorrow(id,state,admin.getId());//参数id指的是borrow列表中的第id条数据，不能用书名（不唯一）
                //处理过一次后重定向到admin界面
                if(state==1 || state==2){
                    resp.sendRedirect("/admin?page=1");
                }
                if(state==3){
                    resp.sendRedirect("/admin?method=getBorrowed&page=1");
                }


                break;
            case "getBorrowed":
                pageStr=req.getParameter("page");
                page=Integer.parseInt(pageStr);
                borrowList=bookService.findAllBorrrowByState(1,page);//查询所有已经借出去的
                req.setAttribute("list",borrowList);
                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages",bookService.getBorrowPagesByState(1));//通过状态查询所有已经借出去的书总数
                req.getRequestDispatcher("return.jsp").forward(req,resp);
                break;


        }


    }
}
