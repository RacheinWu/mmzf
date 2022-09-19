package com.rachein.mmzf.core.service;

import com.rachein.mmzf.entity.DB.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 吴远健
 * @since 2022-08-06
 */
public interface IUserService extends IService<User> {

    void removeByOpenId(String fromUserName);

}
