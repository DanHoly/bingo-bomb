package org.bingo.bomb.chaos.shiro.filter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.bingo.bomb.chaos.rpc.api.system.IUserRpcService;
import org.bingo.bomb.chaos.shiro.constants.ShiroConstans;

public class SysUserFilter extends PathMatchingFilter {
	
	@Resource(name = "userRpcService")
	private IUserRpcService userRpcService;
	
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(ShiroConstans.CURRENT_USER, userRpcService.findByUserName(username));
       
        return true;
    }
    
	public void setUserRpcService(IUserRpcService userRpcService) {
		this.userRpcService = userRpcService;
	}
}
