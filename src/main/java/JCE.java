import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class JCE {
    private static final byte[] salt =
            {
                    (byte)0x4D, (byte)0x9B, (byte)0xC6, (byte)0x53,
                    (byte)0x17, (byte)0xAF, (byte)0xE2, (byte)0x08
            };

    private static final int iterations = 311;

    public static void main(String... args) throws Exception {
        String data = "Test";
        for (String arg : args) {
            data += ":" + arg;
        }

        SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        PBEKeySpec pbeKeySpec = new PBEKeySpec("abcdefg".toCharArray());
        SecretKey myDesKey = keyFac.generateSecret(pbeKeySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterations);

        Cipher c = Cipher.getInstance("PBEWithMD5AndTripleDES");
        c.init(ENCRYPT_MODE, myDesKey, paramSpec);

        System.out.println("Encrypting, \"" + data + "\"");

        byte[] encryptedData = c.doFinal(data.getBytes());
        System.out.println("Encrypted: " + new String(encryptedData));

        c.init(DECRYPT_MODE, myDesKey, paramSpec);
        byte[] decipheredText = c.doFinal(encryptedData);
        System.out.println("Decrypted: " + new String(decipheredText));
    }
}
