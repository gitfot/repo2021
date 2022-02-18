package org.fun.test.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.fun.test.biz.entity.Student;

/**
 * 学生表-测试(student)Mapper接口
 *
 * @author zz
 * @since 2022-02-15 23:10:19
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
