package com.ssafy.web.socket.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class getMyPlanDto {

    private int plan_id;
    private String plan_name;
}
