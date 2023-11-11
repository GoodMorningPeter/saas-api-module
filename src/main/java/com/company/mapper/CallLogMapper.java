package com.company.mapper;

import com.company.entity.CallLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CallLogMapper {
    List<CallLog> findAll();
    boolean insertLog(CallLog calllog);
}
