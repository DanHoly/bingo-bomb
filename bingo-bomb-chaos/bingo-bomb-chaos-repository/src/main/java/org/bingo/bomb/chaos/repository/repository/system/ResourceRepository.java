package org.bingo.bomb.chaos.repository.repository.system;

import java.math.BigInteger;

import org.bingo.bomb.chaos.repository.domain.system.ResourceEntity;
import org.bingo.bomb.chaos.repository.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 资源仓库
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:02:35
 * @since JDK 1.7
 */
@Repository("resourceRepository")
public interface ResourceRepository extends JpaRepository<ResourceEntity, BigInteger>, PagingAndSortingRepository<ResourceEntity, BigInteger> {

}