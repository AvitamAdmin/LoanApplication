package com.avitam.bankloanapplication.web.controllers;

import com.avitam.bankloanapplication.model.dto.CustomerDTO;
import com.avitam.bankloanapplication.core.service.CoreService;
import com.avitam.bankloanapplication.core.service.UserService;
import com.avitam.bankloanapplication.model.entity.Customer;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.repository.RoleRepository;
import com.avitam.bankloanapplication.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class AdminController extends BaseController{

    public static final String ADMIN_USER="/admin/user";
    Logger logger= LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private CoreService coreService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    /*@PostMapping("")
    @ResponseBody
    public UserDto getAllUsers(@RequestBody UserDto userDto) {
        Pageable pageable = getPageable(userDto.getPage(), userDto.getSizePerPage(), userDto.getSortDirection(), userDto.getSortField());
        User user = userDto.getUser();
        Page<User> page = isSearchActive(user) != null ? userRepository.findAll(Example.of(user), pageable) : userRepository.findAll(pageable);
        userDto.setUsersList(page.getContent());
        userDto.setTotalPages(page.getTotalPages());
        userDto.setTotalRecords(page.getTotalElements());
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }*/

    @GetMapping("/get")
    @ResponseBody
    public CustomerDTO getActiveUserList() {
        CustomerDTO userDto = new CustomerDTO();
        userDto.setCustomerList(customerRepository.findByStatusOrderByIdentifier(true));
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public CustomerDTO editUser(@RequestBody CustomerDTO request) {
        CustomerDTO userDto = new CustomerDTO();
        Customer user = customerRepository.findByRecordId(userDto.getRecordId());
        userDto.setCustomer(user);
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public CustomerDTO save(@RequestBody CustomerDTO request) {

        userService.save(request);
        return request;
    }

    @GetMapping("/add")
    @ResponseBody
    public CustomerDTO addUser() {
        CustomerDTO userDto=new CustomerDTO();
        userDto.setCustomerList(customerRepository.findByStatusOrderByIdentifier(true));
        userDto.setBaseUrl(ADMIN_USER);
        return userDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public CustomerDTO deleteUser(@RequestBody CustomerDTO userDto) {

        for (String id : userDto.getRecordId().split(",")) {
            customerRepository.deleteByRecordId(id);
        }
        userDto.setBaseUrl(ADMIN_USER);
        userDto.setMessage("Data Deleted Successfully");
        return userDto;
    }


}
