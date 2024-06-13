package store.backendstore.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQSender {


	/// The rabbit template
	private final AmqpTemplate rabbitTemplate;

	/// Constructor injection
	RabbitMQSender(AmqpTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	/// The exchange
	@Value("${javainuse.rabbitmq.exchange}")
	private String exchange;

	/// The routing key
	@Value("${javainuse.rabbitmq.routingkey}")
	private String routingkey;

	/// Send a message to the queue
	public boolean send(Object company) {
		try {
			log.info("Message ton send >>>>>>>> " + company.toString());
			rabbitTemplate.convertAndSend(exchange, routingkey, company);
			log.info("Send msg =" + company);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}