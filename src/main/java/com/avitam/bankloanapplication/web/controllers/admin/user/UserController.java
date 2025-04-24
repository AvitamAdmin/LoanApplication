//package com.avitam.bankloanapplication.web.controllers.admin.user;
//
//import com.avitam.bankloanapplication.core.service.CoreService;
//import com.avitam.bankloanapplication.core.service.impl.UserServiceImpl;
//import com.avitam.bankloanapplication.model.dto.*;
//
//
//import com.avitam.bankloanapplication.model.entity.LoanType;
//import com.avitam.bankloanapplication.model.entity.User;
//import com.avitam.bankloanapplication.repository.UserRepository;
//import com.avitam.bankloanapplication.web.controllers.BaseController;
//import org.apache.commons.collections4.CollectionUtils;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin/user")
//public class UserController extends BaseController {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private CoreService coreService;
//    @Autowired
//    private UserServiceImpl userService;
//
//    public static final String ADMIN_USER = "/admin/user";
//
//    @PostMapping()
//    public UserWsDto getAllUsers(@RequestBody UserWsDto userWsDto) {
//        Pageable pageable = getPageable(userWsDto.getPage(), userWsDto.getSizePerPage(), userWsDto.getSortDirection(), userWsDto.getSortField());
//        UserDto userDto = CollectionUtils.isNotEmpty(userWsDto.getUserDtoList()) ? userWsDto.getUserDtoList().get(0) : new UserDto();
//        User user = modelMapper.map(userDto, User.class);
//        Page<User> page = isSearchActive(user) != null ? userRepository.findAll(Example.of(user), pageable) : userRepository.findAll(pageable);
//        userWsDto.setUserDtoList(modelMapper.map(page.getContent(), List.class));
//        userWsDto.setTotalPages(page.getTotalPages());
//        userWsDto.setTotalRecords(page.getTotalElements());
//        userWsDto.setBaseUrl(ADMIN_USER);
//        return userWsDto;
//    }
//
//    @GetMapping("/get")
//    public UserWsDto getActiveUserList() {
//        UserWsDto userWsDto = new UserWsDto();
//        userWsDto.setUserDtoList(modelMapper.map(userRepository.findByStatusOrderByIdentifier(true), List.class));
//        userWsDto.setBaseUrl(ADMIN_USER);
//        return userWsDto;
//    }
//
//    @PostMapping("/getedit")
//    public UserWsDto editUser(@RequestBody UserWsDto request) {
//        UserWsDto userWsDto = new UserWsDto();
//        userWsDto.setUserDtoList(modelMapper.map(userRepository.findByRecordId(request.getUserDtoList().get(0).getRecordId()), List.class));
//        userWsDto.setBaseUrl(ADMIN_USER);
//        return userWsDto;
//    }
//
//    @PostMapping(value = "/edit")
//    public UserWsDto save(@RequestBody UserDto request) throws IOException {
//        UserWsDto userWsDto = new UserWsDto();
//        userService.save(request);
//        userWsDto.setMessage("User updated Successfully");
//        return userWsDto;
//    }
//
//    @PostMapping("/delete")
//    public UserWsDto deleteUser(@RequestBody UserWsDto userWsDto) {
//
//        for (UserDto data : userWsDto.getUserDtoList()) {
//            userRepository.deleteByRecordId(data.getRecordId());
//        }
//        userWsDto.setBaseUrl(ADMIN_USER);
//        userWsDto.setMessage("Data Deleted Successfully");
//        return userWsDto;
//    }
//
//    @GetMapping("/getAdvancedSearch")
//    @ResponseBody
//    public List<SearchDto> getSearchAttributes() {
//        return getGroupedParentAndChildAttributes(new User());
//    }
//
//}
//
