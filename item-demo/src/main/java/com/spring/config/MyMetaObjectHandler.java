package com.spring.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        strictFillStrategy(metaObject,"createdAt", LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictFillStrategy(metaObject,"updatedAt", LocalDateTime.now());
    }

    private void strictFillStrategy(MetaObject metaObject, String updatedAt, LocalDateTime now) {
        
    }
}
