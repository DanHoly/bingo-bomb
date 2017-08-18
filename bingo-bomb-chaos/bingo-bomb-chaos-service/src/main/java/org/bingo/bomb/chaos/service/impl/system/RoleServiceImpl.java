package org.bingo.bomb.chaos.service.impl.system;

import java.math.BigInteger;

import org.bingo.bomb.chaos.repository.domain.system.RoleEntity;
import org.bingo.bomb.chaos.repository.repository.system.RoleRepository;
import org.bingo.bomb.chaos.service.AbstractService;
import org.bingo.bomb.chaos.service.system.IRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色服务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:43:28
 * @since JDK 1.7
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractService<RoleEntity, BigInteger, RoleRepository> implements IRoleService {


}