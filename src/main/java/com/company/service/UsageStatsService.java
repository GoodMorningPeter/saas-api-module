package com.company.service;


import com.company.entity.UsageStats;
import com.company.mapper.UsageStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsageStatsService {
    @Autowired
    private UsageStatsMapper usageStatsMapper;
    public List<UsageStats> findAll(){
        List<UsageStats> all = usageStatsMapper.findAll();
        return all;
    }
}
