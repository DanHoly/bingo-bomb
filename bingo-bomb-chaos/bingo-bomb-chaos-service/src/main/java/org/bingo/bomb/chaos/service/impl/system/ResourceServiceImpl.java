package org.bingo.bomb.chaos.service.impl.system;

import java.math.BigInteger;

import org.bingo.bomb.chaos.repository.domain.system.ResourceEntity;
import org.bingo.bomb.chaos.repository.repository.system.ResourceRepository;
import org.bingo.bomb.chaos.service.AbstractService;
import org.bingo.bomb.chaos.service.system.IResourceService;
import org.springframework.stereotype.Service;

/**
 * 资源服务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:43:28
 * @since JDK 1.7
 */
@Service("resourceService")
public class ResourceServiceImpl extends AbstractService<ResourceEntity, BigInteger, ResourceRepository> implements IResourceService {

}