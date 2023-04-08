package com.test.mybatis_accessor.component;

import com.circustar.mybatis_accessor.annotation.event.IUpdateEvent;
import com.circustar.mybatis_accessor.class_info.DtoClassInfo;
import com.circustar.mybatis_accessor.listener.ExecuteTiming;
import com.circustar.mybatis_accessor.listener.event.property_change.PropertyChangeEventModel;
import com.circustar.mybatis_accessor.provider.command.IUpdateCommand;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "executeUpdateBean2")
public class ExecuteUpdateBean2  implements IUpdateEvent<PropertyChangeEventModel> {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public ExecuteTiming getDefaultExecuteTiming() {
        return ExecuteTiming.AFTER_ENTITY_UPDATE;
    }

    @Override
    public IUpdateCommand.UpdateType[] getDefaultUpdateTypes() {
        return new IUpdateCommand.UpdateType[]{IUpdateCommand.UpdateType.UPDATE, IUpdateCommand.UpdateType.INSERT, IUpdateCommand.UpdateType.DELETE};
    }

    @Override
    public void exec(PropertyChangeEventModel model, IUpdateCommand.UpdateType updateType
            , DtoClassInfo dtoClassInfo, List<Object> dtoList, String updateEventLogId) {
        this.name = dtoClassInfo.getDtoClass().getName() + "_" + updateType.getName();
        System.out.println(this.name);
    }
}
