package repository.impl;

import entity.Book;
import entity.BookCase;
import repository.BookRepository;
import utils.JDBCTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookReposityImpl implements BookRepository {
    @Override
    public List<Book> findAll(int index ,int limit) {
        Connection connection= JDBCTools.getConnection();
        String sql="select * from book,bookcase where book.bookcaseid=bookcase.id limit ?,?";
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Book> list=new ArrayList<>();
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,index);
            preparedStatement.setInt(2,limit);
//            preparedStatement继承自statement
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
             // 为了节省栈内存，不需要定义一个个变量最后合成一个list
                    list.add(new Book(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),
                            resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6),
                            new BookCase(resultSet.getInt(9),resultSet.getString(10))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }

        return list;
    }

    @Override
    public int count() {
        Connection connection= JDBCTools.getConnection();
        String sql="select count(*) from book,bookcase where book.bookcaseid=bookcase.id";
//        执行结过就是一个总数
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int count=0;
        try {
            preparedStatement=connection.prepareStatement(sql);
//            preparedStatement继承自statement
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
//                为了节省栈内存，不需要定义一个个变量
                count=resultSet.getInt(1);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }
        return count;
    }
}
