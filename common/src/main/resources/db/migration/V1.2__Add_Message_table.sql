USE natsume;

-- 用户表
CREATE TABLE tb_message
(
    `id`          int(11)  NOT NULL AUTO_INCREMENT COMMENT '留言表Id',
    `user_id`     int(11)  NOT NULL COMMENT '评论用户Id',
    `product_id`  int(11)  NOT NULL COMMENT '商品Id',
    `username`    varchar(50)       DEFAULT NULL COMMENT '用户名',
    `contacts`    varchar(50)       DEFAULT NULL COMMENT '联系人姓名',
    `phone`       varchar(50)       DEFAULT NULL COMMENT '联系人电话',
    `content`     varchar(50)       DEFAULT NULL COMMENT '评论内容(长度限制为140个中文汉字)',
    `give_like`   int(11)           DEFAULT 0 COMMENT '点赞总量',
    `status`      int(10)           DEFAULT 1 COMMENT '评论状态(正常默认 1，已删除 0)',
    `reply_id`    int(4)   NOT NULL COMMENT '回复评论id',
    `role`        int(4)   NOT NULL COMMENT '角色0-管理员, 1-普通用户',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;