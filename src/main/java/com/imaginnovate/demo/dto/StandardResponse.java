package com.imaginnovate.demo.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StandardResponse {
    Integer status;
    String message;
}
