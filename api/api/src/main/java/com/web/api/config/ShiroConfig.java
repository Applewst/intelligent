package com.web.api.config;


import com.web.api.config.impl.JwtTokenManager;
import com.web.api.config.impl.ShiroSessionManager;
import com.web.api.filter.JwtAuthcFilter;
import com.web.api.filter.JwtRolesFilter;
import jakarta.servlet.Filter;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Log4j2
public class ShiroConfig {

    @Autowired
    private JwtTokenManager jwtTokenManager;

    //创建权限管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm,
                                                                  DefaultWebSessionManager sessionManager) {
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

    //AOP增强(使用注解鉴权方式)
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 配合DefaultAdvisorAutoProxyCreator事项注解权限校验
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager
                                                                                           defaultWebSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 封装自定义过滤器
     * @return
     */
    private Map<String, Filter> filters(){
        Map<String, Filter> filters = new HashMap<>();
        //<标记,过滤器对象>
        filters.put("jwt-authc",new JwtAuthcFilter(jwtTokenManager));   //jwt认证
        filters.put("jwt-roles",new JwtRolesFilter(jwtTokenManager));   //jwt角色认证 and关系
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

        filterChainDefinitionMap.put("/news","anon");
        filterChainDefinitionMap.put("/register","anon"); //注册接口可以匿名访问
        filterChainDefinitionMap.put("/login","anon");  //任何人都可以访问
        filterChainDefinitionMap.put("/static/**","anon"); //静态资源可以匿名访问
        filterChainDefinitionMap.put("/admin","jwt-roles[admin]"); //必须拥有admin角色才可以访问


        //载入过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

}











