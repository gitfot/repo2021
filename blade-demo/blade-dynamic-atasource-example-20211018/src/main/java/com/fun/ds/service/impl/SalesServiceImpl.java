package com.fun.ds.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fun.ds.entity.Sales;
import com.fun.ds.mapper.SalesMapper;
import com.fun.ds.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * (sales)表服务实现类
 *
 * @author zz
 * @since 2021-10-18 18:01:23
 */
@Service
@RequiredArgsConstructor
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

}
