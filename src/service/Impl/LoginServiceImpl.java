package service.Impl;

import entity.Reader;
import repository.AdminRepository;
import repository.ReaderRepository;
import repository.impl.AdminRepositoryImpl;
import repository.impl.ReaderRepositoryImpl;
import service.LoginService;

public class LoginServiceImpl implements LoginService {
    private ReaderRepository readerRepository=new ReaderRepositoryImpl() ;
    private AdminRepository adminRepository=new AdminRepositoryImpl();
    /**
     * 控制器传过来需要验证用户名和密码，业务处理是调用数据库操作查询数据库，返回正确的封装数据给控制器，
     * @param username
     * @param password
     * @return
     */
    @Override
    public Object login(String username, String password,String type) {
        Object object=null;
        switch(type){
            case "reader":
                object=readerRepository.login(username, password);
                break;
            case "admin":
                object=adminRepository.login(username, password);
                break;

        }
        return object;



    }
}
