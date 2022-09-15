package com.spring.config;
import com.spring.bean.Admin;
import com.spring.bean.LoginUser;
import com.spring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * 自定义用户信息查询
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        System.out.println("终究是要进入这里的");
        Admin admin = adminService.getUserByName(name);
        if (admin == null){
            throw new RuntimeException("用户名错误");
        }
        LoginUser loginUser = coverToLoginUser(admin);
        return loginUser;
    }

    private LoginUser coverToLoginUser(Admin adminEntity) {
        LoginUser loginUser = new LoginUser();
        loginUser.setName(adminEntity.getName());
        loginUser.setNickname(adminEntity.getName());
        loginUser.setPassword(adminEntity.getPassword());
        loginUser.setPhone(adminEntity.getPhone());
        return loginUser;
    }
}
