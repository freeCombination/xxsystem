package com.xx.system.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 加/解密工具类
 * 
 * @version V1.20,2013-12-6 下午3:25:28
 * @see [相关类/方法]
 * @since V1.20
 * @depricated
 */
@SuppressWarnings("restriction")
public class RsaCryptoUtil {
    /** @Fields PUB_KEY : PUB_KEY 公钥 */
    public static final String PUB_KEY = "pub_key";
    
    /** @Fields PRIV_KEY : PRIV_KEY 私钥 */
    public static final String PRIV_KEY = "priv_key";
    
    /**
     * @Title loadKey
     * @author wanglc
     * @Description: 加载密钥
     * @date 2013-12-6
     * @param type
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws IOException
     */
    public static Key loadKey(String type)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        InputStream is =
            RsaCryptoUtil.class.getResourceAsStream("/application.properties");
        Properties config = new Properties();
        config.load(is);
        KeyFactory keyFactory =
            KeyFactory.getInstance("RSA", new BouncyCastleProvider());
        
        if (PRIV_KEY.equals(type)) {
            // privateKey
            String privateKeyValue = config.getProperty(PRIV_KEY);
            PKCS8EncodedKeySpec priPKCS8 =
                new PKCS8EncodedKeySpec(Base64.decode(privateKeyValue));
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            return privateKey;
            
        } else {
            // publicKey
            String privateKeyValue = config.getProperty(PUB_KEY);
            X509EncodedKeySpec bobPubKeySpec =
                new X509EncodedKeySpec(Base64.decode(privateKeyValue));
            PublicKey publicKey = keyFactory.generatePublic(bobPubKeySpec);
            return publicKey;
        }
    }
    
    /**
     * @Title decrypt
     * @author wanglc
     * @Description: decrypt
     * @date 2013-12-6
     * @param privateKey
     * @param cipherText
     * @return
     */
    public static String decrypt(RSAPrivateKey privateKey, String cipherText) {
        byte[] raw = Base64.decode(cipherText);
        if (privateKey != null) {
            try {
                Cipher cipher =
                    Cipher.getInstance("RSA", new BouncyCastleProvider());
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return new String(cipher.doFinal(raw));
            } catch (Exception e) {
                throw new RuntimeException("解密失败！");
            }
        }
        return null;
    }
    
}
