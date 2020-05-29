package org.wy.sso.common.CypherTool;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component(value = "Base64")
public class Base64 {
    /**
     * 加密
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getBase64(String str) throws UnsupportedEncodingException {
        byte[] b = null;
        String s = null;
        b = str.getBytes("utf-8");
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * 解密
     * @param s
     * @return
     * @throws IOException
     */
    public String getFromBase64(String s) throws IOException {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            b = decoder.decodeBuffer(s);
            result = new String(b, "utf-8");
        }
        return result;
    }
}
