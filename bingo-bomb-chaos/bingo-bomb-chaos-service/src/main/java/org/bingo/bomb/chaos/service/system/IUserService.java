package org.bingo.bomb.chaos.service.system;

import java.math.BigInteger;

import org.bingo.bomb.chaos.repository.domain.system.UserEntity;
import org.bingo.bomb.chaos.service.IService;

/**
 * 用户服务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:29:58
 * @since JDK 1.7
 */
public interface IUserService extends IService<UserEntity, BigInteger> {
}