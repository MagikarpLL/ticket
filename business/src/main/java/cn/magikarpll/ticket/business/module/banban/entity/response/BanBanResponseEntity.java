package cn.magikarpll.ticket.business.module.banban.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanBanResponseEntity {

    public static final Integer SUCCESS = 1;

    public static final Integer NOT_SUCCESS = 0;

    public static final Integer FAILURE = -1;

    private Integer code;

    private String message;

    private Boolean status;

    private Object result;

}
