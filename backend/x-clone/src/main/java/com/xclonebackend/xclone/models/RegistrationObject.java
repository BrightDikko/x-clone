package com.xclonebackend.xclone.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationObject {
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
}
