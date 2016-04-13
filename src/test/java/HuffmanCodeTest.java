import static org.junit.Assert.*;

/**
 * Created by Furkan on 14.04.2016.
 */
public class HuffmanCodeTest {

    @org.junit.Test
    public void testEncode() throws Exception {
        HuffmanCode Htree = new HuffmanCode();

        // Create symbol array
        HuffmanCode.HuffData[] symbols = new HuffmanCode.HuffData[]{
                new HuffmanCode.HuffData(186, '_'),
                new HuffmanCode.HuffData(103, 'e'),
                new HuffmanCode.HuffData(80, 't'),
                new HuffmanCode.HuffData(64, 'a'),
                new HuffmanCode.HuffData(63, 'o'),
                new HuffmanCode.HuffData(57, 'i'),
                new HuffmanCode.HuffData(57, 'n'),
                new HuffmanCode.HuffData(51, 's'),
                new HuffmanCode.HuffData(48, 'r'),
                new HuffmanCode.HuffData(47, 'h'),
                new HuffmanCode.HuffData(32, 'd'),
                new HuffmanCode.HuffData(32, 'l'),
                new HuffmanCode.HuffData(23, 'u'),
                new HuffmanCode.HuffData(22, 'c'),
                new HuffmanCode.HuffData(21, 'f'),
                new HuffmanCode.HuffData(20, 'm'),
                new HuffmanCode.HuffData(18, 'w'),
                new HuffmanCode.HuffData(16, 'y'),
                new HuffmanCode.HuffData(15, 'g'),
                new HuffmanCode.HuffData(15, 'p'),
                new HuffmanCode.HuffData(13, 'b'),
                new HuffmanCode.HuffData(8, 'v'),
                new HuffmanCode.HuffData(5, 'k'),
                new HuffmanCode.HuffData(1, 'j'),
                new HuffmanCode.HuffData(1, 'q'),
                new HuffmanCode.HuffData(1, 'x'),
                new HuffmanCode.HuffData(1, 'z')
        };

        // Build hufffman tree
        Htree.buildTree(symbols);

        String code = "11000010011111110010100001";
        String message = "q__rg";

        assertEquals(Htree.encode(message,Htree.huffTree),code);
    }
}