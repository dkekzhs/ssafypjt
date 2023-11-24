package com.ssafy.web.travel.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ShareUserDto {
    private int plan_id;
    private String user_id;
}
