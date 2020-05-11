package com.free.yrl.demospringbootpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@Configuration
@EnableSwagger2
@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoSpringbootPaymentApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoSpringbootPaymentApplication.class, args);

	}

	/*配置跨域访问的过滤器*/
	@Bean
	public CorsFilter corsFilter() {

		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);
		return new CorsFilter(configSource);

	}

}
