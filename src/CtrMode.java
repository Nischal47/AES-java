import java.util.Arrays;

public class CtrMode {

    public byte[] encrypt(byte[] iv,byte[] message,byte[] key) throws Exception {
        AES aes = new AES();
        byte[] cipherIv;
        if(message.length<16){
            System.out.println("Message should be atleast 128 bits");
        }
        int length = message.length;
        int n = (length + 15)/16*16;
        byte[] cipher = new byte[n];

        if(length == 16){
            System.out.println("Iv"+Arrays.toString(iv));
            System.out.println("Key"+Arrays.toString(key));
           cipherIv =  aes.encryptText(iv,key);
           System.out.println("cipherIv"+Arrays.toString(cipherIv));
           cipher = aes.XORBytes(cipherIv,message);
           return cipher;
        }

        int i = 0;
        int k = 0;
        while (i < length){
            byte[] block = new byte[16];
            byte[] result = new byte[16];
            int j = 0;
            for (; j < 16 && i < length; j++, i++) {
                block[j] = message[i];
            }
            while (j < 16) {
                /* pad with white spaces */
                block[j++] = 0x00;
            }

            cipherIv =  aes.encryptText(iv,key);
            System.out.println("block cipher "+ i + " "+Arrays.toString(cipherIv));
            result = aes.XORBytes(cipherIv,block);
            System.out.println("Cipher" + i + " "+Arrays.toString(result));
            for (j = 0 ; j < 16 && k < cipher.length; j++, k++) {
                cipher[k] = result[j];
            }
        }
        return cipher;
    }

    public byte[] decrypt(byte iv[],byte[] cipherText,byte[] key) throws Exception {
        AES aes = new AES();
        byte[] cipherIv;
        if(cipherText.length<16){
            System.out.println("Message should be atleast 128 bits");
        }
        int length = cipherText.length;
        int n = (length + 15)/16*16;
        byte[] plainText = new byte[n];

        if(length == 16){
            System.out.println("Iv"+Arrays.toString(iv));
            System.out.println("Key"+Arrays.toString(key));
            cipherIv =  aes.encryptText(iv,key);
            System.out.println("cipherIv"+Arrays.toString(cipherIv));
            plainText = aes.XORBytes(cipherIv,cipherText);
            return plainText;
        }

        int i = 0;
        int k = 0;
        while (i < length){
            byte[] block = new byte[16];
            byte[] result = new byte[16];
            int j = 0;
            for (; j < 16 && i < length; j++, i++) {
                block[j] = cipherText[i];
                System.out.println("j" + block[j]);
            }
            while (j < 16) {
                /* pad with white spaces */
                block[j++] = 0x00;
            }

            System.out.println("block " + i+" "+Arrays.toString(block));
            cipherIv =  aes.encryptText(iv,key);
            System.out.println("block cipher "+ i + " "+Arrays.toString(cipherIv));
            result = aes.XORBytes(cipherIv,block);
            System.out.println("plain" + i + " "+Arrays.toString(result));
            for (j = 0 ; j < 16 && k < plainText.length; j++, k++) {
                plainText[k] = result[j];
            }
        }
        return plainText;
    }

    public static byte[] increment(byte[] a) {
        for (int i = a.length - 1; i >= 0; --i) {
            if (++a[i] != 0) {
                return a;
            }
        }
        throw new IllegalStateException("Counter overflow");
    }


}
