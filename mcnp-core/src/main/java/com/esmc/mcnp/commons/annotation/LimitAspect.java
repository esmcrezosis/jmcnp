package com.esmc.mcnp.commons.aop;

import com.esmc.mcnp.McnpException;
import com.esmc.mcnp.commons.util.IPUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Limite de courant AOP
 */
@Aspect
@Configuration
public class LimitAspect {

    /**
     * Différents compartiments de jetons sont divisés en fonction de l'adresse IP et le cache est automatiquement nettoyé chaque jour. Seuls 6 jetons sont émis par seconde
     */
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) {
                    return RateLimiter.create(6);
                }
            });

    /**
     * Point de coupure de la couche de service
     */
    @Pointcut("@annotation(com.esmc.mcnp.commons.aop.ServiceLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
        ServiceLimit.LimitType limitType = limitAnnotation.limitType();
        String key = limitAnnotation.key();
        Object obj;
        try {
            if(limitType.equals(ServiceLimit.LimitType.IP)){
                key = IPUtils.getIpAddr();
            }
            RateLimiter rateLimiter = caches.get(key);
            Boolean flag = rateLimiter.tryAcquire();
            if(flag){
                obj = joinPoint.proceed();
            }else{
                throw new McnpException("Camarade, vous visitez trop souvent");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new McnpException("Le système est anormal, veuillez contacter l'administrateur");
        }
        return obj;
    }
}