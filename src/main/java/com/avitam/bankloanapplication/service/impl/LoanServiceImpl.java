package com.avitam.bankloanapplication.service.impl;

import com.avitam.bankloanapplication.model.dto.CustomerDto;
import com.avitam.bankloanapplication.model.dto.LoanDto;
import com.avitam.bankloanapplication.model.dto.LoanEmiDetailDto;
import com.avitam.bankloanapplication.model.dto.LoanTypeDto;
import com.avitam.bankloanapplication.model.dto.LoanWsDto;
import com.avitam.bankloanapplication.model.entity.Customer;
import com.avitam.bankloanapplication.model.entity.Loan;
import com.avitam.bankloanapplication.model.entity.LoanType;
import com.avitam.bankloanapplication.repository.CustomerRepository;
import com.avitam.bankloanapplication.repository.LoanDetailsRepository;
import com.avitam.bankloanapplication.repository.LoanRepository;
import com.avitam.bankloanapplication.repository.LoanScoreResultRepository;
import com.avitam.bankloanapplication.repository.LoanTypeRepository;
import com.avitam.bankloanapplication.service.LoanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {


    public static final String ADMIN_lOAN = "/loans/loan";
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanDetailsRepository loanDetailsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LoanTypeRepository loanTypeRepository;
    @Autowired
    private LoanScoreResultRepository loanScoreResultRepository;

    public LoanWsDto createLoan(LoanWsDto request) {
        Loan loan = new Loan();
        List<LoanDto> loanDtos = request.getLoanDtoList();
        List<Loan> loans = new ArrayList<>();
        for (LoanDto loanDto : loanDtos) {
//            loanDto.setRecordId(null);
            if (loanDto.getRecordId() != null) {
                loan = loanRepository.findByRecordId(loanDto.getRecordId());
                modelMapper.map(loanDto, loan);
                loanRepository.save(loan);
                request.setMessage("Data updated successfully");
            } else {
                loan = modelMapper.map(loanDto, Loan.class);
                loan.setStatus(true);
                loan.setCreationTime(new Date());
                LocalDate localDate = LocalDate.now();
                loan.setSanctionDate(localDate);
                modelMapper.map(loanDto, loan);
                loanRepository.save(loan);
            }
            if (request.getRecordId() == null) {
                loan.setRecordId(String.valueOf(loan.getId().getTimestamp()));
            } else {
                checkLoanStatus(loan);
            }
            //getLoanType(loan);
            //getCustomer(loan);


            loanRepository.save(loan);
            loans.add(loan);
            loanDto.setBaseUrl(ADMIN_lOAN);

            request.setMessage("Data added Successfully");
        }
        request.setLoanDtoList(modelMapper.map(loans, List.class));

        return request;

    }

//
//    @Override
//    public LoanDto getEmiStatusTillDate(LoanDto loanDto) {
//        Loan loan = loanRepository.findByRecordId(loanDto.getRecordId());
//        LocalDate sanctionDate = loan.getSanctionDate();
//        LocalDate currentDate = LocalDate.now();
//        //LocalDate baseDate = currentDate.withDayOfMonth(5);
//        LocalDate baseDate = sanctionDate.withDayOfMonth(5);
//        currentDate = currentDate.plusMonths(1);
//        //int noOfMonths = (int) ChronoUnit.MONTHS.between(baseDate, currentDate);
//        //currentDate = currentDate.plusDays(15);
//        //int noOfMonths = (int) ChronoUnit.MONTHS.between(sanctionDate, currentDate);
//
//        int noOfMonths = 0;
//        for (LoanEmiDetailDto loanEmiDetailDto : loan.getLoanEmiDetailDtoList()) {
//            if (loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Paid")) {
//                noOfMonths++;
//            }
//        }
//
//        if (sanctionDate.getDayOfMonth() > 5) {
//            baseDate = baseDate.plusMonths(noOfMonths + 1);
//        } else {
//            baseDate = baseDate.plusMonths(0);
//        }
//
//        double totalInterestAmount = 0.0;
//        double totalInstalmentAmount = 0.0;
//        double totalPayableAmount = 0.0;
//        double totalPenalty = 0.0;
//        int loopCount = 0;
//        //LocalDate dueDate = null;
//        for (LoanEmiDetailDto loanEmiDetailDto : loan.getLoanEmiDetailDtoList()) {
//            if (loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Unpaid")) {
//
//                loanEmiDetailDto.setDueDate(baseDate);
//
//                double instalment = 0.0;
//                double foreclosingCharges = 0.0;
//                if (loan.isForeClosing()) {
//                    instalment = loanEmiDetailDto.getInstalment();
//                    foreclosingCharges = loan.getDesiredLoan() * 5 / 100;
//                } else {
//                    instalment = loan.getPendingInstallmentAmount();
//                }
//                double interest = loanEmiDetailDto.getInterestAmount();
//                double baseAmount = instalment + interest;
//
//                double penalty = 0.0;
//
//                if (currentDate.isAfter(baseDate)) {
//                    int daysLate = (int) ChronoUnit.DAYS.between(baseDate, currentDate);
//                    penalty = roundToTwoDecimal(loanEmiDetailDto.getInstalment() * 0.05 * daysLate);
//                }
//                double totalPayable = roundToTwoDecimal(baseAmount + penalty + foreclosingCharges);
//                loanEmiDetailDto.setInstalment(instalment);
//                loanEmiDetailDto.setPenalty(penalty);
//                loanEmiDetailDto.setTotalPayable(totalPayable);
//                loan.setForeClosingCharges(foreclosingCharges);
//                //dueDate = loanEmiDetailDto.getDueDate();
//                loopCount++;
//                break;
//            }
//            loopCount++;
//        }
//
//        for (int i = 0; i < loopCount - 1; i++) {
//            LoanEmiDetailDto loanEmiDetailDto = loan.getLoanEmiDetailDtoList().get(i);
//            totalPayableAmount = totalPayableAmount + loanEmiDetailDto.getTotalPayable();
//            loan.setTotalPayableAmount(totalPayableAmount);
//            totalInterestAmount = totalInterestAmount + loanEmiDetailDto.getInterestAmount();
//            loan.setTotalInterestAmount(totalInterestAmount);
//            totalInstalmentAmount = totalInstalmentAmount + loanEmiDetailDto.getInstalment();
//            loan.setTotalInstalmentAmount(totalInstalmentAmount);
//            totalPenalty = totalPenalty + loanEmiDetailDto.getPenalty();
//            loan.setTotalPenalty(totalPenalty);
//        }
//
//        loan.setLoanEmiDetailDtoList(loan.getLoanEmiDetailDtoList());
//        loan.setPendingInstallmentAmount(loan.getDesiredLoan() - loan.getTotalInstalmentAmount());
//
//        loanRepository.save(loan);
//        modelMapper.map(loan, loanDto);
//        return loanDto;
//    }


    @Override
    public LoanDto getEmiStatusTillDate(LoanDto loanDto) {
        Loan loan = loanRepository.findByRecordIdAndCustomerId(loanDto.getRecordId(), loanDto.getCustomerId());
        LocalDate sanctionDate = loan.getSanctionDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate baseDate = sanctionDate.withDayOfMonth(5);
        currentDate = currentDate.plusMonths(4);
        currentDate = currentDate.plusDays(20);

        int noOfMonths = 0;
        for (LoanEmiDetailDto loanEmiDetailDto : loan.getLoanEmiDetailDtoList()) {
            if (loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Paid")) {
                noOfMonths++;
            }
        }

        if (sanctionDate.getDayOfMonth() > 5) {
            baseDate = baseDate.plusMonths(noOfMonths + 1);
        }

        double totalInterestAmount = 0.0;
        double totalInstalmentAmount = 0.0;
        double totalPayableAmount = 0.0;
        double totalPenalty = 0.0;
        int loopCount = 0;

        for (LoanEmiDetailDto loanEmiDetailDto : loan.getLoanEmiDetailDtoList()) {
            if (loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Unpaid")) {
                loanEmiDetailDto.setDueDate(baseDate);

                double instalment = 0.0;
                double foreclosingCharges = 0.0;
                if (loanDto.isForeClosing()) {
                    instalment = roundToTwoDecimal(loan.getPendingInstallmentAmount());
                    foreclosingCharges = roundToTwoDecimal(loan.getDesiredLoan() * 5 / 100);

                } else {
                    instalment = roundToTwoDecimal(loanEmiDetailDto.getInstalment());

                }

                double interest = roundToTwoDecimal(loanEmiDetailDto.getInterestAmount());
                double baseAmount = roundToTwoDecimal(instalment + interest);

                double penalty = 0.0;
                if (currentDate.isAfter(baseDate)) {
                    int daysLate = (int) ChronoUnit.DAYS.between(baseDate, currentDate);
                    penalty = roundToTwoDecimal(loanEmiDetailDto.getInstalment() * 0.05 * daysLate);
                }

                double totalPayable = roundToTwoDecimal(baseAmount + penalty + foreclosingCharges);
                loanEmiDetailDto.setInstalment(instalment);
                loanEmiDetailDto.setPenalty(penalty);
                loanEmiDetailDto.setTotalPayable(totalPayable);
                loan.setForeClosingCharges(foreclosingCharges);
                loan.setPendingInstallmentAmount(roundToTwoDecimal(loan.getPendingInstallmentAmount()-loanEmiDetailDto.getInstalment()));
                loopCount++;
                break;
            }
            loopCount++;
        }

        for (int i = 0; i <= loopCount - 1; i++) {
            LoanEmiDetailDto loanEmiDetailDto = loan.getLoanEmiDetailDtoList().get(i);

            totalPayableAmount += loanEmiDetailDto.getTotalPayable();
            totalInterestAmount += loanEmiDetailDto.getInterestAmount();
            totalInstalmentAmount += loanEmiDetailDto.getInstalment();
            totalPenalty += loanEmiDetailDto.getPenalty();
        }

        loan.setTotalPayableAmount(roundToTwoDecimal(totalPayableAmount));
        loan.setTotalInterestAmount(roundToTwoDecimal(totalInterestAmount));
        loan.setTotalInstalmentAmount(roundToTwoDecimal(totalInstalmentAmount));
        loan.setTotalPenalty(roundToTwoDecimal(totalPenalty));

        loan.setLoanEmiDetailDtoList(loan.getLoanEmiDetailDtoList());
        loanRepository.save(loan);
        modelMapper.map(loan, loanDto);
        return loanDto;
    }

    public LoanDto updatePaymentStatus(LoanDto loanDto) {

        Loan loan = loanRepository.findByRecordId(loanDto.getRecordId());
        int loopCount = 0;

        for (LoanEmiDetailDto loanEmiDetail : loan.getLoanEmiDetailDtoList()) {
            LoanEmiDetailDto loanEmiDetailDto1 = loanDto.getLoanEmiDetailDtoList().get(0);
            if (loanEmiDetail.getPaymentStatus().equalsIgnoreCase("Unpaid")) {
                if (loanEmiDetailDto1.getRecordId().equalsIgnoreCase(loanEmiDetail.getRecordId())) {
                    loanEmiDetail.setPaymentStatus(loanEmiDetailDto1.getPaymentStatus());
                    loanEmiDetail.setRecordId(loanEmiDetailDto1.getRecordId());
//                    loan.setTotalInterestAmount(loan.getTotalInterestAmount() + loanEmiDetail.getInterestAmount());
//                    loan.setTotalInstalmentAmount(loan.getTotalInstalmentAmount() + loanEmiDetail.getInstalment());
//                    loan.setTotalPayableAmount(loan.getTotalPayableAmount() + loanEmiDetail.getTotalPayable());
//                    loan.setTotalPenalty(loan.getTotalPenalty() + loanEmiDetail.getPenalty());
//                    loan.setPendingInstallmentAmount(loan.getPendingInstallmentAmount() - loanEmiDetail.getInstalment());
                    loopCount++;
                    break;
                }
            }
            loopCount++;
        }

        if (loan.getPendingInstallmentAmount() == 0) {
            for (int i = loopCount; i < loan.getTenure(); i++) {
                LoanEmiDetailDto loanEmiDetailDto = loan.getLoanEmiDetailDtoList().get(i);
                loanEmiDetailDto.setPaymentStatus("Paid");
                loanEmiDetailDto.setDueDate(loan.getLoanEmiDetailDtoList().get(loopCount - 1).getDueDate());
                loanEmiDetailDto.setInstalment(0);
                loanEmiDetailDto.setInterestAmount(0);
                loanEmiDetailDto.setTotalPayable(0);
                loanEmiDetailDto.setPenalty(0);
            }
            if(loopCount<loan.getTenure()) {
                loan.setForeClosing(true);
            }
        }

        loan.setLoanEmiDetailDtoList(loan.getLoanEmiDetailDtoList());
        modelMapper.map(loan, loanDto);
        loanRepository.save(loan);

        return loanDto;
    }

    public void checkLoanStatus(Loan loan) {
        int paidCount = 0;
        for (LoanEmiDetailDto loanEmiDetailDto : loan.getLoanEmiDetailDtoList()) {
            if (loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Paid")) {
                paidCount++;
            }
        }
        if (loan.getTenure() == paidCount) {
            loan.setLoanStatus("Completed");
        } else {
            loan.setLoanStatus("Active");
        }
    }

    public void getLoanType(Loan loan) {

        LoanType loanType = loanTypeRepository.findByRecordId(loan.getLoanTypeDto().getRecordId());
        //loan.setLoanType(loanType.getRecordId());
        LoanTypeDto loanTypeDto = modelMapper.map(loanType, LoanTypeDto.class);
        loan.setLoanTypeDto(loanTypeDto);

    }

    public void getCustomer(Loan loan) {

        Customer customer = customerRepository.findByRecordId(loan.getCustomerDto().getRecordId());
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        loan.setCustomerDto(customerDto);
    }

    private double roundToTwoDecimal(double value) {
        return Math.round(value * 100.0) / 100.0;
    }


    @Override
    public LoanWsDto getTotalDesiredLoanByCustomerRecordId(LoanWsDto request) {
        LoanWsDto loanWsDto = new LoanWsDto();
        List<LoanDto> loanDtos = new ArrayList<>();
        for (LoanDto loanDto : request.getLoanDtoList()) {
            List<Loan> loans = loanRepository.findByCustomerIdAndLoanStatus(loanDto.getCustomerId(), "Active");
            double total = 0;
            for (Loan loan : loans) {
                total += loan.getDesiredLoan();
                loanDto.setDesiredLoan(total);
            }
            loanDtos.add(loanDto);
        }
        loanWsDto.setLoanDtoList(loanDtos);
        return loanWsDto;
    }


//    //TODO: known issue:     "message": "Can not set java.util.Date field com.gulbalasalamov.bankloanapplication.model.entity.Loan.loanDate to java.lang.String",
//    public void updateLoanPartially(Long loanId, Map<Object, Object> objectMap) {
//        var loanById = findLoanById(loanId);
//        loanById.ifPresent(loan -> objectMap.forEach((key, value) -> {
//
//            Field field = ReflectionUtils.findField(Loan.class, (String) key);
//            field.setAccessible(true);
//            ReflectionUtils.setField(field, loan, value);
//            loanRepository.save(loan);
//        }));
//    }

//    public void updateLoan(Long loanId, Loan loan) {
//        var loanById = findLoanById(loanId);
//        loanById.ifPresent(updatedLoan -> {
//                    updatedLoan.setId(loan.getId());
//                    updatedLoan.setLoanType(loan.getLoanType());
//                    updatedLoan.setLoanLimit(loan.getLoanLimit());
//                    updatedLoan.setLoanDate(loan.getLoanDate());
//                    updatedLoan.setLoanStatus(loan.getLoanStatus());
//                    updatedLoan.setLoanScoreResult(loan.getLoanScoreResult());
//                    updatedLoan.setLoanApplication(loan.getLoanApplication());
//                    loanRepository.save(updatedLoan);
//                }
//        );
}


//        for (LoanEmiDetailDto loanEmiDetailDto : loan.getLoanEmiDetailDtoList()) {
//            if (loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Unpaid")) {
//                if (currentDate.isAfter(baseDate)) {
//                    int noOfDays = (int) ChronoUnit.DAYS.between(baseDate, currentDate);
//                    double totalPayable = loanEmiDetailDto.getTotalPayable();
//                   // loanEmiDetailDto.setPenalty(0);
//                    //loanEmiDetailDto.setTotalPayable(loanEmiDetailDto.getInstalment()+loanEmiDetailDto.getInterestAmount());
//                    loanEmiDetailDto.setTotalPayable(roundToTwoDecimal(totalPayable + (loanEmiDetailDto.getTotalPayable() * 0.04 * noOfDays)));
//                    loanEmiDetailDto.setPenalty(roundToTwoDecimal(loanEmiDetailDto.getTotalPayable() - totalPayable));
//                    loanEmiDetailDto.setDueDate(baseDate);
//                    loan.setTotalPayableAmount(loanEmiDetailDto.getTotalPayable()+loan.getTotalPayableAmount());
//                    loan.setTotalInterestAmount(loanEmiDetailDto.getInterestAmount()+loan.getTotalInterestAmount());
//                    loan.setTotalInstalmentAmount(loanEmiDetailDto.getInstalment()+loan.getTotalInstalmentAmount());
//                    loan.setTotalPenalty(loanEmiDetailDto.getPenalty()+loan.getTotalPenalty());
//                    loan.setPendingInstallmentAmount(loan.getDesiredLoan()-loan.getTotalInstalmentAmount());
//
//                    break;
//                }
//                loan.setTotalPayableAmount(loanEmiDetailDto.getTotalPayable()+loan.getTotalPayableAmount());
//                loan.setTotalInterestAmount(loanEmiDetailDto.getInterestAmount()+loan.getTotalInterestAmount());
//                loan.setTotalInstalmentAmount(loanEmiDetailDto.getInstalment()+loan.getTotalInstalmentAmount());
//                loan.setPendingInstallmentAmount(loan.getDesiredLoan()-loan.getTotalInstalmentAmount());
//                loanEmiDetailDto.setDueDate(baseDate);
//                break;
//            }
//        }


//        for(LoanEmiDetailDto loanEmiDetailDto: loan.getLoanEmiDetailDtoList()) {
//            if(loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Paid")) {
//                totalPayableAmount=totalPayableAmount+loanEmiDetailDto.getTotalPayable();
//                loan.setTotalPayableAmount(totalPayableAmount);
//                totalInterestAmount=totalInterestAmount+loanEmiDetailDto.getInterestAmount();
//                loan.setTotalInterestAmount(totalInterestAmount);
//                totalInstalmentAmount=totalInstalmentAmount+loanEmiDetailDto.getInstalment();
//                loan.setTotalInstalmentAmount(totalInstalmentAmount);
//                totalPenalty=totalPenalty+loanEmiDetailDto.getPenalty();
//                loan.setTotalPenalty(totalPenalty);
//            }
//        }


//        if(loopCount != loan.getTenure()) {
//            if (loan.getPendingInstallmentAmount() == 0.0) {
//                for (int i = loopCount; i <= loan.getTenure(); i++) {
//                    LoanEmiDetailDto loanEmiDetailDto = loan.getLoanEmiDetailDtoList().get(loopCount);
//                    //loanEmiDetailDto.setPaymentStatus("Paid");
//                    loanEmiDetailDto.setDueDate(dueDate);
//                    loanEmiDetailDto.setInstalment(0.0);
//                    loanEmiDetailDto.setInterestAmount(0.0);
//                    loanEmiDetailDto.setTotalPayable(0.0);
//                }
//            }
//        }


//        for(int i=0; i<loan.getTenure(); i++) {
//            LoanEmiDetailDto loanEmiDetailDto = loan.getLoanEmiDetailDtoList().get(i);
//            for (int j = 0; j < loanDto.getLoanEmiDetailDtoList().size(); j++) {
//                LoanEmiDetailDto loanEmiDetailDto1 = loanDto.getLoanEmiDetailDtoList().get(j);
//                if(loanEmiDetailDto.getRecordId().equalsIgnoreCase(loanEmiDetailDto1.getRecordId())){
//                    loanEmiDetailDto.setPaymentStatus(loanEmiDetailDto1.getPaymentStatus());
//                }
//            }
//        }
//        for(LoanEmiDetailDto loanEmiDetailDto1:loanDto.getLoanEmiDetailDtoList()) {
//            for(LoanEmiDetailDto loanEmiDetailDto: loan.getLoanEmiDetailDtoList()) {
//                if (loanEmiDetailDto.getPaymentStatus().equalsIgnoreCase("Unpaid")) {
//                    if (loanEmiDetailDto1.getRecordId().equalsIgnoreCase(loanEmiDetailDto.getRecordId())) {
//                        loanEmiDetailDto.setPaymentStatus(loanEmiDetailDto1.getPaymentStatus());
//                        loanEmiDetailDto.setRecordId(loanEmiDetailDto1.getRecordId());
//                        loanEmiDetailDto1.setTotalPayable(loanEmiDetailDto.getTotalPayable());
//                        loanEmiDetailDto1.setInstalment(loanEmiDetailDto.getInstalment());
//                        loanEmiDetailDto1.setInterestAmount(loanEmiDetailDto.getInterestAmount());
//                        loanEmiDetailDto1.setPenalty(loanEmiDetailDto.getPenalty());
//                        loanEmiDetailDto1.setDueDate(loanEmiDetailDto.getDueDate());
//                        break;
//                    }
//                }
//            }
//        }

//        for (int i = 0; i < loan.getTenure(); i++) {
//            LoanEmiDetailDto loanEmiDetailDto = loanDto.getLoanEmiDetailDtoList().get(0);
//            int j = Integer.parseInt(loanEmiDetailDto.getRecordId());
//            if (i == j) {
//                LoanEmiDetailDto loanEmiDetailDto1 = loan.getLoanEmiDetailDtoList().get(i);
//                loanEmiDetailDto1.setPaymentStatus(loanEmiDetailDto.getPaymentStatus());
//                loanEmiDetailDto1.setRecordId(loanEmiDetailDto.getRecordId());
//                break;
//            }
//            //loan.setLoanEmiDetailDtoList(loanEmiDetailDto1);
//        }

//                        loanEmiDetailDto1.setTotalPayable(loanEmiDetail.getTotalPayable());
//                        loanEmiDetailDto1.setInstalment(loanEmiDetail.getInstalment());
//                        loanEmiDetailDto1.setInterestAmount(loanEmiDetail.getInterestAmount());
//                        loanEmiDetailDto1.setPenalty(loanEmiDetail.getPenalty());
//                        loanEmiDetailDto1.setDueDate(loanEmiDetail.getDueDate());