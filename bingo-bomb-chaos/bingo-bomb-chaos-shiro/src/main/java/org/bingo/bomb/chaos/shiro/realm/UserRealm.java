package org.bingo.bomb.chaos.shiro.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.bingo.bomb.chaos.rpc.api.system.IUserRpcService;
import org.bingo.bomb.chaos.rpc.api.vo.system.UserRpcVo;
import org.bingo.bomb.commons.utils.DigitConstant;

public class UserRealm extends AuthorizingRealm {
	
	@Resource(name = "userRpcService")
	private IUserRpcService userRpcService;
	
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userRpcService.findRoles(userName));
        authorizationInfo.setStringPermissions(userRpcService.findPermissions(userName));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String userName = (String)token.getPrincipal();

        UserRpcVo user = userRpcService.findByUserName(userName);

        if(user == null) {
            throw new UnknownAccountException();
        }

        if(DigitConstant.SHORT_ZERO ==  user.getActive()) {
            throw new LockedAccountException(); 
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(),
                user.getPassword(), 
                ByteSource.Util.bytes(user.getUserName() + user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
    
	public void setUserRpcService(IUserRpcService userRpcService) {
		this.userRpcService = userRpcService;
	}
    
}
