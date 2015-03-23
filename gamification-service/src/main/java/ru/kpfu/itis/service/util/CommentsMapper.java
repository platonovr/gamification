package ru.kpfu.itis.service.util;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by Дамир on 08.02.2015.
 */
@Component("commentsMapper")
public class CommentsMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
//        factory.classMap(Comment.class, CommentDto.class)
//                .field("complaint.id", "complaintId")
//                .field("owner.name","ownerName")
//                .byDefault()
//                .register();
    }
}
