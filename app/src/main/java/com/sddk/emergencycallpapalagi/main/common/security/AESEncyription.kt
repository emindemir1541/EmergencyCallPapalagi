package com.sddk.emergencycallpapalagi.main.common.security

import android.util.Base64
import com.sddk.emergencycallpapalagi.main.common.feedBack.logTest
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object AESEncryption {


    fun encrypt(strToEncrypt: String?): String? {
        try {
            val ivParameterSpec = IvParameterSpec(Base64.decode(EncryptionUtil.iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec = PBEKeySpec(EncryptionUtil.secretKey.toCharArray(), Base64.decode(EncryptionUtil.salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            return if (strToEncrypt != null) Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT) else null
        } catch (e: Exception) {
            logTest("Error while encrypting", e)
        }
        return null
    }

    fun decrypt(strToDecrypt: String?): String? {
        try {

            val ivParameterSpec = IvParameterSpec(Base64.decode(EncryptionUtil.iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec = PBEKeySpec(EncryptionUtil.secretKey.toCharArray(), Base64.decode(EncryptionUtil.salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec);
            val secretKey = SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return if (strToDecrypt!=null) String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT))) else null
        } catch (e: Exception) {
            logTest("Error while decrypting:", e);
        }
        return null
    }
}