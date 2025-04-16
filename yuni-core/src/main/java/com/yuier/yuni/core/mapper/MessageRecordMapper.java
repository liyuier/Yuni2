package com.yuier.yuni.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuier.yuni.core.domain.entity.MessageRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * (MessageRecord)表数据库访问层
 *
 * @author liyuier
 * @since 2025-04-16 23:21:58
 */
@Mapper
public interface MessageRecordMapper extends BaseMapper<MessageRecordEntity> {

}

