//package com.avitam.bankloanapplication.web.controllers;
//
//
//import com.avitam.bankloanapplication.core.Utility;
//import com.avitam.bankloanapplication.core.service.SecurityService;
//import com.avitam.bankloanapplication.core.service.UserService;
//import com.avitam.bankloanapplication.model.dto.CustomerDto;
//import com.avitam.bankloanapplication.model.dto.UserDto;
//import com.avitam.bankloanapplication.model.entity.User;
//import com.avitam.bankloanapplication.repository.RoleRepository;
//import com.avitam.bankloanapplication.repository.UserRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.MessageSource;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.UnsupportedEncodingException;
//import java.util.UUID;
//
//@Controller
//@CrossOrigin
//public class SecurityController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private SecurityService securityService;
//    @Autowired
//    ApplicationEventPublisher eventPublisher;
//    @Autowired
//    private MessageSource messages;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @GetMapping("/forgotpassword")
//    public String showForgotPasswordForm(HttpServletRequest request, Model model) {
//        return "security/forgotpassword";
//    }
//
//    @PostMapping("/forgotpassword")
//    public String processForgotPassword(HttpServletRequest request, Model model) {
//        String email = request.getParameter("email");
//        String token = UUID.randomUUID().toString();
//
//        boolean success = userService.updateResetPasswordToken(token, email);
//        if (success) {
//            String resetPasswordLink = Utility.getSiteURL(request) + "/resetpassword?token=" + token;
//            //sendEmail(email, resetPasswordLink);
//            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
//            model.addAttribute("color", "green");
//        } else {
//            model.addAttribute("message", "User Not Registered. Please enter valid email id");
//            model.addAttribute("color", "red");
//        }
//
//        return "security/forgotpasswordsuccess";
//    }
//
////    public void sendEmail(String recipientEmail, String link)
////            throws MessagingException, UnsupportedEncodingException {
////        EMail eMail = new EMail();
////
////        //eMail.setFrom("healthcheck@cheil.com");
////        eMail.setTo(recipientEmail);
////
////        String subject = "Here's the link to reset your password";
////
////        String content = "<p>Hello,</p>"
////                + "<p>You have requested to reset your password.</p>"
////                + "<p>Click the link below to change your password:</p>"
////                + "<p><a href=\"" + link + "\">Change my password</a></p>"
////                + "<br>"
////                + "<p>Ignore this email if you do remember your password, "
////                + "or you have not made the request.</p>";
////
////        eMail.setSubject(subject);
////        eMail.setContent(content);
////        mailService.sendEmail(eMail);
////    }
//
//
//    @GetMapping("/resetpassword")
//    public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
//        User user = userService.getByResetPasswordToken(token);
//        model.addAttribute("token", token);
//
//        if (user == null) {
//            model.addAttribute("message", "Invalid Token");
//            return "security/forgotpassword";
//        }
//        return "security/passwordreset";
//    }
//
//    @PostMapping("/resetpassword")
//    public String processResetPassword(HttpServletRequest request, Model model) {
//        String token = request.getParameter("token");
//        String password = request.getParameter("password");
//
//        User user = userService.getByResetPasswordToken(token);
//        model.addAttribute("title", "Reset your password");
//
//        if (user == null) {
//            model.addAttribute("message", "Invalid Token");
//            return "security/resetsuccess";
//        } else {
//            userService.updatePassword(user, password);
//            model.addAttribute("message", "You have successfully changed your password.");
//        }
//        return "security/resetsuccess";
//    }
//
//    @GetMapping("")
//    public String viewHomePage() {
//
//        return "index";
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("userForm", new User());
//        model.addAttribute("roles", roleRepository.findAll());
//        return "security/signupForm";
//    }
//
//    @PostMapping("/register")
//    @ResponseBody
//    public UserDto processRegister(@RequestBody UserDto request) {
//       userService.save(request);
//       request.setMessage("Registration Successful!!");
//       return request;
//    }
//
//    @GetMapping("/login")
//   @ResponseBody
//    public String login(Model model, String error, String logout, String quota) {
//        if (error != null) {
//            model.addAttribute("message", "Your username and password is invalid.");
//        }
//
//        if (quota != null) {
//            model.addAttribute("message", "Allotted Quota Exceeded");
//        }
//
//        if (logout != null) {
//            model.addAttribute("message", "You have been logged out successfully.");
//        }
//
//        return "login";
//    }
//
//
//}


package com.avitam.bankloanapplication.web.controllers;


import com.avitam.bankloanapplication.core.service.SecurityService;
import com.avitam.bankloanapplication.core.service.UserService;
import com.avitam.bankloanapplication.mail.service.EMail;
import com.avitam.bankloanapplication.mail.service.MailService;
import com.avitam.bankloanapplication.model.dto.CustomerDto;
import com.avitam.bankloanapplication.model.dto.CustomerWsDto;
import com.avitam.bankloanapplication.model.dto.UserDto;
import com.avitam.bankloanapplication.model.entity.Customer;
import com.avitam.bankloanapplication.model.entity.User;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.repository.RoleRepository;
import com.avitam.bankloanapplication.service.CustomerService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

@Controller
@CrossOrigin
public class SecurityController extends BaseController {

    public static final String EMAIL_CONTENT2 = "<br>If you don't approve the access, please ignore the email. The request will be expired in 30mins.";
    public static final String MAIL_CONTENT = " is requesting for your approval to access to Zero-in platform - VQA. <br>If you approve the access, click the link below.";
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Environment env;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/forgotpassword")
    @ResponseBody
    public String showForgotPasswordForm() {
        return "security/forgotpassword";
    }

    @PostMapping("/forgotpassword")
    @ResponseBody
    public CustomerWsDto processForgotPassword(@RequestBody CustomerDto customerDto) {
        CustomerWsDto customerWsDto = new CustomerWsDto();
        String email = customerDto.getFullName();
        String token = UUID.randomUUID().toString();

        try {
            boolean success = customerService.updateResetPasswordToken(token, email);
            if (success) {
                String appUrl = env.getProperty("server.url");
                String uiUrl = env.getProperty("server.ui.url") + "/login/updatepassword?token=" + token;

                String subject = "Here's the link to reset your password";

                String content = "<p>Hello,</p>"
                        + "<p>You have requested to reset your password.</p>"
                        + "<p>Click the link below to change your password:</p>"
                        + "<p><a href=\"" + uiUrl + "\">Change my password</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, "
                        + "or you have not made the request.</p>";

                sendEmail(email, subject, content);
                customerWsDto.setMessage("We have sent a reset password link to your email. Please check.");
                customerWsDto.setSuccess(true);
            } else {
                customerWsDto.setMessage("User Not Registered. Please enter valid email id");
                customerWsDto.setSuccess(false);
            }
            return customerWsDto;

        } catch (UnsupportedEncodingException | MessagingException e) {
            customerWsDto.setMessage("Error while sending email" + e.getMessage());
            customerWsDto.setSuccess(false);
            return customerWsDto;
        }
    }

    @GetMapping("/otplogin")
    @ResponseBody
    public String otpLogin(@RequestBody CustomerDto customerDto) {

        try {
            String email = customerDto.getFullName();
            String token = new DecimalFormat("000000").format(new Random().nextInt(999999));
            boolean success = customerService.updateOtp(token, email);
            if (success) {
                String subject = "Here's the otp for your login";
                String content = "<p>Please find the otp below "
                        + "<br>" + token;
                sendEmail(email, subject, content);
            } else {
                return "Please enter valid email id";
            }

        } catch (UnsupportedEncodingException | MessagingException e) {
            return "Error while sending email";
        }
        return "Otp sent successfully";
    }

    public void sendEmail(String recipientEmail, String subject, String content)
            throws MessagingException, UnsupportedEncodingException {
        EMail eMail = new EMail();

        //eMail.setFrom("healthcheck@cheil.com");
        eMail.setTo(recipientEmail);

        eMail.setSubject(subject);
        eMail.setContent(content);
        mailService.sendEmail(eMail);
    }

    @GetMapping("/resetpassword")
    @ResponseBody
    public CustomerDto showResetPasswordForm(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getByResetPasswordToken(customerDto.getResetPasswordToken());
        if (customer != null) {
            return modelMapper.map(customer, CustomerDto.class);
        }
        return new CustomerDto();
    }

    @PostMapping("/resetpassword")
    @ResponseBody
    public CustomerWsDto processResetPassword(@RequestBody CustomerDto customerDto) {
        String token = customerDto.getResetPasswordToken();
        String password = customerDto.getPassword();
        CustomerWsDto customerWsDto = new CustomerWsDto();

        Customer customer = customerService.getByResetPasswordToken(token);

        if (customer == null) {
            customerWsDto.setMessage("Invalid Token");
            customerWsDto.setSuccess(false);
            return customerWsDto;
        } else {
            customerService.updatePassword(customer, password);
            customerWsDto.setSuccess(true);
            customerWsDto.setMessage("You have successfully changed your password.");
        }
        return customerWsDto;
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(Model model, String error, String logout, String quota) {
        model.addAttribute("otpEnabled", env.getProperty("otp.enabled", "false"));
        if (error != null) {
            model.addAttribute("message", "Your username and password is invalid.");
        }
        if (quota != null) {
            model.addAttribute("message", "Allotted Quota Exceeded");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("userForm", new User());
//        model.addAttribute("roles", roleRepository.findAll());
//        return "security/signupForm";
//    }

    @PostMapping("/register")
    @ResponseBody
    public UserDto processRegister(@RequestBody UserDto request) {
       userService.save(request);
       request.setMessage("Registration Successful!!");
       return request;
    }
}