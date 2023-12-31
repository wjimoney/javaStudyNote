package com.heima.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.behavior.service.ApUnlikesBehaviorService;
import com.heima.common.redis.CacheService;
import com.heima.model.behavior.dtos.UnLikesBehaviorDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP不喜欢行为表 服务实现类
 * </p>
 *
 * @author itheima
 */
@Slf4j
@Service
public class ApUnlikesBehaviorServiceImpl implements ApUnlikesBehaviorService {

    @Autowired
    private CacheService cacheService;

    @Override
    public ResponseResult unLike(UnLikesBehaviorDto dto) {

        if (dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApUser user = AppThreadLocalUtils.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        if (dto.getType() == 0) { //不喜欢
            Object obj = cacheService.hGet("UNLIKE-BEHAVIOR-" + dto.getArticleId(), user.getId().toString());
            if(obj != null){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"已反对");
            }
            log.info("保存当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hPut("UNLIKE-BEHAVIOR-" + dto.getArticleId(),
                    user.getId().toString(),
                    JSON.toJSONString(dto));
        } else { //取消不喜欢
            log.info("删除当前key:{} ,{}, {}", dto.getArticleId(), user.getId(), dto);
            cacheService.hDelete("UNLIKE-BEHAVIOR-" + dto.getArticleId().toString(), user.getId().toString());
        }

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}