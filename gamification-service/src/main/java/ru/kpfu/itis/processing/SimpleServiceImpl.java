package ru.kpfu.itis.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.SimpleDao;
import ru.kpfu.itis.model.IdentifiedEntity;

import java.io.Serializable;
import java.util.List;

@Service("simpleService")
public class SimpleServiceImpl implements SimpleService {

    @Autowired
    private SimpleDao simpleDao;

    @Override
    @Transactional(readOnly = true)
    public <D> D findById(Class<D> aClass, Serializable aId) {
        return simpleDao.findById(aClass, aId);
    }

    @Override
    @Transactional()
    public <D extends IdentifiedEntity> Serializable save(D aEntity) {
        return simpleDao.save(aEntity);
    }

    @Override
    public <D extends IdentifiedEntity> void saveOrUpdate(D aEntity) {
        simpleDao.saveOrUpdate(aEntity);
    }

    @Override
    public <D extends IdentifiedEntity> void delete(D aEntity) {
        simpleDao.delete(aEntity);
    }

    @Override
    public <E extends IdentifiedEntity> E load(Class<E> aClass, Serializable aId) {
        return simpleDao.loadById(aClass, aId);
    }

    @Override
    @Transactional(readOnly = true)
    public <D> List<D> fetchAll(Class<D> aClass) {
        return simpleDao.fetchAll(aClass);
    }

}
