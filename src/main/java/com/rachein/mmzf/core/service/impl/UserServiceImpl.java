package com.rachein.mmzf.core.service.impl;

import com.rachein.mmzf.entity.DB.User;
import com.rachein.mmzf.core.mapper.UserMapper;
import com.rachein.mmzf.core.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 吴远健
 * @since 2022-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void removeByOpenId(String openId) {
        lambdaUpdate().eq(User::getOpenid, openId)
                .remove();
    }
}
