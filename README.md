# Cache

### Description:
The application is the project assignment for the course "Data-Structures" at the Harokopio University of Athens.
The application is a work in progress that implements simple Cache algorithms. 
It currently implements the LRU (Least Recently Used) algorithm using a doubly linked list and a hashmap.
The application is written in Java, uses the Maven build system and is tested using JUnit 4.

### Features:
- [ ] Cache Operations:
  - [x] **LRU Cache**: The cache is implemented using the LRU (Least Recently Used) algorithm.
      - **Get**: Get the value of a key.
      - **Put**: Insert a key-value pair.
  
  - [ ] **MRU Cache**: The cache is implemented using the MRU (Most Recently Used) algorithm.
      - **Get**: Get the value of a key.
      - **Put**: Insert a key-value pair.
    
  - [ ] **LFU Cache**: The cache is implemented using the LFU (Least Frequently Used) algorithm.
      - **Get**: Get the value of a key.
      - **Put**: Insert a key-value pair.


- [ ] **Cache Data Structures**:
  - [x] **LRU Cache** 
      - **Doubly Linked List**
      - **HashMap**
  - [ ] **MRU Cache**
      - **Doubly Linked List**
      - **HashMap**
  - [ ] **LFU Cache**
      - **Doubly Linked List**
      - **MinHeap**

#### The application is the project assignment for the course "Data-Structures" at the Harokopio University of Athens.
### Team Members:
| Ονοματεπώνυμο         | Email                    |
|-----------------------|--------------------------|
| Αρβανίτης Σπυρίδων    | it2023003@hua.gr         |
| Πολίτης Αλέξιος       | it2023100@hua.gr         |
| Ξηρομερίτης Δημήτριος | it2023052@hua.gr         |


##### To use the app, you need to install the following dependencies:
- ***Java***  (version 11 or higher)
- ***Apache Maven*** 
- ***JUnit 4*** 

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
java -cp target/cache1.0.jar org.CacheEx.App
```

Run unit tests using:

```
mvn test
```