package org.bingo.bomb.chaos.bootstrap.fix;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.bytesoft.compensable.CompensableBeanFactory;
import org.hibernate.service.jta.platform.internal.AbstractJtaPlatform;

/**
 * 修复事务
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午3:09:23
 * @since JDK 1.7
 */
public class FixHibernateForSpringJtaPlatform extends AbstractJtaPlatform {

	private static final long serialVersionUID = 1L;

	private static CompensableBeanFactory beanFactory;

	protected TransactionManager locateTransactionManager() {
		return beanFactory == null ? null : beanFactory.getTransactionManager();
	}

	protected UserTransaction locateUserTransaction() {
		return null;
	}

	public static CompensableBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(CompensableBeanFactory beanFactory) {
		FixHibernateForSpringJtaPlatform.beanFactory = beanFactory;
	}

}