package there.cumt.service;

import there.cumt.entity.User;
import there.cumt.utils.Result;

import java.util.List;


public interface AdminService {


    Result authoriseUser(String userName);

    Result revoke(String userName);

    Result addUser(User user);

    List<User> getUsers();

    User getOneUsers(String userName);
}
