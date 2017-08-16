package org.bingo.bomb.chaos.repository.repository;

import java.io.Serializable;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 分页和排序仓库
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:01:01
 * @since JDK 1.7
 */
@NoRepositoryBean
public interface PagingAndSortingRepository<T, ID extends Serializable> extends JpaSpecificationExecutor<T> {

	/**
	 * 全等于分页查询
	 * 
	 * @param example
	 * @param minId
	 * @param pageable
	 * @return
	 */
	public Page<T> findAll(Example<T> example, Pageable pageable, ID minId);

	/**
	 * 非全等于分页查询(自定义)
	 * 
	 * @param specification
	 * @param pageable
	 * @param minId
	 * @return
	 */
	public Page<T> findAll(Specification<T> specification, Pageable pageable, ID minId);

}
