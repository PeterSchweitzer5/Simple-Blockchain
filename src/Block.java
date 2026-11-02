import java.security.MessageDigest;
import java.util.HexFormat;

public class Block {

    public String data;
    public String previousHash;
    public int nonce = 0;
    public String hash;

    public Block(String data, String previousHash) throws Exception {
        this.data = data;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public String calculateHash() throws Exception {
        return sha256(previousHash + nonce + data);
    }

    public void mine(int difficulty) throws Exception {
        String target = "";
        for (int i = 0; i < difficulty; i++) {
            target = target + "0";
        }

        while (!hash.startsWith(target)) {
            nonce++;
            hash = calculateHash();
        }
    }

    public static String sha256(String text) throws Exception {
        byte[] bytes = MessageDigest.getInstance("SHA-256").digest(text.getBytes());
        return HexFormat.of().formatHex(bytes);
    }
}