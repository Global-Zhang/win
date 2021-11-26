package com.hereWeGo.portal.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Configuration
public class RabbitmqConfig {

	/**
	 * 申明队列
	 * @return
	 */
	@Bean
	public Queue queue(){
		return new Queue("smsQueue");
	}

	/**
	 * 申明交换机
	 * @return
	 */
	@Bean
	public TopicExchange topicExchange(){
		return new TopicExchange("smsExchange");
	}

	/**
	 * 将队列绑定到交换机
	 * @return
	 */
	@Bean
	public Binding binding(){
		return BindingBuilder.bind(queue()).to(topicExchange()).with("*.sms");
	}

}