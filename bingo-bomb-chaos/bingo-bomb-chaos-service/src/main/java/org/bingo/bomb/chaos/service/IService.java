package org.bingo.bomb.chaos.service;

import java.io.Serializable;
import java.util.List;

import org.bingo.bomb.chaos.repository.domain.AbstractBaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 抽象服务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:07:58
 * @since JDK 1.7
 */
public interface IService<V extends AbstractBaseEntity, ID extends Serializable> {

	/**
	 * 保存Vo
	 * 
	 * @param v
	 * @return
	 */
	public void save(V v);

	/**
	 * 批量保存
	 * 
	 * @param list
	 * @return
	 */
	public void save(List<V> list);

	/**
	 * 根据是否有Id判断是更新还是保存
	 * 
	 * @param v
	 * @return
	 */
	public ServiceResult<ID> saveOrUpdate(V v);
	
	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public V findById(ID id);
	
	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public V findByCondition(V v);

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	public void deleteById(ID id, Long version, Boolean isLogicDelete);

	/**
	 * 根据Id更新
	 * 
	 * @param id
	 * @param v
	 * @return
	 */
	public void updateById(ID id, Long version, V update);
	
	/**
	 * 查询总条数
	 * 
	 * @param condition
	 * @return
	 */
	public long findByCount(V condition);

	/**
	 * 分页查询
	 * 
	 * @param condition
	 * @param size
	 * @param minId
	 * @param sort
	 * @return
	 */
	public Page<V> findByPage(V condition, int size, ID minId, Sort sort);

	/**
	 * 查询总条数
	 * 
	 * @param specification
	 * @return
	 */
	public long findByCount(Specification<V> specification);

	/**
	 * 分页查询
	 * 
	 * @param specification
	 * @param size
	 * @param minId
	 * @param sort
	 * @return
	 */
	public Page<V> findByPage(Specification<V> specification, int size, ID minId, Sort sort);
}
