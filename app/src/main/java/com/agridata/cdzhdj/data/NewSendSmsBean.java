package com.agridata.cdzhdj.data;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-28 10:05.
 * @Description :描述
 */
public class NewSendSmsBean {


    public String phone;
    public String template_mid="7602703b9ab24dd6bcd2c44a2847c4f1";
    public TemplateBean template;


    public  static class  TemplateBean{
        public String userName;
        public String farm;
        public String datetime;
        public String animalAmount;
    }

}
