package com.esmc.mcnp.services.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.esmc.mcnp.repositories.base.BaseRepository;

@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T, K extends Serializable> implements BaseService<T, K>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Entity class of service.
     */
    protected final Class<T> entityClass;

    /**
     * Default constructor. Loads entity class from super service information.
     * It is used
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public BaseServiceImpl() {
        Class clazz = getClass();
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            clazz = clazz.getSuperclass();
        }
        Object o = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

        if (o instanceof TypeVariable) {
            this.entityClass = (Class<T>) ((TypeVariable) o).getBounds()[0];
        } else {
            this.entityClass = (Class<T>) o;
        }
    }

    /**
     * Repository object provided by spring data which makes querying and
     * operating on entities much easier.
     *
     * @return
     */
    protected abstract BaseRepository<T, K> getRepository();

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public T add(T entity) {
       return getRepository().save(entity);
        
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public T update(T entity) {
    	return getRepository().save(entity);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void deleteById(K id) {
        getRepository().deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void deleteMany(Iterable<K> ids) {
        for (K id : ids) {
            getRepository().deleteById(id);
        }
    }

    @Override
    public T findById(K id) {
        return getRepository().findOne(id);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public List<T> list() {
        return getRepository().findAll();
    }

}
