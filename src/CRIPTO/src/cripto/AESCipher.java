package cripto;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Classe base para encriptação/decriptação de arquivos.
 */
public class AESCipher {
    
    /**Vetor de inicialização para o algoritmo AES/CFB/PKCS7Padding.*/
    private byte[] iv;

    /**
     * Encriptar o stream de entrada, direcionando os bytes criptografados para
     * o stream de saída.
     * @param istream stream de entrada.
     * @param ostream stream de saída.
     * @param password senha para encriptação do arquivo.
     * @throws java.lang.Exception erro no precesso de encriptação.
     */
    public void encrypt(InputStream istream, OutputStream ostream, String password) throws Exception {
        //Testa o tamanho da senha.
        if (password.length() == 0) {
            throw new Exception("Senha não pode estar vazia");
        }
        Provider provider = new BouncyCastleProvider();
        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS7Padding", provider);
        byte[] passwordHash = getSHA256Hash(password);
        SecretKey secretKey = new SecretKeySpec(passwordHash, "AES");
        this.iv = generateIv();
        IvParameterSpec ivParSpec = new IvParameterSpec(this.iv);   
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParSpec);
        try (CipherOutputStream costream = new CipherOutputStream(ostream, cipher)) {
            byte[] buffer = new byte[256];
            int length;
            while ((length = istream.read(buffer)) != -1) {
                costream.write(buffer, 0, length);                
                costream.flush();
            }
        }
    }
    
    /**
     * Decriptar o stream de entrada, direcionando os bytes decriptografados para
     * o stream de saída.
     * @param istream stream de entrada.
     * @param ostream stream de saída.
     * @param password senha para decriptação do arquivo.
     * @param iv vetor de inicialização para o algoritmo AES/CFB/PKCS7Padding..
     * @throws java.lang.Exception erro no processo de decriptação.
     */
    public void decrypt(InputStream istream, OutputStream ostream, String password,
    byte[] iv) throws Exception {
        Provider provider = new BouncyCastleProvider();
        Cipher cipher = Cipher.getInstance("AES/CFB/PKCS7Padding", provider);
        byte[] passwordHash = getSHA256Hash(password);
        SecretKey secretKey = new SecretKeySpec(passwordHash, "AES");
        IvParameterSpec ivParSpec = new IvParameterSpec(iv); 
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParSpec);
        try (CipherInputStream cistream = new CipherInputStream(istream, cipher)) {
            byte[] buffer = new byte[256];
            int length;            
            while ((length = cistream.read(buffer)) != -1) {                 
                ostream.write(buffer, 0, length);
                ostream.flush();
            }
        }        
    }
    
    /**
     * Decriptar o stream de entrada, direcionando os bytes decriptografados para
     * o stream de saída.
     * @param istream stream de entrada.
     * @param ostream stream de saída.
     * @param password senha para decriptação do arquivo.
     * @throws java.lang.Exception erro no processo de decriptação.
     */
    public void decrypt(InputStream istream, OutputStream ostream, String password)
    throws Exception {
        decrypt(istream, ostream, password, iv);
    }
    
    /**
     * Obter o hash no formato SHA-256 para a senha passada como parâmetro. Usa
     * classes do pacote <b>java.security</b> para o processo.
     * @param password senha a ser obtido o hash no formato SHA-256
     * @return bytes do hash SHA-256.
     * @throws NoSuchAlgorithmException erro no processo de hash.
     */
    private byte[] getSHA256Hash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes());
        return encodedhash;
    }
    
    /**
     * Gerar o vetor de inicialização com bytes gerados de modo pseudoaleatório
     * para o algoritmo AES/CFB/PKCS7Padding.
     * @return bytes do vetor de inicialização.
     * @throws NoSuchAlgorithmException erro no processo.
     */
    public byte[] generateIv() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(System.currentTimeMillis());
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
    
    /**
     * Obter o vetor de inicialização para o algoritmo AES/CFB/PKCS7Padding.
     * @return bytes do vetor de inicialização.
     */
    public byte[] getIv() {
        return iv;
    }
    
}