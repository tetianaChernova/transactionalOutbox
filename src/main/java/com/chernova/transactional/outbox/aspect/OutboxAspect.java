package com.chernova.transactional.outbox.aspect;

import com.chernova.transactional.outbox.annotation.WithTransactionalOutbox;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OutboxAspect {

	@Around("@annotation(withTransactionalOutboxAnnotation)")
	public Object withTransactionalOutbox(ProceedingJoinPoint joinPoint, WithTransactionalOutbox withTransactionalOutboxAnnotation)
			throws Throwable {
		System.out.println("Triggered join point with withTransactionalOutbox annotation");
		Object result = joinPoint.proceed();
		System.out.println("After proceeding joinPoint");
		return result;
	}
}
