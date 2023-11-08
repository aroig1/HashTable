@SuppressWarnings("unchecked")
public class Hashtable<K, V> {
    private Pair[] table;
    private int n;//the number of key-value pairs in the table
    private int m;//the size of the table
    private double alphaHigh = 0.5;//resize if n/m exceeds this (1/2)
    private double alphaLow = 0.125;//resize if n/m goes below this (1/8)

    //constructor--default table size is 11
    public Hashtable() {
	n = 0;
	m = 11;
	table = new Pair[m];
    }

    //constructor
    public Hashtable(int m) {
	n = 0;
	this.m = m;
	table = new Pair[m];
    }

    //returns the value associated with key <key>
    //return null if key is not in table
    //do not forget that you will have to cast the result to (V)
    public V get(K key) {
	//TO BE IMPLEMENTED
        int index = Math.abs(key.hashCode()) % m;
        if (table[index] == null) {
            return null;
        }
        else {
            return (V)table[index].getValue();
        }
    }

    //puts (key, val) into the table or updates value
    //if the key is already in the table
    //resize to getNextNum(2*m) if (double)n/m exceeds alphaHigh after the insert
    public void put(K key, V val) {
	//TO BE IMPLEMENTED
        int index = Math.abs(key.hashCode()) % m;

        while (table[index] != null && !(table[index].getKey().equals(key))) { // check for empty slot or matched key
            ++index;
            index = index % m;
        }
        if (table[index] == null) { // create new entry
            table[index] = new Pair(key, val);
            ++n;
        }
        else { // update value if key matches
            table[index].setValue(val);
        }

        if (((double)n / m) > alphaHigh) {
            resize(2 * m); 
        }

    }

    //removes the (key, value) pair associated with <key>
    //returns the deleted value or null if the element was not in the table
    //resize to getNextNum(m/2) if m/2 >= 11 AND (double)n/m < alphaLow after the delete
    public V delete(K key) {
	//TO BE IMPLEMENTED
        V val = null;
        int index = Math.abs(key.hashCode()) % m;

        if (table[index] == null) {
            return null;
        }
        else if (key.equals(table[index].getKey())){
            val = (V)table[index].getValue();
            table[index] = null;
            --n;
        }

        if ((m/2 >= 11) && (((double)n / m) < alphaLow)) {
            resize(m / 2); // IMPLEMENT
        }

        return val;
    }

    //return true if table is empty
    public boolean isEmpty() {
	//TO BE IMPLEMENTED
        for (int i = 0; i < table.length; ++i) {
            if (table[i] != null) {
                return false;
            }
        }
        return true;
    }

    //return the number of (key,value) pairs in the table
    public int size() {
	//TO BE IMPLEMENTED
        int count = 0;
        for (int i = 0; i < table.length; ++i) {
            if (table[i] != null) {
                ++count;
            }
        }
        return count;
    }

    //This method is used for testing only. Do not use this method yourself for any reason
    //other than debugging. Do not change this method.
    public Pair[] getTable() {
	return table;
    }

    //PRIVATE

    
    //gets the next multiple of 6 plus or minus 1,
    //which has a decent probability of being prime.
    //Use this method when resizing the table.
    private int getNextNum(int num) {
	if(num == 2 || num == 3)
	    return num;
	int rem = num % 6;
	switch(rem) {
	case 0: num++; break;
	case 2: num+=3; break;
	case 3: num+=2; break;
	case 4: num++; break;
	}
	return num;
    }

    public void resize(int x) {
        // int newM = getNextNum(x);
        // Pair[] newTable = new Pair[newM];
        // for (int i = 0; i < m; ++i) {
        //     if (table[i] != null) {
        //         System.out.println("REHASHING: " + table[i].getKey()); // DELETE
        //         int index = Math.abs(table[i].getKey().hashCode()) % newM;
        //         System.out.println("REHASHING: " + table[i].getKey() + " to index " + index);
        //         newTable[index] = new Pair(table[i].getKey(), table[i].getValue());
        //     }
        // }
        // table = newTable;
        // m = newM;

        int oldM = m;
        m = getNextNum(x);
        Pair[] oldTable = table;
        this.table = new Pair[m];
        for (int i = 0; i < oldM; ++i) {
            if (oldTable[i] != null) {
                put((K)oldTable[i].getKey(), (V)oldTable[i].getValue());
            }
        }
    }

    public void printTable() {
        System.out.println();
        for (int i = 0; i < table.length; ++i) {
            if (table[i] == null) {
                System.out.print("");
            }
            else {
                System.out.println(table[i].getKey() + ", " + table[i].getValue());
            }
        }
        System.out.println();
    }

    public void printTable(Pair[] table) {
        System.out.println();
        for (int i = 0; i < table.length; ++i) {
            if (table[i] == null) {
                System.out.print("");
            }
            else {
                System.out.println(table[i].getKey() + ", " + table[i].getValue());
            }
        }
        System.out.println();
    }

}
      
