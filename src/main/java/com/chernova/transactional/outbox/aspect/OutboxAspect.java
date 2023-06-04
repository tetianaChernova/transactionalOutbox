package com.chernova.transactional.outbox.aspect;

import java.lang.annotation.Annotation;

import com.chernova.transactional.outbox.annotation.OutboxEvent;
import com.chernova.transactional.outbox.data.Event;
import com.chernova.transactional.outbox.data.EventService;
import com.chernova.transactional.outbox.annotation.WithTransactionalOutbox;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxAspect {
	private final EventService eventService;
	private final ObjectMapper objectMapper;

	@Around(value = "@annotation(withTransactionalOutboxAnnotation)")
	public Object withTransactionalOutbox(ProceedingJoinPoint joinPoint, WithTransactionalOutbox withTransactionalOutboxAnnotation)
			throws Throwable {
		log.debug("Triggered join point with withTransactionalOutbox annotation");
		Object result = joinPoint.proceed();

		String jsonData = "{}";

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Annotation[][] annotations = signature.getMethod().getParameterAnnotations();
		Object[] args = joinPoint.getArgs();

		Object annotatedArgument;
		for (int i = 0; i < args.length; i++) {
			for (Annotation annotation : annotations[i]) {
				if (annotation instanceof OutboxEvent) {
					annotatedArgument = args[i];
					jsonData = objectMapper.writeValueAsString(annotatedArgument);
					break;
				}
			}
		}

		Event event = new Event();
		event.setData(jsonData);
		eventService.createEvent(event);
		log.debug("After proceeding joinPoint");
		return result;
	}
}
