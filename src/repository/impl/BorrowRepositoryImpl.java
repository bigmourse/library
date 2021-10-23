package repository.impl;

import entity.Book;
import entity.Borrow;
import entity.Reader;
import repository.BorrowRepository;
import utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepositoryImpl implements BorrowRepository {
    @Override
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state) {
        Connection connection= JDBCTools.getConnection();
        String sql="insert into borrow(bookid,readerid,borrowtime,returntime,state) values(?,?,?,?,0)";
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookid);
            preparedStatement.setInt(2,readerid);
            preparedStatement.setString(3,borrowtime);
            preparedStatement.setString(4,returntime);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }
    }

    @Override
    public List<Borrow> findAllByReaderId(Integer readerId,Integer index,Integer limit) {
        Connection connection=JDBCTools.getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Borrow> list=new ArrayList<>();
        String sql="select borrow.id,book.name,book.author,book.publish,reader.name,reader.tel,reader.cardid,borrow.borrowtime,borrow.returntime,borrow.state from borrow,book,reader where reader.id=? and reader.id=borrow.readerid and book.id=borrow.bookid limit ?,?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,readerId);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,limit);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
//                减少栈内存的开销
                Borrow borrow= new Borrow(resultSet.getInt(1),
                        new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
                        new Reader(resultSet.getString(5),resultSet.getInt(6),resultSet.getString(7)),
                        resultSet.getString(8),resultSet.getString(9),resultSet.getInt(10));
                list.add(borrow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }
            return  list;
    }

    @Override
    public int count(Integer readerId) {
        Connection connection=JDBCTools.getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int count=0;
        String sql="select count(*) from borrow,book,reader where reader.id=? and reader.id=borrow.readerid and book.id=borrow.bookid";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,readerId);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                count=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }
        return count;
    }

    @Override
    public List<Borrow> findAllByState(Integer state,Integer index,Integer limit) {
        Connection connection=JDBCTools.getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Borrow> list=new ArrayList<>();
        String sql="select borrow.id,book.name,book.author,book.publish,reader.name,reader.tel,reader.cardid,borrow.borrowtime,borrow.returntime,borrow.state from borrow,book,reader where borrow.state=? and reader.id=borrow.readerid and book.id=borrow.bookid limit ?,?;";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,state);
            preparedStatement.setInt(2,index);
            preparedStatement.setInt(3,limit);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
//                减少栈内存的开销
                Borrow borrow= new Borrow(resultSet.getInt(1),
                        new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
                        new Reader(resultSet.getString(5),resultSet.getInt(6),resultSet.getString(7)),
                        resultSet.getString(8),resultSet.getString(9),resultSet.getInt(10));
                list.add(borrow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }
        return  list;
    }

    @Override
    public int countByState(Integer state) {
        Connection connection=JDBCTools.getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int count=0;
        String sql="select count(*) from borrow,book,reader where borrow.state=? and reader.id=borrow.readerid and book.id=borrow.bookid";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,state);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                count=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }
        return count;
    }

    @Override
    public void handle(Integer borrowId, Integer state, Integer adminId) {
        Connection connection=JDBCTools.getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String sql="update borrow set state=?,adminid=? where id=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,state);
            preparedStatement.setInt(2,adminId);
            preparedStatement.setInt(3,borrowId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCTools.release(connection,preparedStatement,resultSet);
        }

    }
}
