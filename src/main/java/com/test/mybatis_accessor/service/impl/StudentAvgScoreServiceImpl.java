package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.StudentStatistics;
import com.test.mybatis_accessor.mapper.StudentAvgScoreMapper;
import com.test.mybatis_accessor.service.IStudentAvgScoreService;
import org.springframework.stereotype.Service;

@Service
public class StudentAvgScoreServiceImpl extends ServiceImpl<StudentAvgScoreMapper, StudentStatistics> implements IStudentAvgScoreService {
}
