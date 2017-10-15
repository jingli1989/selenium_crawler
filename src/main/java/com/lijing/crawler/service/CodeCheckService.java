package com.lijing.crawler.service;

import com.alibaba.fastjson.JSON;
import com.lijing.crawler.model.VerifyCodeResDto;
import com.lijing.crawler.virtual.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片验证码服务
 * Created by lijing on 2017/10/14 0014.
 */
@Slf4j
@Service
public class CodeCheckService {

    private static final String appCode = "49a602904e5a452f89fe4d3f290fbc37";

    public static void main(String[] args) {
        CodeCheckService codeCheckService = new CodeCheckService();
        String str = codeCheckService.getVerifyCode("iVBORw0KGgoAAAANSUhEUgAAAEgAAAAYCAYAAABZY7uwAAAMWElEQVR42r1YB1dU1xo1P+GtZYwm" +
                "z0TFFhMrErGgBnsFjRpQLCBKEUQpKrZgQ1ERQbpEOiiIQug4UpQ+gDQpItLLwFCmMExlv3OPEZiA" +
                "GaO+fGudtebeO/fcc/fd3/72d8bwOnLxdzEwMACTa2ugKopkOciWpCqdS6gQKh0rpHIoOP0YaJdg" +
                "QCCnc39KeD91wx7P7TD01Ud9220I+F5kTgk+Z4z5O4DegWPmtE7lRBxFKzoUbUrnjMI5SseybgHE" +
                "HnWQGlVC8uvLT148v58P6xBzNHEbyFplZCg+GfQPBugdOMywuLkJPL4I6uvPYeGG81iqcxHau65i" +
                "096b2HHYDfusvGFiE4DSl01DbFEMwC6mE6LOXnRXNkAskaGnV0jnYeY+fKIMx52qcNatBilZnfQe" +
                "QW02esviMCCXDs4j7BOjurYNTa1dkEhlSmu0C7OEnrsOHZ1droRB7v8OQMPBYcYxF120dfTi6wUW" +
                "mLjQCj/vdMSWA7coQBv2XMdafSes3umEilctw76uAuGpzcg9fw+sM354lluJU44PoLHhHKYvtcVE" +
                "jROYuswBK3d5wvFOCorLG9BTmw9e5ROltRSX18P0pD9uesejjdOjdM0i0HgQINkwUP8VgOQKOaQy" +
                "CfnyIjT11WBtsRrGzj2MxVscEPukCJU1rSh+2YDcotdIz65AcnoparNy8eppCJoKU+g8jaxCeM42" +
                "wqH19pi6xBpjfzTDNwsPY4nefmw0uIEfVtpDTdMGmpsd4OgWg37xyJeMTSmizLU8GYw31V1QyIcY" +
                "cshv7yBACpJe/xeA+JxclX+Sk/xmleXgq7nmmPbrHuxmr4Jt7zrkiROV/leX8QyPrBYh4ZI+2uvq" +
                "wCWac9TSExNmmeBbjWPwCErAjfRpCM47gNKKRgoqwwz9Ix44cTl81GeHRGVh9ip7HDaIQNIlHvo6" +
                "hgAyIuKs565LAVKVWrJ2LuR84T8Ch5P6/MMAYuJZbhUB6Ag2HLqIyKoIpIkj4SGwgavgKIQKHmVO" +
                "VUoABch73xIk+d9D8MPnmLXyBGGOOdJzKsEXCtApKkWvpHaQqR1cHp7nV1MWjhZegSzMXH6CMizL" +
                "Swhx78B7NXOA6N5oQDHgtJhdA9c1HNImzgcDpJDJVAPU3yxEU3AN/iBUZwAytvVDfdNbUWWJwxHQ" +
                "dxFhQidc8J8MZ6dZ8D+4GDd+0ULy/YdYq+dE0+qiy2NIJLKPovh1zziSnja45ZtIBZsBQPEnEMNH" +
                "/HY2qqPYkIrESiC9A6dWYx8dndeDPm+KCap60fB7NQIinlGArC+EUsEeRJnkvhPfGOd4O2CZtgzm" +
                "xuo4qq8JV9cATF50DBPmW6C2nvPRGvDbzShM+ukYPAmT+sjL9/L7EJNciNBHWXicyEbk5mzEbs1D" +
                "VVQ+amLYdPR1d0Ii5hK9EqPFKRBsbXOkqO9HxIIDCDe6iuTEQiR6sZD2rBmiftmnAdRbRL7Agzdw" +
                "9knAuDnm0CYV7IxTJFzuJsEvPB0RsXkoLK2j/40WeUOvawo2cf8DQwdnyh4dUu1kMvlHA8Ro03/V" +
                "j8I3JI0K9hTNI3Ted+Pr+aa47mKCl9FZeB1fhDeJL1DJvobMxNMoySxBXHwFTpq5Qk/rGHRJsViz" +
                "9QK0dS9iyVQzGBiHoLqm49MAkvaSStYhoiX6y9nm+FH7FJZvvwStbZewhDyMGU4esSPum7DnNMYt" +
                "NMYhlysqQZDyORA2FELU+hJyYv6Gh+XZIMpC20th+GaBJZYRD2ak5UOfyaxj3BwzjCPrSkorJWwY" +
                "ctFB5/kw/qEdmxfFY+9RL1xzj4VHeDT23TqE23cTMVXTGlM1bBGdUPBxAD2TVMO3L2Pw2NDalwBk" +
                "BmuHEFpZfENSKYuYB2cQAR4eYokUP+tfxYyVNtgfboqzvO24xjdCh7xp1Gf1lCegykUbL6/MA/9V" +
                "utK1wyd+p6nNsMVqRQDitrKJBr11zII+IdbuuUKr60aD63hd166kP4FhZTC58zu8Wj2QKH8MgaQX" +
                "kRVu6BZ0Y+vu6/hqliluesaPEHuVADHgrOgi5i/8ELKes+i5jXtv0EXGPy1WyYjGFi5W7rhCXbcn" +
                "KwRx/X64L3LGPaEDokTuIxnEa0dDxDEKUF9jkdK1A8d88CV5rslyv0Fgvl68G/w+AXZH/ISV51Zj" +
                "wiJjfEXSn/W8XCmdM+TJOCUxwWWpLe5KXei1Nw0dKK1sxF4LL0wgwDMMLctvx4bvgrFuYhCaanl/" +
                "MYrtOSPrv5yHY/xwfH9qKfb9YQ8HQQymrbalKZaaVaG6cS2rwzKS5wvWnUV2wavB824CK9zikznE" +
                "D5AjSUCLvFYJHGb0c1792dgqqClcu+Qq+TCmCI/Oxvif9LApUA1bQ6ZjW+gsdIs6UFHdjEXEaDIf" +
                "z9UvGZ1c5RdkzGd5RQPu3AiH9bkg6Jt7YCvRxdna9hR45jeHVOoNk4KhvzAS5fkc1QAxUS5rxqPM" +
                "aAQ0p8Ctj0V1gPEjOYU1KgHKIr5mxfbLmESqGOO2h9O2VlYKu971sOjRQrDQEXlPTJHh+j3Kby9H" +
                "pfNySHpaKTgJemzE6bAxTWcHBUjjxFJsClKDRN6v9Kw+Uvo377tJATpLikdDM3fomkhCPRajQeu3" +
                "nMcl5ygkpBbTNXkFPsV3ZH1zV5+mzLLbmTQCnL8FSMlJyxUUIIYVzOSqgqHwql2OdNFJaSVDqUSa" +
                "TREp1e/CR3gKu7omQZc7HuXSbEhJyZVLFEjWL8KWgOnYGDQZeubudB6GQcw6RrCdNMO7TO7Q/9hf" +
                "jSDGc0jkuT0CGJIUnahuCQ//FJT7+KM75W2vx7RIaktt6H2v3rS914l/EEDMVxk/7wh0jVzoZKqi" +
                "h9dHqPu2zJvZ+1P/Qj0V8TBcbu+o9+xunYGo/SwkbCuATCIbNIB6ZgSgH0wR/CCdiv9fI/9FLbYb" +
                "36ZWwOHWI6pTHW0cBHkH0vsXk/SbQPQp5GEmOKFh6PT2okDXvGmHGjGgjGxEJxXSNQ4HSSQVwzvn" +
                "oWqA+kh74O0bTCvJITs/uu0wlN8izNo8E7Y3bEY6YK84CtD8NWcoi4aXYCWBJvSWSuVgGZZAJnor" +
                "sCs6v8DyzjFkfIF1UzZTgM6O1QCvlaP0Ej1dQgSGZ2DlL5eJDv1GfRkTvF4e7rr4QCgQ4sjpAOrE" +
                "f15rh7M6RxFgaEctgsbG89SiTNE8ThkY9jh7sEN4F2mv2aoBqn9TDQvL41ijd402lp1dQ6VUIpXA" +
                "PfQObJysR9zHMI3ZCmFSU9fwFm1MmerG7AnxBSK6L9TDE1JN8A15SlqR0bcrZproYuwcE6Jpl2iV" +
                "quO0oIvHQ3NzF3zcU7BmiyOmkVQxPO6LV7Uj2V1W2YSTZ+5h5jwTTJhtivHEN2kSMO/H5CAlMxt2" +
                "V/yw/eBtnLx8H/nFtR+XYoN9jZyP3IqFZKi/zeP2YmiF/IJVIXoo66pCZGgsgv0iEROZSKtHVAKb" +
                "lntmD2kO6cgP2twljjwezgRoJvUmaplTo2dy8t573baBEUmReeaYu9IK05dbQu2gDixuuMHQzBvz" +
                "V9hjxlI7/Gp6B3FPit6rnzyS8k3NnXhRVk8qbD3auC1o4FTjQZorWOxHaCetU2t7z2CP99EA5VVq" +
                "ILNsGsTSVnrcIuQgoSEd92vi4FjkBU1bHazV3IUd24yR015Emst+xLNewMDSC+qk5M/QssOUxdb4" +
                "VsOKaoYa+W1xJpBufbxPJBljOocYTi8nR9wLiMZ+Kx/a7ixafx6zdIyheVkXVmlHcI9/EZ5CO/AU" +
                "Xe/t9t9FcLITrC4bIDQi5AM2zP4BQBIpB4WvVo96zaM8GBfy3OB82RNnnZ3gwHZDLqcYpdwqVHBf" +
                "I6EgD7FPCxGdXIBYVgHyS17TqqZqH0dMmNicG4nGdB+IOt9ATkS4ua0bT56UwicpCC48S7gILP7U" +
                "rDEokT77oHfJjm9EnF/15wVIoZBCJK6H4BFhR+xQ+fYuK0BgpbLDjq57go0JB6H9hwGWPN4J9Yc6" +
                "KOgoh1Dah+ctochsCYZi4MOa2J7qVHBfRKGfW/dedqT0h+Cp+D465S3/qBkekEohycuB+Hk6FN1d" +
                "owOUkhAOVvJDZKTGIi8nFWUlbNQRce7idoz6hcUlTZDWDxmyyUFuWBUdrHIxDFhTw7QxOXQFMXxS" +
                "5c2uz7zZ/i54vB40NtTiZXkh2HkZyMxIQGrKI/rOSXHBSIoNRNL9O2A99EB6kj+y0sLAznyA4rwo" +
                "VBTF4H/ozgq2PjtSAAAAAABJRU5ErkJggg==");
        System.out.println(str);
    }

    /**
     * 根据验证码图片获取验证码内容
     * @param imgCode base64图片
     * @return 验证码内容
     */
    public static String getVerifyCode(String imgCode){
        String host = "http://captcha.api51.cn";
        String path = "/images";
        String method = "POST";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> queryMap = new HashMap<>();
        Map<String, String> bodyMap = new HashMap<>();
        try {

            imgCode = imgCode.replaceAll("\r\n","");
            bodyMap.put("data", imgCode);
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, queryMap, bodyMap);
            if(response.getStatusLine().getStatusCode()==200) {
                VerifyCodeResDto verifyCodeResDto = JSON.parseObject(EntityUtils.toString(response.getEntity()), VerifyCodeResDto.class);
                if("0".equals(verifyCodeResDto.getCode())) {
                    return verifyCodeResDto.getData().getRecognition();
                }
                log.error("验证码打码失败，原因:{}",verifyCodeResDto);
                return null;
            }
            log.error("验证码打码失败，原因:{}",response.getStatusLine().getStatusCode());
        } catch (Exception e) {
            log.error("图片验证码打码失败，原因:",e);
        }
        return null;
    }

    /**
     * 文件转换成base64
     * @param file 文件
     * @return base64字符串
     */
    public static String getBase64(File file){
        byte[] data = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            log.error("文件读取失败");
        } catch (IOException e) {
            log.error("文件解码base64失败，IO异常");
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
