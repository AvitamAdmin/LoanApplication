package com.avitam.bankloanapplication.tokenGeneration;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String jwtToken;
    private String recordId;
}
