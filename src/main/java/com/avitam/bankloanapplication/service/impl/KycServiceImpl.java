package com.avitam.bankloanapplication.service.impl;

import com.avitam.bankloanapplication.core.service.CoreService;
import com.avitam.bankloanapplication.model.dto.KYCDto;
import com.avitam.bankloanapplication.model.dto.KYCWsDto;
import com.avitam.bankloanapplication.model.entity.KYC;
import com.avitam.bankloanapplication.repository.KYCRepository;
import com.avitam.bankloanapplication.service.KycService;
import org.bouncycastle.math.raw.Mod;
import org.bson.types.Binary;
import org.checkerframework.checker.units.qual.K;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class KycServiceImpl implements KycService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoreService coreService;

    @Autowired
    private KYCRepository kycRepository;

    private static final String ADMIN_KYC="/admin/Kyc";

    @Override
    public KYCWsDto handelEdit(KYCWsDto request) {
        KYC kyc = new KYC();
        List<KYCDto> kycDtos = request.getKycDtoList();
        List<KYC> kycs = new ArrayList<>();
        for (KYCDto kycDto :kycDtos){
            if (kycDto.getRecordId() != null){
                kyc = kycRepository.findByRecordId(kycDto.getRecordId());
                modelMapper.map(kycDto,kyc);
                kycRepository.save(kyc);
                request.setMessage("Data updated successfully");
            }
            else {
                kyc= modelMapper.map(kycDto,KYC.class);
                kyc.setCreationTime(new Date());
                kyc.setStatus(true);
                if (kycDto.getImage() != null && !kycDto.getImage().isEmpty()) {
                    try {
                        kyc.setPanImage(new Binary(kycDto.getImage().getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setMessage("Error processing image file");
                        return request;
                    }
                }
                kycRepository.save(kyc);
            }

            if (request.getRecordId()== null){
                kyc.setRecordId(String.valueOf(kyc.getId().getTimestamp()));
            }
            kycRepository.save(kyc);
            kycs.add(kyc);
            request.setBaseUrl(ADMIN_KYC);
            request.setMessage("Data Deleted Successfully");

        }
        request.setKycDtoList(modelMapper.map(kycs, List.class));
        return request;
    }
}
