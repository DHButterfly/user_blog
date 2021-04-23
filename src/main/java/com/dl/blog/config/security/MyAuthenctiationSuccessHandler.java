package com.dl.blog.config.security;

import com.dl.blog.pojo.BlogUser;
import com.dl.blog.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

//登录用户认证通过后，显示登录成功页面前，做的操作。
@Component
public class MyAuthenctiationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenctiationSuccessHandler.class);

    // key为sessionId，value为HttpSession，使用static，定义静态变量，使之程序运行时，一直存在内存中。
    // 保存所有已经登录用户的会话（每个浏览器一个会话）
    public static HashMap<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

    @Autowired
    // @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    // @Bean(name="sessionRegistry",value="sessionRegistry")
    @Bean
    // @Bean(name="sessionRegistry")
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        // 1.登录认证成功后，获取用户名
        //（只能在认证成功通过后，才能获得sc，不然在CustomUserService implements UserDetailsService的loadUserByUsername方法中是第二次才能获取到）
        SecurityContext sc = SecurityContextHolder.getContext();
        String currentuser = ((BlogUser) sc.getAuthentication().getPrincipal()).getUsername();
        logger.info("当前登录用户：" + currentuser);


        // 2.先判断用户是否重复登录
        Iterator<Entry<String, HttpSession>> iterator = sessionMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, HttpSession> entry = iterator.next();
            HttpSession session = entry.getValue();
            // 2.1 判断session中所包含的用户名称是否有当前登录用户
            String username = SessionUtil.getUserName(session);
            if (currentuser.equals(username)) {
                logger.info("用户：" + currentuser + "已经在其它地方登录过，将踢除！");
                SessionUtil.expireSession(session);
                logger.info("删除的会话："+entry.getKey());
                // 2.2 从sessionMap中踢除会话
                iterator.remove();
                // 2.3 从sessionRegistry中踢除会话
                sessionRegistry.removeSessionInformation(session.getId());
            }
        }

		/*//以下这种方法会引起java.util.ConcurrentModificationException: null 错误，    HashMap
		// 2.先判断用户是否重复登录
		for (Entry<String, HttpSession> entry : sessionMap.entrySet()) {
			HttpSession session = entry.getValue();
			// 2.1 判断session中所包含的用户名称是否有当前登录用户
			String username = SessionUtil.getUserName(session);
			if (currentuser.equals(username)) {
				logger.info("用户：" + currentuser + "已经在其它地方登录过，将踢除！");
				SessionUtil.expireSession(session);
				logger.info(entry.getKey());
				sessionMap.remove(entry.getKey());//这里会引起同步错误
				sessionRegistry.removeSessionInformation(session.getId());
			}
		}*/

        // 3.将当前session保存到sessionMap中
        logger.info("将当前会话:" + request.getSession().getId() + "，保存到sessionMap");
        sessionMap.put(request.getSession().getId(), request.getSession());
        for (Entry<String, HttpSession> entry : sessionMap.entrySet()) {
            logger.info("显示已经保存的sessionMap:Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

        // 4.打印所有认证通过的用户（包含重复登录的,不过上面已经踢除了）
        List<Object> principals = sessionRegistry.getAllPrincipals();
        List<String> usersNamesList = new ArrayList<String>();
        for (Object principal: principals) {
            if (principal instanceof BlogUser) {
                usersNamesList.add(((BlogUser) principal).getUsername());
            }
        }
        logger.info("已经认证通过的用户数:"+usersNamesList.size()+"，     已经认证通过用户："+usersNamesList.toString());


        // response.sendRedirect("/");
        super.onAuthenticationSuccess(request, response, authentication);
    }

}

