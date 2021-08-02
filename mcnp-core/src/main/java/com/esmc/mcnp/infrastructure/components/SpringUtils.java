package com.esmc.mcnp.infrastructure.components;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * description: La classe d'outils à ressort est pratique pour obtenir des
 * grains dans un environnement de gestion sans ressort
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor {
	/**
	 * Contexte de l'application Spring
	 */
	private static ConfigurableListableBeanFactory beanFactory;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}

	/**
	 * Obtenir un objet
	 *
	 * @param name beanName
	 * @return Object Une instance d'un bean enregistré avec le nom donné
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}

	/**
	 * Obtenir des objets de type requiredType
	 *
	 * Classe @param clz
	 * 
	 * @return Bean
	 * @throws BeansException
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		return (T) beanFactory.getBean(clz);
	}

	/**
	 * Renvoie true si BeanFactory contient une définition de bean correspondant au
	 * nom donné
	 *
	 * @param name beanName
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	/**
	 * Déterminez si la définition de bean enregistrée avec le nom donné est un
	 * singleton ou un prototype. Si la définition du bean correspondant au nom
	 * donné n'est pas trouvée, une exception (NoSuchBeanDefinitionException) sera
	 * levée
	 *
	 * @param name beanName
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	/**
	 * @param name beanName
	 * @return Class le type d'objet enregistré
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	/**
	 * Si le nom du bean donné a des alias dans la définition du bean, ces alias
	 * sont renvoyés
	 *
	 * @param name beanName
	 * @return bean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}

	/**
	 * Obtenez l'objet proxy aop
	 *
	 * Objet proxy de l'appelant @param
	 * 
	 * @return Récupère l'objet proxy aop
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}
}
