package com.example.demo.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    
    @Bean
    public Queue hubQueue()
    {
        return new Queue("hub.queue",true);
    }

    @Bean
    public DirectExchange hubExchange()
    {
        return new DirectExchange("hub-exchange");
    }
    @Bean
    public Queue hubDeleteQueue()
    {
        return new Queue("hub.delete.queue",true);
    }

     @Bean
    public Binding hubBinding(@Qualifier("hubQueue") Queue hubQueue,DirectExchange hubExchange)
    {
        return BindingBuilder.bind(hubQueue).to(hubExchange).with("hub.queue");
    }

    @Bean
    public Binding huBinding2(@Qualifier("hubDeleteQueue") Queue hubDeleteQueue,DirectExchange hubExchange)
    {
        return BindingBuilder.bind(hubDeleteQueue).to(hubExchange).with("hub.delete.queue");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
