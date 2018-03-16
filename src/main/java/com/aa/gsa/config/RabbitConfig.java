package com.aa.gsa.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aa.gsa.properties.RunCompareProperties;
import com.aa.gsa.properties.MessagingProperties;

@Configuration
@EnableRabbit
public class RabbitConfig {

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private RabbitAdmin rabbitAdmin;

	@Autowired
	private MessagingProperties props;

	@Autowired
	private RunCompareProperties cppCompareProps;
	
	@Bean
	public RabbitTemplate template() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setConnectionFactory(connectionFactory);
		template.setMessageConverter(jackson2MessageConverter());
		return template;
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public DirectExchange runExchange() {
		return new DirectExchange(props.getRunExchange());
	}

	@Bean
	public Queue runQueue() {
		return new Queue(props.getRunQueue(), true);
	}

	@Bean
	public Queue statusQueue() {
		return new Queue(props.getStatusQueue(), true);
	}

	@Bean
	public Binding runBinding() {
		return BindingBuilder.bind(runQueue()).to(runExchange()).with(props.getRunRoutingKey());
	}	

	@Bean
	public Binding statusBinding() {
		return BindingBuilder.bind(statusQueue()).to(runExchange()).with(props.getStatusRoutingKey());
	}

	@Bean
	public Queue execStatusQueue() {
		return rabbitAdmin.declareQueue();
	}

	@Bean
	public Binding declareBindingForRunLogMessages() {
		Binding binding = BindingBuilder.bind(execStatusQueue()).to(fanoutExchange());
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		FanoutExchange fanoutExchange = new FanoutExchange(props.getLogExchange(), true, false);
		rabbitAdmin.declareExchange(fanoutExchange);
		return fanoutExchange;
	}
	
	@Bean
	public FanoutExchange runStatusUpdateExchange() {
		FanoutExchange fanoutExchange = new FanoutExchange(props.getRunStatusUpdateExchange(), true, false);
		rabbitAdmin.declareExchange(fanoutExchange);
		return fanoutExchange;
	}

	@Bean
	public Jackson2JsonMessageConverter jackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	//CPP Compare Configuration
	@Bean
	public DirectExchange cppCompareExchange() {
		return new DirectExchange(cppCompareProps.getMessaging().getExchangeName());
	}

	@Bean
	public Queue cppCompareSubmitQueue() {
		return new Queue(cppCompareProps.getMessaging().getSubmitQueue(), true);
	}

	@Bean
	public Queue cppCompareStatusQueue() {
		return new Queue(cppCompareProps.getMessaging().getStatusQueue(), true);
	}

	@Bean
	public Binding cppCompareSubmitBinding() {
		return BindingBuilder.bind(cppCompareSubmitQueue()).to(cppCompareExchange()).with(cppCompareProps.getMessaging().getSubmitRoutingKey());
	}	

	@Bean
	public Binding cppCompareStatusBinding() {
		return BindingBuilder.bind(cppCompareStatusQueue()).to(cppCompareExchange()).with(cppCompareProps.getMessaging().getStatusRoutingKey());
	}
}
