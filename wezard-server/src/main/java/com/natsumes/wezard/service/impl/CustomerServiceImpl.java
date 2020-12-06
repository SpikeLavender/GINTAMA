package com.natsumes.wezard.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natsumes.wezard.config.WxConfig;
import com.natsumes.wezard.entity.form.WxMessageForm;
import com.natsumes.wezard.service.CustomerService;
import com.natsumes.wezard.wechat.AesException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private WxConfig wxConfig;

    @SneakyThrows
    @Override
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        List<String> strings = new ArrayList<>();
        strings.add(wxConfig.getToken());
        strings.add(timestamp);
        strings.add(nonce);

        String content = strings.stream().sorted().collect(Collectors.joining());
        byte[] md5Bytes  = MessageDigest.getInstance("SHA1").digest(content.getBytes());

        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        String res = hexValue.toString();

        return res.equalsIgnoreCase(signature);
    }

    @Override
    public String handleMessage(WxMessageForm wxMessageForm) throws AesException {


        log.info(wxMessageForm.getCreateTime());

        String encrypt = wxMessageForm.getEncrypt();

        //String decrypt = WeChatUtils.decrypt(appId, encrypt, "B7jKIdUdgr8Qgf/nV3VHOg==", AESKey);
        //WXBizMsgCrypt pc = new WXBizMsgCrypt(token, AESKey, appId);
        //String s = pc.DecryptMsg(null, null, null, encrypt);

        String respMessage = "success";
        //此处我默认为直接人工服务  可根据实际业务调整
        //小程序客服 文本信息
        try {
            return switchCustomerService(wxMessageForm);
//            String msgType = wxMessageForm.getMsgType();
//            //return switchCustomerService(wxMessageForm);
//            String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN_REDIS_KEY);
//            if(msgType.equals("text")){
//                if("人工服务".equals(wxMessageForm.getContent())){
//
//                    return switchCustomerService(wxMessageForm);
//                }
//
//                sendCustomerTextMessage(wxMessageForm.getFromUserName(), "你好，欢迎使用人工服务", accessToken);
//
//            }else if(msgType.equals("event")){//会话功能
////                String sessionFrom = (String) requestMap.get("SessionFrom");
////                logger.info("SessionFrom   SessionFrom"  +  sessionFrom);
////                int i = sessionFrom.indexOf("+");
////                String sessionFromFirst = "1";
////                String appId = wxXCXTempSend.APP_ID;
////                if( i > 0){
////                    sessionFromFirst = sessionFrom.substring(0, i); //标志位 1 2 3 4 5 6
////                    logger.info("SessionFrom   sessionFromFirst    "  +  sessionFromFirst);
////                    String sessionFromLast = sessionFrom.substring(i+1);  //{"appId":"","data":"test"}
////                    logger.info("SessionFrom   sessionFromLast     "  +  sessionFromLast);
////                    if(JSONObject.parseObject(sessionFromLast).get("appId") != null){
////                        appId = (String) JSONObject.parseObject(sessionFromLast).get("appId");
////                    }
////                    sendCustomerTextMessage(fromUserName,"你好，欢迎使用会话服务",accessToken);
////                }
//
//            }else if(msgType.equals("image")){
////                logger.info("公众号接受图片..........");
////                sendCustomerImageMessage(fromUserName,requestMap.get("MediaId"),accessToken);
//            }else{
//
//            }

        } catch (Exception e) {
            return respMessage;
        }
    }

    private void sendCustomerTextMessage(String fromUserName, String s, String accessToken) {

    }

    private String switchCustomerService(WxMessageForm wxMessageForm) throws JsonProcessingException {


        String toUserName = wxMessageForm.getToUserName();
        String fromUserName = wxMessageForm.getFromUserName();

        wxMessageForm.setFromUserName(toUserName);
        wxMessageForm.setToUserName(fromUserName);
        wxMessageForm.setMsgType("transfer_customer_service");
        wxMessageForm.setContent(wxMessageForm.getContent());

        String value = new ObjectMapper().writeValueAsString(wxMessageForm);
        log.info(value);
        return value;
    }
}
