package blog.services;

import blog.models.User;
import blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import javax.sql.DataSource;

@Service
@Primary
public class UserServiceJpaImpl implements UserService {
    @Autowired
    DataSource dataSource ;

    public DataSource getDataSource()
   {
            return this.dataSource;
        }

    public void setDataSource(DataSource dataSource)

    {
           this.dataSource = dataSource;
     }


    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> findAll() {
        return this.userRepo.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.userRepo.findOne(id);
    }

    @Override
    public User create(User user) {
        // TODO: encrypt the password here  - DONE
        String hash = bCrypt(user.getPasswordHash());
        user.setPasswordHash(hash);

        return this.userRepo.save(user);
    }

    @Override
    public User edit(User user) {
        return this.userRepo.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepo.delete(id);
    }

    @Override
    public boolean authenticate(String username, String password) throws SQLException
    {
        String query = "Select u.password_hash from users u where u.username = ?";
        PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
        String hash = bCrypt(password);
        pstmt.setString(1, username);
        ResultSet resultSet = pstmt.executeQuery();

         if(resultSet.next()){
            String pass_db = resultSet.getString("password_hash");
            if(bCryptCheck(password,pass_db))
                return true;
            else
                return false;
        }

        else
           return false;

    }

    @Override
    public User login(String username, String password) {
        throw new UnsupportedOperationException("login Operation not implemented");
    }

    @Override
    public User register(String username, String password, String fullName) {
        throw new UnsupportedOperationException("register Operation not implemented");
    }

    @Override
    public void setPassword(String username, String newPassword) {
        throw new UnsupportedOperationException("setPassword Operation not implemented");
    }
    private static String bCrypt(String password) {
        String result = BCrypt.hashpw(password,BCrypt.gensalt());
        return result;
    }
    private static Boolean bCryptCheck(String password1,String password2) {
        boolean result = BCrypt.checkpw(password1,password2);
        return result;
    }

}
