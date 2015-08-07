package ru.kpfu.itis.model.listener;

import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import ru.kpfu.itis.model.BaseLongIdEntity;

public class SaveOrUpdateDateListener extends DefaultSaveOrUpdateEventListener {

//    static Logger logger = Logger.getLogger(SaveOrUpdateDateListener.class);

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {
//        logger.debug("Entering onSaveOrUpdate()");
        if (event.getObject() instanceof BaseLongIdEntity) {
            BaseLongIdEntity record = (BaseLongIdEntity) event.getObject();
            record.prePersist();
        }
        super.onSaveOrUpdate(event);
    }
}