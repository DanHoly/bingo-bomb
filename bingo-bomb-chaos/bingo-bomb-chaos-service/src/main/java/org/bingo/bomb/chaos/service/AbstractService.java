package org.bingo.bomb.chaos.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.bingo.bomb.chaos.repository.domain.AbstractBaseEntity;
import org.bingo.bomb.chaos.repository.repository.PagingAndSortingRepository;
import org.bingo.bomb.chaos.service.ServiceResult.ServiceType;
import org.bingo.bomb.commons.exception.DataDeleteException;
import org.bingo.bomb.commons.exception.DataFindException;
import org.bingo.bomb.commons.exception.DataSaveException;
import org.bingo.bomb.commons.exception.DataUpdateException;
import org.bingo.bomb.commons.exception.NotSupportOperationException;
import org.bingo.bomb.commons.fix.SpringContextHolder;
import org.bingo.bomb.commons.utils.DigitConstant;
import org.bingo.bomb.commons.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 抽象服务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:16:29
 * @since JDK 1.7
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class AbstractService<E extends AbstractBaseEntity, ID extends Serializable, R extends JpaRepository<E, ID>> implements IService<E, ID>, InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private Object DELETE_LOCK = new Object();

	private Object UPDATE_LOCK = new Object();

	protected Class<E> entityClass;

	protected Class<ID> idClass;

	protected Class<R> repositoryClass;

	protected R repository;

	@Resource(name = "springContextHolder")
	protected SpringContextHolder springContextHolder;

	public void setSpringContextHolder(SpringContextHolder springContextHolder) {
		this.springContextHolder = springContextHolder;
	}

	/**
	 * 初始化仓库
	 * 
	 * @param repositoryName
	 */
	private void initRepository(String repositoryName) {
		if (repository == null && springContextHolder.hasBean(repositoryName)) {
			repository = springContextHolder.getBean(repositoryName, repositoryClass);
		}
	}

	/**
	 * 获得仓库名称
	 * 
	 * @return
	 */
	private String getRepositoryName() {
		return repositoryClass.getSimpleName().substring(0, 1).toLowerCase() + repositoryClass.getSimpleName().substring(1);
	}

	/**
	 * 初始化类
	 */
	private void initClass() {
		List<Class<?>> list = Utils.genericClass(this.getClass(), AbstractService.class);
		entityClass = (Class<E>) list.get(0);
		idClass = (Class<ID>) list.get(1);
		repositoryClass = (Class<R>) list.get(2);
	}

	/**
	 * 初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		initClass();
		String repositoryName = getRepositoryName();
		logger.info("repository name:{},entity class:{},vo class:{},id class:{},repository class:{},", repositoryName, entityClass, idClass, repositoryClass);
		initRepository(repositoryName);
	}

	/**
	 * 保存
	 */
	@Override
	public void save(E e) {
		if (e.getId() == null) {
			e.setId(Utils.id());
		}
		e.setCreateTime(new Date());
		e.setModifyTime(new Date());
		e.setVersion(1L);
		E entity = Utils.newInstanceClass(entityClass);
		Utils.copyNotNullObject(e, entity);
		try {
			repository.saveAndFlush(entity);
		} catch (Exception ex) {
			e.setId(null);
			throw new DataSaveException(ex);
		}
	}

	/**
	 * 批量保存
	 */
	@Override
	public void save(List<E> list) {
		if (list != null && !list.isEmpty()) {
			for (E entity : list) {
				this.save(entity);
			}
		}
	}

	/**
	 * 保存或者更新
	 */
	@Override
	public ServiceResult<ID> saveOrUpdate(E e) {
		ServiceResult<ID> serviceResult = null;
		if (e.getId() != null) {
			this.updateById(idClass.cast(e.getId()), e.getVersion(), e);
			serviceResult = new ServiceResult<ID>();
			serviceResult.setId((ID) e.getId());
			serviceResult.setServiceType(ServiceType.UPDATE);
		} else {
			this.save(e);
			serviceResult = new ServiceResult<ID>();
			serviceResult.setId((ID) e.getId());
			serviceResult.setServiceType(ServiceType.SAVE);
		}
		return serviceResult;
	}

	/**
	 * 根据ID查找
	 */
	@Override
	@Transactional(readOnly = true)
	public E findById(ID id) {
		try {
			E entity = repository.findOne(id);
			return entity;
		} catch (Exception e) {
			throw new DataFindException(e);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public E findByCondition(E v) {
		E entity = Utils.newInstanceClass(entityClass);
		Example<E> example = Example.of(entity);
		return repository.findOne(example);
	}
	
	/**
	 * 根据ID删除,线程安全,并发不高,可以根据业务在repository中自己实现
	 */
	@Override
	public void deleteById(ID id, Long version, Boolean isLogicDelete) {
		try {
			if (isLogicDelete == null) {
				isLogicDelete = Boolean.TRUE;
			}
			synchronized (DELETE_LOCK) {
				E entity = Utils.newInstanceClass(entityClass);
				entity.setId(BigInteger.class.cast(id));
				entity.setVersion(version);
				Example<E> example = Example.of(entity);
				entity = repository.findOne(example);

				if (entity != null) {
					if (!isLogicDelete) {
						repository.delete(id);
						repository.flush();
					} else {
						entity.setActive(DigitConstant.SHORT_ZERO);
						entity.setVersion(entity.getVersion() + 1L);
						this.updateById(id, version, entity);
					}
				}
			}
		} catch (Exception e) {
			throw new DataDeleteException(e);
		}
	}

	/**
	 * 根据ID更新,线程安全,并发不高,可以根据业务在repository中自己实现
	 */
	@Override
	public void updateById(ID id, Long version, E update) {
		try {
			synchronized (UPDATE_LOCK) {
				E entity = Utils.newInstanceClass(entityClass);
				entity.setId(BigInteger.class.cast(id));
				entity.setVersion(version);
				entity.setActive(DigitConstant.SHORT_ONE);
				Example<E> example = Example.of(entity);
				entity = repository.findOne(example);
				if (entity != null) {
					update.setCreateTime(entity.getCreateTime());
					update.setModifyTime(new Date());
					update.setId(entity.getId());
					if (entity.getVersion() == 0) {
						update.setVersion(1L);
					} else {
						update.setVersion(entity.getVersion() + 1L);
					}
					Utils.copyNotNullObject(update, entity);
					repository.saveAndFlush(entity);
				}
			}
		} catch (Exception e) {
			throw new DataUpdateException(e);
		}
	}

	/**
	 * condition进行全等查询总条数
	 */
	@Override
	@Transactional(readOnly = true)
	public long findByCount(E condition) {
		E entity = Utils.newInstanceClass(entityClass);
		Utils.copyNotNullObject(condition, entity);
		Example<E> example = Example.of(entity);
		return repository.count(example);
	}

	/**
	 * condition进行全等分页查询
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<E> findByPage(E condition, int size, ID minId, Sort sort) {
		if (size < 1) {
			size = Utils.DEFAULT_SIZE;
		}
		if (minId == null) {
			minId = (ID) new BigInteger("0");
		}
		if (sort == null) {
			sort = new Sort(Direction.DESC, "id");
		} else {
			List<Order> orders = new ArrayList<Order>();
			Iterator<Order> iterator = sort.iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				if (!"id".equals(order.getProperty())) {
					orders.add(order);
				}
			}
			orders.add(0, new Order(Direction.DESC, "id"));
			sort = new Sort(orders);
		}
		if (PagingAndSortingRepository.class.isAssignableFrom(repositoryClass)) {
			PagingAndSortingRepository<E, ID> repository = PagingAndSortingRepository.class.cast(this.repository);
			E entity = Utils.newInstanceClass(entityClass);
			Utils.copyNotNullObject(condition, entity);
			Example<E> example = Example.of(entity);
			return repository.findAll(example, new PageRequest(Utils.DEFAULT_PAGE, size, sort), minId);
		}
		throw new NotSupportOperationException("please service Repository implement MaxIdPagingAndSortingRepository");
	}

	/**
	 * specification自定义条件查询总条数
	 */
	@Override
	@Transactional(readOnly = true)
	public long findByCount(Specification<E> specification) {
		if (PagingAndSortingRepository.class.isAssignableFrom(repositoryClass)) {
			PagingAndSortingRepository<E, ID> repository = PagingAndSortingRepository.class.cast(this.repository);
			return repository.count((Specification<E>) specification);
		}
		throw new NotSupportOperationException("please service Repository implement MaxIdPagingAndSortingRepository");
	}

	/**
	 * specification自定义条件分页查询
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<E> findByPage(Specification<E> specification, int size, ID minId, Sort sort) {
		if (size < 1) {
			size = Utils.DEFAULT_SIZE;
		}
		if (minId == null) {
			minId = (ID) new BigInteger("0");
		}
		if (sort == null) {
			sort = new Sort(Direction.DESC, "id");
		} else {
			List<Order> orders = new ArrayList<Order>();
			Iterator<Order> iterator = sort.iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				if (!"id".equals(order.getProperty())) {
					orders.add(order);
				}
			}
			orders.add(0, new Order(Direction.DESC, "id"));
			sort = new Sort(orders);
		}
		if (PagingAndSortingRepository.class.isAssignableFrom(repositoryClass)) {
			PagingAndSortingRepository<E, ID> repository = PagingAndSortingRepository.class.cast(this.repository);
			return repository.findAll((Specification<E>) specification, new PageRequest(Utils.DEFAULT_PAGE, size, sort), minId);
		}
		throw new NotSupportOperationException("please service Repository implement MaxIdPagingAndSortingRepository");
	}


}
