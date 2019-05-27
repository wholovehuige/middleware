
public class BloomFilter {

    private RedisBitSet bitSet;
    private int bitSetSize;  //数组步长
    private int k = 1<<3; // hash 函数个数


    public BloomFilter(int bitSetSize, RedisBitSet redisBitSet) {
        this.bitSet = redisBitSet;
        this.bitSetSize = bitSetSize;
    }


    public void clear() {
        bitSet.clear();
    }


    public void add(String strKey) {
        int[] hashes = HashMethods.getHashs(strKey.getBytes(),k);
        for (int hash : hashes)
            bitSet.set(Math.abs(hash % bitSetSize), true);
    }


    public boolean contains(String strKey) {
        return contains(strKey.getBytes());
    }

    private boolean contains(byte[] bytes) {
        int[] hashes = HashMethods.getHashs(bytes, k);
        for (int hash : hashes) {
            if (!bitSet.get(Math.abs(hash % bitSetSize))) {
                bitSet.set(Math.abs(hash % bitSetSize), true);
                return false;
            }
        }
        return true;
    }

    public RedisBitSet getBitSet() {
        return bitSet;
    }


    public int getBitSetSize() {
        return this.bitSetSize;
    }

    //暂不实现
    public int getKeys() {
        return 0;
    }
}
