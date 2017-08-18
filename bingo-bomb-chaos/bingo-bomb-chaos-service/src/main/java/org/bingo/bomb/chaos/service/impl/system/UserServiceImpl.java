package org.bingo.bomb.chaos.service.impl.system;

import java.math.BigInteger;

import org.bingo.bomb.chaos.repository.domain.system.UserEntity;
import org.bingo.bomb.chaos.repository.repository.system.UserRepository;
import org.bingo.bomb.chaos.service.AbstractService;
import org.bingo.bomb.chaos.service.system.IUserService;
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