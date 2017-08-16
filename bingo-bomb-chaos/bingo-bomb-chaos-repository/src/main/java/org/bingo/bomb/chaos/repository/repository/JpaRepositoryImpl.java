package org.bingo.bomb.chaos.repository.repository;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.bingo.bomb.chaos.repository.domain.AbstractBaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Jpa仓库实现类
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午2:01:08
 * @since JDK 1.7
 */
public class JpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements JpaRepository<T, ID>, PagingAndSortingRepository<T, ID> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityManager entityManager;

	protected JpaEntityInformation<T, ?> entityInformation;

	protected String tableName;

	protected Class<T> domainClass;

	public JpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.entityInformation = entityInformation;
		this.domainClass = entityInformation.getJavaType();
		init();
	}

	public JpaRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
		this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
		this.domainClass = domainClass;
		init();
	}

	protected void init() {
		if (AbstractBaseEntity.class.isAssignableFrom(domainClass) && domainClass.isAnnotationPresent(Entity.class) && domainClass.isAnnotationPresent(Table.class)) {
			tableName = domainClass.getAnnotation(Table.class).name();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Example<T> example, Pageable pageable, ID minId) {
		ExampleSpecification<T> exampleSpecification = new ExampleSpecification<T>(example);
		if (minId == null || new BigInteger("0").equals(minId)) {
			TypedQuery<T> query = getQuery(exampleSpecification, domainClass, pageable);
			return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, domainClass, pageable, exampleSpecification);
		}
		ByMinIdSpecification<T, ID> byMaxIdSpecification = new ByMinIdSpecification<T, ID>(entityInformation, minId);
		ComposedSpecification<T> spec = new ComposedSpecification<T>(exampleSpecification, byMaxIdSpecification, CompositionType.AND);
		TypedQuery<T> query = getQuery(spec, domainClass, pageable);
		return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, domainClass, pageable, exampleSpecification);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findAll(Specification<T> specification, Pageable pageable, ID minId) {
		if (minId == null || new BigInteger("0").equals(minId)) {
			TypedQuery<T> query = getQuery(specification, domainClass, pageable);
			return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, domainClass, pageable, specification);
		}
		ByMinIdSpecification<T, ID> byMaxIdSpecification = new ByMinIdSpecification<T, ID>(entityInformation, minId);
		ComposedSpecification<T> spec = new ComposedSpecification<T>(specification, byMaxIdSpecification, CompositionType.AND);
		TypedQuery<T> query = getQuery(spec, domainClass, pageable);
		return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, domainClass, pageable, specification);
	}

	private static class ExampleSpecification<T> implements Specification<T> {

		private final Example<T> example;

		public ExampleSpecification(Example<T> example) {
			this.example = example;
		}

		@Override
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			return QueryByExamplePredicateBuilder.getPredicate(root, cb, example);
		}
	}

	private static final class ByMinIdSpecification<T, ID> implements Specification<T> {

		private JpaEntityInformation<T, ?> entityInformation;

		private ID id;

		public ByMinIdSpecification(JpaEntityInformation<T, ?> entityInformation, ID id) {
			this.entityInformation = entityInformation;
			this.id = id;
		}

		@SuppressWarnings("unchecked")
		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			Path<Number> path = (Path<Number>) root.get(entityInformation.getIdAttribute());
			return cb.lt(path, (Number) id);
		}
	}

	private static class ComposedSpecification<T> implements Specification<T>, Serializable {

		private static final long serialVersionUID = 1L;

		private final Specification<T> lhs;
		private final Specification<T> rhs;
		private final CompositionType compositionType;

		private ComposedSpecification(Specification<T> lhs, Specification<T> rhs, CompositionType compositionType) {

			Assert.notNull(compositionType, "CompositionType must not be null!");

			this.lhs = lhs;
			this.rhs = rhs;
			this.compositionType = compositionType;
		}

		public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

			Predicate otherPredicate = rhs == null ? null : rhs.toPredicate(root, query, builder);
			Predicate thisPredicate = lhs == null ? null : lhs.toPredicate(root, query, builder);

			return thisPredicate == null ? otherPredicate : otherPredicate == null ? thisPredicate : this.compositionType.combine(builder, thisPredicate, otherPredicate);
		}
	}

	private enum CompositionType {

		AND {
			@Override
			public Predicate combine(CriteriaBuilder builder, Predicate lhs, Predicate rhs) {
				return builder.and(lhs, rhs);
			}
		},

		OR {
			@Override
			public Predicate combine(CriteriaBuilder builder, Predicate lhs, Predicate rhs) {
				return builder.or(lhs, rhs);
			}
		};

		abstract Predicate combine(CriteriaBuilder builder, Predicate lhs, Predicate rhs);
	}

}
