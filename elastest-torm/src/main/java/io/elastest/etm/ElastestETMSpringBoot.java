package io.elastest.etm;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import io.elastest.etm.utils.UtilTools;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
public class ElastestETMSpringBoot extends AsyncConfigurerSupport {

	@Bean
	UtilTools utils() {
		UtilTools utils = new UtilTools();
		return utils;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedMethods("*").allowedOrigins("*");
			}
		};
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(6);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("TJob-");
		executor.initialize();
		return executor;
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(65500);
		container.setMaxBinaryMessageBufferSize(65500);
		return container;
	}
	
	public static void main(String[] args) throws Exception {
		new SpringApplication(ElastestETMSpringBoot.class).run(args);
	}
}
