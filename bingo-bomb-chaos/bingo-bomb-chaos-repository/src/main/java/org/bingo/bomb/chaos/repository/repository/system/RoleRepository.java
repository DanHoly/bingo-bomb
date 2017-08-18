package org.bingo.bomb.chaos.repository.repository.system;

import java.math.BigInteger;

import org.bingo.bomb.chaos.repository.domain.system.RoleEntity;
import org.bingo.bomb.chaos.repository.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 角色仓库
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:02:35
 * @since JDK 1.7
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<RoleEntity, BigInteger>, PagingAndSortingRepository<RoleEntity, BigInteger> {

}