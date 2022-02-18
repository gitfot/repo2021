package org.fun.test.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.fun.test.biz.entity.Student;
import org.fun.test.biz.mapper.StudentMapper;
import org.fun.test.biz.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * 学生表-测试(student)表服务实现类
 *
 * @author zz
 * @since 2022-02-15 23:10:18
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
