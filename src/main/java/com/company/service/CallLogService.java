package com.company.service;

import com.company.entity.CallLog;
import com.company.mapper.CallLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
@Service
public class CallLogService {
    @Autowired
    private CallLogMapper callLogMapper;
    public List<CallLog> findAll(){
        List<CallLog> all = callLogMapper.findAll();
        return all;
    }
    public boolean insertLog(CallLog calllog){
        boolean result = callLogMapper.insertLog(calllog);
        return result;
    }
}
