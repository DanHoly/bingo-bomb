package org.bingo.bomb.chaos.restful.api.user;

import java.util.List;
import java.util.Set;

import org.bingo.bomb.chaos.restful.api.fix.CurrentUser;
import org.bingo.bomb.chaos.rpc.api.system.IResourceRpcService;
import org.bingo.bomb.chaos.rpc.api.system.IUserRpcService;
import org.bingo.bomb.chaos.rpc.api.vo.system.ResourceRpcVo;
import org.bingo.bomb.chaos.rpc.api.vo.system.UserRpcVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;

@Controller
public class IndexController {

    @Reference
    private IUserRpcService userRpcService;
    
    @Reference
    private IResourceRpcService resourceRpcService;

    @RequestMapping("/")
    public String index(@CurrentUser UserRpcVo currentUser, Model model) {
        Set<String> permissions = userRpcService.findPermissions(currentUser.getUserName());
        List<ResourceRpcVo> menus = resourceRpcService.findMenus(permissions);
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


}
