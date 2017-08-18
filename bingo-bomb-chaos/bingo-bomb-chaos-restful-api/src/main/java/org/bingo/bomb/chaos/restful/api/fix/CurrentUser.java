package org.bingo.bomb.chaos.restful.api.fix;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bingo.bomb.chaos.shiro.constants.ShiroConstans;

/**
 * 绑定当前登录的用户
 * @author jiangchangcheng  
 * @date 2017年8月18日 下午3:15:31
 * @since JDK 1.7
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    /**
     * 当前用户在request中的名字
     * @return
     */
    String value() default ShiroConstans.CURRENT_USER;

}
