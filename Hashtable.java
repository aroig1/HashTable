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
        int index = 0;
        if (key instanceof Integer) {
            index = (Integer)key % m;
        }
        else if (key instanceof String) {
            for (int i = 0; i < ((String)key).length(); ++i) {
                index += (int)(((String)key).charAt(i));
            }
            index = index % m;
        }
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
        int index = 0;
        
        if (key instanceof Integer) {
            index = (Integer)key % m;
        }
        else if (key instanceof String) {
            for (int i = 0; i < ((String)key).length(); ++i) {
                index += (int)(((String)key).charAt(i));
            }
            index = index % m;
        }
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

        //printTable(); // DELETE
    }

    //removes the (key, value) pair associated with <key>
    //returns the deleted value or null if the element was not in the table
    //resize to getNextNum(m/2) if m/2 >= 11 AND (double)n/m < alphaLow after the delete
    public V delete(K key) {
	//TO BE IMPLEMENTED
        int index = 0;
        V val;
        if (key instanceof Integer) {
            index = (Integer)key % m;
        }
        else if (key instanceof String) {
            for (int i = 0; i < ((String)key).length(); ++i) {
                index += (int)(((String)key).charAt(i));
            }
            index = index % m;
        }
        if (table[index] == null) {
            return null;
        }
        else {
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
        int newM = getNextNum(x);
        Pair[] newTable = new Pair[newM];
        for (int i = 0; i < m; ++i) {
            if (table[i] != null) {
                int index = 0;
                if (table[i].getKey() instanceof Integer) {
                    index = (Integer)table[i].getKey() % newM;
                }
                else if (table[i].getKey() instanceof String) {
                    for (int j = 0; j < ((String)table[i].getKey()).length(); ++j) {
                        index += (int)(((String)table[i].getKey()).charAt(j));
                    }
                    index = index % newM;
                }
                newTable[index] = new Pair(table[i].getKey(), table[i].getValue());
            }
        }
        table = newTable;
        m = newM;
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

}
      
