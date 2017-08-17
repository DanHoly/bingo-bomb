package org.bingo.bomb.chaos.bootstrap.fix;

import javax.persistence.spi.PersistenceUnitTransactionType;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * 修复Spring对JPA事务配置
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午3:09:15
 * @since JDK 1.7
 */
public class FixSpringPersistenceTransactionPostProcessor implements PersistenceUnitPostProcessor {

	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		pui.setTransactionType(PersistenceUnitTransactionType.JTA);
	}

}
