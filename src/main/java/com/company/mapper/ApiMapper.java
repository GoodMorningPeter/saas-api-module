package com.company.mapper;

import com.company.entity.Api;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ApiMapper {
    List<Api> findAll();

    int addApi(Api api);

    int updateApi(Api api);

    int deleteApi(Integer id);
}
