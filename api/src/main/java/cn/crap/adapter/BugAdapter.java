package cn.crap.adapter;

import cn.crap.dto.BugDto;
import cn.crap.enu.BugPriority;
import cn.crap.enu.BugSeverity;
import cn.crap.enu.BugStatus;
import cn.crap.enu.BugTraceType;
import cn.crap.framework.MyException;
import cn.crap.model.Bug;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.LoginUserHelper;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class BugAdapter {
    public static BugDto getDto(Bug model, Module module, Project project){
        if (model == null){
            return null;
        }

        BugDto dto = new BugDto();
        BeanUtil.copyProperties(model, dto);
        dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        dto.setUpdateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getUpdateTime().getTime()));
        dto.setStatusStr(BugStatus.getNameByValue(model.getStatus()));
        dto.setPriorityStr(BugPriority.getNameByValue(model.getPriority()));
        dto.setSeverityStr(BugSeverity.getNameByValue(model.getSeverity()));
        dto.setTraceTypeStr(BugTraceType.getNameByValue(model.getTraceType()));

        if (module != null){
            dto.setModuleName(module.getName());
        }
        if (project != null){
            dto.setProjectName(project.getName());
        }

        return dto;
    }

    public static Bug getModel(String projectId, String moduleId) throws MyException {
        Assert.notNull(projectId, "projectId 不能为空");
        Bug model = new Bug();
        model.setProjectId(projectId);
        model.setName("新建缺陷");
        model.setContent("");
        model.setTraceType(BugTraceType.FUNCTION.getByteValue());
        model.setPriority(BugPriority.MIDDLE.getByteValue());
        model.setSeverity(BugSeverity.MAJOR.getByteValue());
        model.setStatus(BugStatus.NEW.getByteValue());
        model.setModuleId(moduleId);
        model.setCreatedBy(LoginUserHelper.getUser().getId());
        return model;
    }

    public static Bug getModel(BugDto dto){
        if (dto == null){
            return null;
        }
        Bug model = new Bug();
        BeanUtil.copyProperties(dto, model);

        return model;
    }

    public static List<BugDto> getDto(List<Bug> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<BugDto> dtos = new ArrayList<>();
        for (Bug model : models){
            dtos.add(getDto(model, null, null));
        }
        return dtos;
    }
}
