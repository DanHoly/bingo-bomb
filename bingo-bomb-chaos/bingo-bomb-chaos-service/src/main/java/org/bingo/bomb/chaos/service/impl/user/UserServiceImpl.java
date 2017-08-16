package org.bingo.bomb.chaos.service.impl.user;

import java.math.BigInteger;

import org.bingo.bomb.chaos.repository.domain.user.UserEntity;
import org.bingo.bomb.chaos.repository.repository.user.UserRepository;
import org.bingo.bomb.chaos.service.AbstractService;
import org.bingo.bomb.chaos.service.user.IUserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:43:28
 * @since JDK 1.7
 */
@Service("userService")
public class UserServiceImpl extends AbstractService<UserEntity, BigInteger, UserRepository> implements IUserService {


}