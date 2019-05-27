public interface BitSet {

     void set(int bitIndex, boolean value);

     boolean get(int bitIndex);

     void clear(int bitIndex);

     void clear();

     long size();

     boolean isEmpty();
}
