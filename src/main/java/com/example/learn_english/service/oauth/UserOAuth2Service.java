package com.example.learn_english.service.oauth;

import com.example.learn_english.model.Role;
import com.example.learn_english.model.User;
import com.example.learn_english.repository.IRoleRepository;
import com.example.learn_english.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserOAuth2Service extends DefaultOAuth2UserService {

    private final IUserRepository userRepo;
    private final IRoleRepository roleRepo;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        checkAndCreateGoogleUser(email,name);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new DefaultOAuth2User(authorities, attributes, "email");
    }

    public void checkAndCreateGoogleUser(String email, String name) {
        Optional<User> existingUser = userRepo.findByEmail(email);
        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPassword("");
            Role defaultRole = roleRepo.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER không tồn tại trong DB"));
            newUser.setRoles(Set.of(defaultRole));

            userRepo.save(newUser);
        }
    }
}
