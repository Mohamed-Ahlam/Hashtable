import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashTable<T>{
    NGen<T>[] hashTable;
    int numOfWords;

    //TODO: Create a constructor that takes in a length and initializes the hash table array

    public HashTable(int size){
        hashTable = new NGen[size];
    }

    //TODO: Implement a simple hash function
    public int hash1(T item) {  // adds first and last letter
        String s = item.toString();
        if (s.length() == 1)        //check if string is only one letter
            return s.charAt(0) % hashTable.length;
        return (s.charAt(0) + 60*s.charAt(1) + s.charAt(s.length()-1) + 60*s.length()) % hashTable.length;
    }

    //TODO: Implement a second (different and improved) hash function
    public int hash2(T item) {
        String s = item.toString();
        int value = 0;
        int i = 0;
        while(i < s.length()){
            value += s.charAt(i);       // add every letter together and then mod it by the hashtable length
            i++;
        }
        return value % hashTable.length;
    }

    //TODO: Implement the add method which adds an item to the hash table using your best performing hash function
    // Does NOT add duplicate items
    public void add(T item) {

//        int index = hash1(item);
        int index = hash2(item);
        NGen<T> ptr = hashTable[index];
        NGen<T> trailer = ptr;

        // only adds if there are no duplicates
            // link item if there is something on the spot

            if (hashTable[index] != null) { //    do chaining if true
                while (ptr != null) {
                    if(ptr.getData().equals(item))  // use equals method // if item was found in table u exit
                        return;
                    trailer = ptr;
                    ptr = ptr.getNext();
                }
                trailer.setNext(new NGen<T>(item, null));
            }
            else {
                hashTable[index] = new NGen<T>(item, null);
            }
    }

    // ** Already implemented -- no need to change **
    // Adds all words from a given file to the hash table using the add(T item) method above
    @SuppressWarnings("unchecked")
    public void addWordsFromFile(String fileName) {
        Scanner fileScanner = null;
        String word;
        try {
            fileScanner = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("File: " + fileName + " not found.");
            System.exit(1);
        }
        while (fileScanner.hasNext()) {
            word = fileScanner.next();
            word = word.replaceAll("\\p{Punct}", ""); // removes punctuation
            this.add((T) word);
        }
    }

    //TODO: Implement the display method which prints the indices of the hash table and the number of words "hashed"
    // to each index. Also prints:
    // - total number of unique words       <>
    // - number of empty indices            <>
    // - number of nonempty indices         <>
    // - average collision length           <>
    // - length of longest chain            <>

    public void display() {
        int empty = 0;
        int nonEmpty = 0;
        int uniqueWords = 0;
        int longestChain = 0;

        for(int i = 0; i< hashTable.length; i++){

            if(hashTable[i] == null){       // if there is an empty index
                empty++;
            }
            else {
                nonEmpty++;
                uniqueWords++;
                if (hashTable[i].getNext() != null) { // theres is chaining if not null
                    NGen<T> ptr = hashTable[i];
                    while (ptr != null) {       // traverse through list and add amount of elements in it
                        numOfWords++;
                        uniqueWords++;
                        ptr = ptr.getNext();
                    }
                    if (numOfWords > longestChain) {
                        longestChain = numOfWords;
                    }
                }
            }

            System.out.println(i + ": " + numOfWords);
            numOfWords = 0;
        }

        int averageCollision = uniqueWords/nonEmpty;


        System.out.println("# Unique words: " + uniqueWords);
        System.out.println("# empty indices: " + empty);
        System.out.println("# Nonempty indices: " + nonEmpty);
        System.out.println("Average Collision Length: " + averageCollision);
        System.out.println("The longest chain: "+ longestChain);
    }

    // TODO: Create a hash table, store all words from "canterbury.txt", and display the table
    //  Create another hash table, store all words from "keywords.txt", and display the table
    public static void main(String args[]) {

//        HashTable hashTable = new HashTable(150);
//        hashTable.addWordsFromFile("gettysburg.txt");

//        HashTable hashTable = new HashTable(50);
//        hashTable.addWordsFromFile("keywords.txt");
//
        HashTable hashTable = new HashTable(150);
        hashTable.addWordsFromFile("canterbury.txt");

        hashTable.display();
    }
}
