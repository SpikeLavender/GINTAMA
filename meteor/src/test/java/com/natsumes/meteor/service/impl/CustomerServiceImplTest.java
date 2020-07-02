package com.natsumes.meteor.service.impl;

import com.natsumes.form.WxMessageForm;
import com.natsumes.meteor.MeteorApplicationTests;
import com.natsumes.wechat.AesException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerServiceImplTest extends MeteorApplicationTests {

    @Autowired
    private CustomerServiceImpl customerService;

    @Test
    public void testCheckSignature() {
        boolean b = customerService.checkSignature("74f4a2e37b356b1941b649bc62ef67f5f45f59f2",
                "1593670616", "381426925");
        Assert.assertTrue(b);
        //, "8289091075331555193"
    }

    @Test
    public void testHandleMessage() throws AesException {
        WxMessageForm wxMessageForm = new WxMessageForm();
        wxMessageForm.setEncrypt("sBmRTKwowPIqBS7xZyU326R7Ku3YxViS9wwxHw0GCdELKeFNpW8JyH6vH1gc44pqOJeR34VslyRLCM9+QWhX3EW6N9FB0K0N8inPiF8UoWfLKUSSLZE/RPOMRpvP+5zFLdHhMdRTlh4z4Avx/u/5URjLt3iZL2LLwpFmoF2oNVYDMQDcmUX39V8Vqe6G2JKjgrLfrb/YxWnyj3DcPRLHyFE3tKqpR6nkmphLC+PVh7MAqbJfb1iwhR3aEp2zBn12mbDBVvy43y0+It/zDmE1kv0ai0Mj2bflQW+Xqh32S9o=");
        customerService.handleMessage(wxMessageForm);
        //Assert.assertTrue(b);
        //, "8289091075331555193"
    }

}