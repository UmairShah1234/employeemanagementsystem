package com.company.employeemanagementsystem.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEmployee {

    private String name;
    private String email;
    private String department;
    private String position;
    private BigDecimal salary;
}
