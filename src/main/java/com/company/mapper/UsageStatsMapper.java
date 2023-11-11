package com.company.mapper;

import com.company.entity.UsageStats;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UsageStatsMapper {
    List<UsageStats> findAll();
    List<UsageStats> updateStats();
}
