package com.natsumes.wezard.interceptor;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.enums.ResponseEnum;

public class SentinelHandlers {

    // 整体要求和当时Hystrix一样，这里还需要在形参中添加BlockException参数，用于接收异常
    // 注意：方法是静态的
    public static Response handleException(Integer categoryId, Integer pageNum, Integer pageSize, BlockException blockException) {
        if (blockException instanceof DegradeException) {
            return Response.error(ResponseEnum.SYSTEM_BLOCK_DEGRADING);

        } else if (blockException instanceof FlowException) {
            return Response.error(ResponseEnum.SYSTEM_BLOCK_FLOWING);
        }
        throw new RuntimeException(ResponseEnum.SYSTEM_ERROR.getDesc());
    }


    public static Response handleError(Integer categoryId,Integer pageNum,  Integer pageSize) {
        return Response.error(ResponseEnum.SYSTEM_ERROR);
    }
}
