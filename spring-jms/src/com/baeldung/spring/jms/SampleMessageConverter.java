package com.baeldung.spring.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class SampleMessageConverter implements MessageConverter {

    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        Employee person = (Employee) object;
        MapMessage message = session.createMapMessage();
        message.setString("name", person.getName());
        message.setInt("age", person.getAge());
        return message;
    }

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        Employee person = new Employee(mapMessage.getString("name"), mapMessage.getInt("age"));
        return person;
    }

}
