import javax.swing.plaf.synth.SynthLookAndFeel;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        SecureRandom secureRandom;
        byte[] nonce;
        byte[] iv;

        secureRandom = SecureRandom.getInstance("SHA1PRNG");
        nonce = new byte[12];
        secureRandom.nextBytes(nonce);
        iv = new byte[16];
        System.arraycopy(nonce, 0, iv, 0, nonce.length);


        Base64.Encoder enc = Base64.getEncoder();
        Base64.Decoder dec = Base64.getDecoder();
        AES aes = new AES();

        byte[] bKey = new byte[]{79, 86, 82, 110, 90, 110, 107, 49, 84, 106, 99, 53, 101, 70, 82, 50, 100, 68, 86, 79, 86, 50, 52, 118, 81, 85, 120, 69, 85, 84, 48, 57};

        System.out.println(Arrays.toString(bKey));

//        byte[] bMessage = new byte[ ]{99, 88, 100, 108, 99, 110, 82, 53, 100, 87, 108, 118, 99, 71, 120, 114, 15};
//        System.out.println(Arrays.toString(bMessage));

        String message = "Kiodaija123";
        byte[] bMessage = message.getBytes(StandardCharsets.UTF_8);

//        byte[] cipherText = aes.encryptText(bMessage,bKey);
//
//        byte[] plainText = aes.decryptText(cipherText,bKey);
//        System.out.println( "Cipher Text " + Arrays.toString(cipherText));
//        System.out.println("plain Text "+Arrays.toString(plainText));

        CtrMode ctrMode = new CtrMode();
        byte[] cipherText = ctrMode.encrypt(iv,bMessage,bKey);

        byte[] plainText = ctrMode.decrypt(iv,cipherText,bKey);



        String cipher = new String(cipherText, StandardCharsets.UTF_8);
        System.out.println("cipher"+ cipher);
        System.out.println("plain"+Arrays.toString(plainText));
        String str = new String(plainText, StandardCharsets.UTF_8).trim();
        System.out.println("plain "+ str);
    }
}
