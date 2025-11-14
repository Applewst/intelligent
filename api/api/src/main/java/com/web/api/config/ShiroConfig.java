package com.web.api.config;

import com.web.api.config.impl.JwtTokenManager;
import com.web.api.config.impl.ShiroSessionManager;
import com.web.api.filter.NewHttpMethodFilter;
import jakarta.servlet.Filter;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Askr-Yggdrasill
 */
@Configuration
@Log4j2
public class ShiroConfig {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    //创建权限管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联userRealm
        defaultWebSecurityManager.setRealm(userRealm);
        SecurityUtils.setSecurityManager(defaultWebSecurityManager);

        //关闭session
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(defaultSubjectDAO);
        return defaultWebSecurityManager;
    }

    //剔除cookie
    @Bean(name="sessionManager")
    public DefaultWebSessionManager sessionManager(){
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        //关闭会话更新
        sessionManager.setSessionValidationSchedulerEnabled(false);
        //取消cookie
        sessionManager.setSessionIdCookieEnabled(false);
        //设置全局会话超时时间(ms -> 1h)
        sessionManager.setGlobalSessionTimeout(60*60*1000);
        return sessionManager;
    }

    //创建自定义Realm对象
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }


    //创建生命周期的管理
    @Bean(name="lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 封装自定义过滤器
     * @return Map<String, Filter>过滤器集合
     */
    private Map<String, Filter> filters(){
        //<标记,过滤器对象>
        Map<String, Filter> filters = new HashMap<>();

        //创建自定义过滤器对象
        //管理员所有权:
        NewHttpMethodFilter adminAll = new NewHttpMethodFilter();
        adminAll.setAllowedRole("admin","admin","admin","admin",jwtTokenManager);

        //游客仅get
        NewHttpMethodFilter allGet = new NewHttpMethodFilter();
        allGet.setAllowedRole("","admin","admin","admin",jwtTokenManager);
        //仅用户get
        NewHttpMethodFilter onlyUserGet = new NewHttpMethodFilter();
        onlyUserGet.setAllowedRole("user,admin","admin","admin","admin",jwtTokenManager);

        //载入自定义过滤器
        filters.put("admin-all",adminAll);
        filters.put("all-get",allGet);
        filters.put("user-get",onlyUserGet);
        return filters;
    }

    //Shiro过滤器管理
    @Bean(name="shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean (DefaultWebSecurityManager defaultWebSecurityManager) {
        //设置安全管理器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //生效自定义过滤器
        shiroFilterFactoryBean.setFilters(filters());

        //添加shiro内置过滤器链
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        //设置过滤器链
        //用户信息
        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/user/**","admin-all");
        //毕业生信息
        filterChainDefinitionMap.put("/graduates/**","all-get");
        //在读学生信息
        filterChainDefinitionMap.put("/students/**","all-get");
        //教师信息
        filterChainDefinitionMap.put("/teacher/**","all-get");
        //科研动态信息
        filterChainDefinitionMap.put("/news/**","all-get");
        //团队活动信息
        filterChainDefinitionMap.put("/events/**","all-get");
        //研究方向信息
        filterChainDefinitionMap.put("/search/**","all-get");
        //资源共享
        filterChainDefinitionMap.put("/resources/**","user-get");
        //论文管理
        filterChainDefinitionMap.put("/papers/**","all-get");
        //学生获奖
        filterChainDefinitionMap.put("/search/awards/**","all-get");
        //团队介绍
        filterChainDefinitionMap.put("/introduce/**","all-get");
        //联系我们
        filterChainDefinitionMap.put("/contact/**","all-get");
        //照片墙
        filterChainDefinitionMap.put("/team/shoots/**","all-get");
        //学生发展
        filterChainDefinitionMap.put("/team/developments/**","all-get");
        //上传接口
        filterChainDefinitionMap.put("/upload/**","admin-all");

        //载入过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

}











