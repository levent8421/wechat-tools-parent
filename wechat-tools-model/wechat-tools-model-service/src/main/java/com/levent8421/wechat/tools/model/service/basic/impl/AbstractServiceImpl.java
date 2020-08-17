package com.levent8421.wechat.tools.model.service.basic.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.levent8421.wechat.tools.commons.entity.AbstractEntity;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.commons.exception.ResourceNotFoundException;
import com.levent8421.wechat.tools.model.repository.basic.AbstractMapper;
import com.levent8421.wechat.tools.model.service.basic.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by 郭文梁 2019/5/18 0018 11:18
 * AbstractServiceImpl
 * 基础业务行为实现
 *
 * @author 郭文梁
 * @data 2019/5/18 0018
 */
@Service
@Slf4j
public abstract class AbstractServiceImpl<T extends AbstractEntity> implements AbstractService<T> {
    private final AbstractMapper<T> mapper;
    private final Class<? extends AbstractEntity> entityClass;

    public AbstractServiceImpl(AbstractMapper<T> mapper) {
        this.mapper = mapper;
        entityClass = obtainEntityClass();
    }

    @Override
    public List<T> allByAddressIndexId(Integer addressIndexId) {
        return null;
    }

    /**
     * 获取实际的泛型类型
     *
     * @return 泛型类型
     */
    @SuppressWarnings("unchecked")
    private Class<? extends AbstractEntity> obtainEntityClass() {
        Type superclass = getClass().getGenericSuperclass();
        while (!(superclass instanceof ParameterizedType)) {
            if (superclass instanceof Class) {
                Class<?> clazz = (Class<?>) superclass;
                superclass = clazz.getGenericSuperclass();
            } else {
                throw new IllegalArgumentException("The superclass of service must be a Adapter or BaseServiceImpl");
            }
        }
        ParameterizedType type = (ParameterizedType) superclass;
        Type typeArgument = type.getActualTypeArguments()[0];
        return (Class<? extends AbstractEntity>) typeArgument;
    }

    @Override
    public T get(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    @NotNull
    public T require(Integer id) {
        T entity = get(id);
        if (entity == null) {
            throw new ResourceNotFoundException(entityClass, id);
        }
        return entity;
    }

    @Override
    public List<T> all() {
        return mapper.selectAll();
    }

    @Override
    public PageInfo<T> list(int page, int rows) {
        return PageHelper.startPage(page, rows).doSelectPageInfo(mapper::selectAll);
    }

    @Override
    public T updateById(T o) {
        o.touch();
        int col = mapper.updateByPrimaryKey(o);
        if (col != 1) {
            throw new InternalServerErrorException("Unable to update entity:" + o);
        }
        return o;
    }

    @Override
    public T save(T o) {
        o.init();
        int col = mapper.insert(o);
        if (col != 1) {
            throw new InternalServerErrorException("Could not save entity:" + o);
        }
        return o;
    }

    @Override
    public List<T> save(List<T> os) {
        if (os == null || os.size() <= 0) {
            return null;
        }
        os = os.stream().peek(AbstractEntity::init).collect(Collectors.toList());
        int col = mapper.insertList(os);
        if (col == os.size()) {
            return os;
        } else {
            throw new InternalServerErrorException("Save error: length of expect to save is " +
                    os.size() + ", but Actual storage length is " + col + ", data: " + os);
        }
    }

    @Override
    public List<T> findByQuery(T query) {
        if (query != null) {
            query.setDeleted(false);
        }
        return mapper.select(query);
    }

    @Override
    public T findOneByQuery(T query) {
        if (query != null) {
            query.setDeleted(false);
        }
        return mapper.selectOne(query);
    }

    @Override
    public void deleteById(Integer id) {
        int rows = mapper.deleteByPrimaryKey(id);
        log.debug("Delete {} where id={}", entityClass, id);
        if (rows > 1) {
            throw new InternalServerErrorException("Delete Error: result is " + rows);
        }
        if (rows != 1) {
            throw new InternalServerErrorException("Error on delete [" + entityClass + "], result rows is " + rows);
        }
    }

    @Override
    public int count() {
        return count(null);
    }

    @Override
    public int count(T query) {
        return mapper.selectCount(query);
    }

    @Override
    public boolean exists(T query) {
        return count(query) > 0;
    }


    @Override
    public int delete(T query) {
        return mapper.delete(query);
    }

    @Override
    public boolean removeById(Integer id) {
        @NotNull T entity = require(id);
        entity.setDeleted(true);
        T res = updateById(entity);
        return res != null;
    }

    @Override
    public int remove(T query) {
        return (int) findByQuery(query)
                .stream()
                .peek(e -> e.setDeleted(true))
                .filter(e -> updateById(e) != null)
                .count();
    }

    @Override
    public boolean existsById(Integer id) {
        return mapper.existsWithPrimaryKey(id);
    }

    @Override
    public void requireExistsById(Integer id) {
        if (!existsById(id)) {
            throw new ResourceNotFoundException(entityClass, id);
        }
    }
}
