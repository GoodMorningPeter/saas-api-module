package com.company.service;

import com.company.entity.Api;
import com.company.mapper.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {
    @Autowired
    private ApiMapper apiMapper;

    public List<Api> findAll(){
        return apiMapper.findAll();
    }

    public int addApi(Api api){
        return apiMapper.addApi(api);
    }

    public int updateApi(Api api){
        return apiMapper.updateApi(api);
    }

    public int deleteApi(Integer id){
        return apiMapper.deleteApi(id);
    }
}