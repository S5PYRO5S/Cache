# Cache

### **Description**
An application that implements various cache algorithms and runs relevant tests.

#### **Currently Implemented Algorithms**

- **LRU (Least Recently Used)**  
  The LRU algorithm removes the least recently used item when the cache exceeds its capacity.
  - **Implementation**: Uses a **doubly linked list** to maintain the order of usage, with the most recently used item at the head and the least recently used at the tail.
  A **hashmap** provides constant-time access to nodes in the linked list.
  - **Time Complexity**: Both `get` and `put` operations have an expected time complexity of **O(1)**.

- **MRU (Most Recently Used)**  
  The MRU algorithm removes the most recently used item when the cache exceeds its capacity.
  - **Implementation**: Similar to LRU, it uses a **doubly linked list** and a **hashmap**, but the eviction logic is reversed. The most recently used item is tracked for removal, stored at the tail of the list.
  - **Time Complexity**: Both `get` and `put` operations have an expected time complexity of **O(1)**.

- **LFU (Least Frequently Used)**
  The LFU algorithm removes the item that has been accessed the least number of times when the cache exceeds its capacity.
  - **Implementation**:
    - A **hashmap** is used to store key-value pairs and provide quick access to items.
    - A **tree map** organizes keys by their frequency of access, ensuring efficient retrieval of the least frequently used items.
    - Each node in the tree map contains a **doubly linked list** of keys with the same frequency, which helps maintain the order of insertion for keys with equal frequency.
  - **Time Complexity**:
    - The `get` and `put` operations take **O(log n)** because determining the least frequently used key involves maintaining frequency order.

#### **Performance Summary**
| Algorithm | `get` Time Complexity | `put` Time Complexity | Eviction Strategy                     |  
|-----------|------------------------|------------------------|---------------------------------------|  
| LRU       | O(1)                  | O(1)                  | Removes the least recently used item  |  
| MRU       | O(1)                  | O(1)                  | Removes the most recently used item   |  
| LFU       | O(log n)              | O(log n)              | Removes the least frequently used item|

#### **Technologies**
- **Language**: Java
- **Build System**: Maven
- **Testing Framework**: JUnit 4


### Features:
- [x] Cache Operations:
  - [x] **LRU Cache**: The cache is implemented using the LRU (Least Recently Used) algorithm.
      - **Get**: Get the value of a key.
      - **Put**: Insert a key-value pair.
  
  - [x] **MRU Cache**: The cache is implemented using the MRU (Most Recently Used) algorithm.
      - **Get**: Get the value of a key.
      - **Put**: Insert a key-value pair.
    
  - [x] **LFU Cache**: The cache is implemented using the LFU (Least Frequently Used) algorithm.
      - **Get**: Get the value of a key.
      - **Put**: Insert a key-value pair.


- [x] **Cache Data Structures**:
  - [x] **LRU Cache** 
      - **Doubly Linked List**
      - **HashMap**
  - [x] **MRU Cache**
      - **Doubly Linked List**
      - **HashMap**
  - [x] **LFU Cache**
      - **TreeMap**
      - **HashMap**
  





#### The application is the project assignment for the course "Data-Structures" at the Harokopio University of Athens.
### Team Members:
| Ονοματεπώνυμο             | Email                    |
|---------------------------|--------------------------|
| Αρβανίτης Σπυρίδων        | it2023003@hua.gr         |
| Πολίτης Αλέξιος           | it2023100@hua.gr         |
| ~~Ξηρομερίτης Δημήτριος~~ | it2023052@hua.gr         |


## Usage

Compile using:

```
mvn compile
```

Create a jar using:

```
mvn package
```

Run main using:

```
java -cp target/cache3.0.jar org.CacheEx.App
```

Run unit tests using:

```
mvn test
```
