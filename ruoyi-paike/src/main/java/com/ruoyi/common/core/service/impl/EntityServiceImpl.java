package com.ruoyi.common.core.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.LongEntity;
import com.ruoyi.common.core.service.EntityService;
import com.ruoyi.common.core.vo.ImportEntityData;
import com.ruoyi.common.exception.ServiceException;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EntityServiceImpl<M extends BaseMapper<T>, T extends LongEntity>
        extends ServiceImpl<M, T> implements EntityService<T> {

    public boolean saveOrUpdate(T entity) {
        if (entity.getId() == null) {
            return save(entity);
        } else {
            return updateById(entity);
        }
    }

    @Transactional
    public String importEntity(List<T> sources) {
        ImportEntityData data = initImportEntityData(sources);
        return importEntity(data, sources);
    }

    @Transactional
    public String importEntity(ImportEntityData data, List<T> sources) {
        if (sources == null || sources.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        data.setTotal(sources.size());
        if (data.getDatas() != null && sources != null && !sources.isEmpty()) {
            data.setKeyFields(getKeyFields(sources.get(0)));
        }
        for (int i = 0; i < sources.size(); i++) {
            data.setIndex(i);
            T source = sources.get(i);
            try {
                importEntity(source);
                importEntity(data, source);
                data.success();
            } catch (Exception e) {
                data.failure();
                String msg = "<br/>第" + (i + 2) + "行导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(e.getMessage(), e);
            }
        }
        if (data.getFailureNum() > 0) {
            failureMsg.insert(0, "导入失败共 " + data.getFailureNum() + " 条数据格式不正确，错误如下：");
//			failureMsg.insert(0, "导入成功共 " + data.getSuccessNum() + " 条。<br/>");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + sources.size() + " 条。");
            saveBatch(data.getInserts());
            updateBatchById(data.getUpdates());
        }
        return successMsg.toString();
    }

    protected ImportEntityData initImportEntityData(List<T> sources) {
        return new ImportEntityData();
    }

    protected void importEntity(ImportEntityData data, T source) {
        importCheckExists(data, source);
        if (source.getId() == null) {
            data.getInserts().add(source);
        } else {
            data.getUpdates().add(source);
        }
//		saveImportEntityData(data);
    }

    private void saveImportEntityData(ImportEntityData data) {
        if (data.getInserts().size() > data.getPoolSize()) {
            saveBatch(data.getInserts());
            data.getInserts().clear();
        }
        if (data.getUpdates().size() > data.getPoolSize()) {
            updateBatchById(data.getUpdates());
            data.getUpdates().clear();
        }
    }

    protected void importEntity(T source) {
    }

    protected void importCheckExists(ImportEntityData data, T source) {
        if (data.getDatas() == null) {
            return;
        }
        if (data.getKeyFields().isEmpty()) {
            return;
        }
        List<T> entities = (List<T>) data.getDatas().stream().filter(d -> {
            boolean result = true;
            for (Field field : data.getKeyFields()) {
                try {
                    if (!Objects.equals(field.get(d), field.get(source))) {
                        result = false;
                        break;
                    }
                } catch (IllegalAccessException e) {
                }
            }
            return result;
        }).collect(Collectors.toList());
        Assert.isTrue(entities.size() <= 1, "数据重复，无法导入");
        if (!entities.isEmpty()) {
            source.setId(entities.get(0).getId());
        }
    }

    private List<Field> getKeyFields(T source) {
        List<Field> keyFields = new ArrayList<>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(source.getClass().getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(source.getClass().getDeclaredFields()));
        for (Field field : tempFields) {
            if (field.isAnnotationPresent(Excel.class)) {
                Excel excel = field.getAnnotation(Excel.class);
                if (excel.isKey()) {
                    field.setAccessible(true);
                    keyFields.add(field);
                }
            }
        }
        return keyFields;
    }

    @SneakyThrows
    protected T getImportForm(T source) {
        boolean hasKey = false;
        T form = (T) source.getClass().newInstance();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(source.getClass().getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(source.getClass().getDeclaredFields()));
        for (Field field : tempFields) {
            if (field.isAnnotationPresent(Excel.class)) {
                Excel excel = field.getAnnotation(Excel.class);
                if (excel.isKey()) {
                    hasKey = true;
                    field.setAccessible(true);
                    field.set(form, field.get(source));
                }
            }
        }
        if (!hasKey) {
            return null;
        }
        return form;
    }

    protected List<T> listByIds(Long[] ids) {
        return super.listByIds(Arrays.asList(ids));
    }

    protected List<T> getByIds(Collection<? extends Serializable> idList) {
        return listByIds(idList);
    }
}
