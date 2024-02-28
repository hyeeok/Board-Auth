package com.greta.masa.auth.service;

import com.greta.masa.auth.entity.User;
import com.greta.masa.auth.entity.UserSecurity;
import com.greta.masa.auth.entity.UserInfo;
import com.greta.masa.auth.entity.UserSession;
import com.greta.masa.auth.repository.UserInfoRepository;
import com.greta.masa.auth.repository.UserRepository;
import com.greta.masa.auth.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final UserSecurityRepository userSecurityRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserInfoRepository userInfoRepository, UserSecurityRepository userSecurityRepository, UserRepository userRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userSecurityRepository = userSecurityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserSession login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        UserSecurity userSecurity = userSecurityRepository.findByUser(user);
        String personalSalt = userSecurity.getPersonalSalt();
        String hashedPassword = userSecurity.getHashedPassword();

        if (!passwordEncoder.matches(password + personalSalt, hashedPassword)) {
            return null;
        }

        Long userId = user.getUserId();
        UserInfo userInfo = userInfoRepository.findByUser(user);
        String nickName = userInfo.getNickName();

        UserSession userSession = new UserSession();
        userSession.setNickName(nickName);
        userSession.setUserId(userId);
        userSession.setCreatedTime(LocalDateTime.now());
        return userSession;
    }

    public void register(UserInfo userInfo, String username, String password) {
        User user = new User();
        user.setUsername(username);
        User savedUser = userRepository.save(user);

        String personalSalt = UUID.randomUUID().toString();
        String hashedPassword = passwordEncoder.encode(password + personalSalt);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setHashedPassword(hashedPassword);
        userSecurity.setPersonalSalt(personalSalt);
        userSecurity.setUser(savedUser);
        userSecurityRepository.save(userSecurity);

        userInfo.setUser(savedUser);
        userInfoRepository.save(userInfo);
    }
}
