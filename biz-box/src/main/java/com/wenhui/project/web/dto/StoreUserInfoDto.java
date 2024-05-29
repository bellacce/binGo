package com.wenhui.project.web.dto;

import com.wenhui.project.dal.mybatis.dataobject.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author XinHe
 * @package com.wenhui.project.web.dto
 * @date 2023-06-23
 * @Description
 */
@Data
public class StoreUserInfoDto {

    private Integer goldAmount;

    private Integer id;

    private String name;

    private String phone;

    private String avatarUrl;

    private List<Orders> orders;



}
