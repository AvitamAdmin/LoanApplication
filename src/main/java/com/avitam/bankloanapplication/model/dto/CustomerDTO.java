package com.avitam.bankloanapplication.model.dto;

import com.avitam.bankloanapplication.model.entity.Customer;
import com.avitam.bankloanapplication.model.entity.LoanApplication;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO extends CommonDto{

    @Pattern(regexp = "[1-9][0-9]{10}")
    private String nationalIdentityNumber;

    @NotBlank(message = "name can not be null")
    private String firstName;

    @NotBlank(message = "surname can not be null")
    private String lastName;

    @NotBlank(message = "phone can not be null")
    private String phone;

    @Email
    private String email;

    @NotNull(message = "monthly income can not be null")
    @Min(1)
    private Double monthlyIncome;

    private String gender;

    @Min(18)
    private Integer age;

    private Integer loanScore;

    private List<LoanApplication> loanApplications;

    private Customer customer;
    private List<Customer> customerList;

    public CustomerDTO(String s, String john, String cloud, String s1, String s2, double v, String female, int i, int i1, Object o) {
    }
}
