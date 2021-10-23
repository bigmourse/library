package repository;

import entity.Admin;

import java.sql.SQLException;

public interface AdminRepository {
    public Admin login(String username, String password) ;
}
