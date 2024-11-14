package com.avitam.bankloanapplication.core.service.impl;

import com.avitam.bankloanapplication.model.Role;
import com.avitam.bankloanapplication.model.dto.CustomerDTO;
import com.avitam.bankloanapplication.core.service.CoreService;
import com.avitam.bankloanapplication.core.service.UserService;
import com.avitam.bankloanapplication.model.entity.Customer;
import com.avitam.bankloanapplication.model.entity.User;
import com.avitam.bankloanapplication.model.entity.VerificationToken;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.repository.RoleRepository;
import com.avitam.bankloanapplication.repository.UserRepository;
import com.avitam.bankloanapplication.repository.VerificationTokenRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    public static final String ADMIN_USER="/admin/user";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void save( CustomerDTO request) {
        Customer customer=null;
        if(request.getRecordId()!=null)
        {
            customer = customerRepository.findByRecordId(request.getRecordId());
            modelMapper.map(request, customer);
        }
        else {
            customer = request.getCustomer();
            customer.setCreationTime(new Date());
            customerRepository.save(customer);
            }
        customer.setLastModified(new Date());
        /*if(StringUtils.isNotEmpty(customer.getPassword()))
        {
            customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
            customer.setPasswordConfirm(bCryptPasswordEncoder.encode(customer.getPasswordConfirm()));
        }*/
            customerRepository.save(customer);
            if (request.getRecordId() == null) {
                customer.setRecordId(String.valueOf(customer.getId().getTimestamp()));
            }
            customerRepository.save(customer);
            request.setCustomer(customer);
            request.setBaseUrl(ADMIN_USER);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUser(user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

       // user.setStatus(true);
        tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public boolean isAdminRole() {
        List<com.avitam.bankloanapplication.model.Role> roles = coreService.getCurrentUser().getRoles();
        if (CollectionUtils.isNotEmpty(roles)) {
            for (Role role : roles) {
                if ("ROLE_ADMIN".equals(role.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            //user.setResetPasswordToken(token);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User getByResetPasswordToken(String token) {

        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public boolean updateOtp(String token, String email) {
        return false;
    }


    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        //user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}