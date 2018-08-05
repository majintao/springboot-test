/**  
 * All rights Reserved, Designed By 3氵哥
 * @Title:  Application.java   
 * @Package com.github.jtmark   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 3氵哥     
 * @date:   2018年8月5日 下午10:43:31   
 * @version V1.0 
 * @Copyright: 2018 . All rights reserved. 
 */
package com.github.jtmark;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**    
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 3氵哥
 * @date:   2018年8月5日 下午10:43:31   
 *     
 * @Copyright: 2018 . All rights reserved. 
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}