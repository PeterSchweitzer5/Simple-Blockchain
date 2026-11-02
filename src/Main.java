import java.util.ArrayList;

public class Main {

    public static ArrayList<Block> chain = new ArrayList<>();
    public static int difficulty = 4;

    public static void main(String[] args) throws Exception {
        addBlock("Genesis block");
        addBlock("Person1 -> Person2: 25 BTC");
        addBlock("Person2 -> Person3: 10 BTC");
        addBlock("Person3 -> Person4: 5 BTC");

        System.out.println();
        printChain();
        System.out.println("Without tamper: Valid: " + isValid());
        tamper(chain.get(1));
        System.out.println("With tamper: Valid: " + isValid());
    }

    public static void addBlock(String data) throws Exception {
        String previousHash;
        if (chain.size() > 0) {
            previousHash = chain.get(chain.size() - 1).hash;
        } else {
            previousHash = "0";
        }

        Block block = new Block(data, previousHash);
        block.mine(difficulty);
        chain.add(block);

        System.out.println("Mined: " + data);
    }

    public static boolean isValid() throws Exception {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.hash.equals(current.calculateHash())) {
                return false;
            }
            if (!current.previousHash.equals(previous.hash)) {
                return false;
            }
        }
        return true;
    }

    public static void tamper(Block block) {
        block.data = "Person2 -> Person4 10 BTC";
    }

    public static void printChain() {
        for (int i = 0; i < chain.size(); i++) {
            Block block = chain.get(i);
            System.out.println("Data:          " + block.data);
            System.out.println("Previous hash: " + block.previousHash);
            System.out.println("Hash:          " + block.hash);
            System.out.println();
        }
    }
}