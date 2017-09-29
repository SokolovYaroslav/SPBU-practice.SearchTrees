
# Search Trees

## Perfomance analisys of different search trees

### Searching value in tree

|Amount of valuses|Ordered or Randomize|Binary Search tree, ms|Red-Black tree, ms|B-tree, ms|
|-----------------|----------------|---------------|------------------|----------|
|100              |Randomize|3.9|10.5|51|
|100              |Ordered|10|6.3|0.9|
|10 000           |Randomize|50|173|309|
|10 000           |Ordered|1677|160|26|
|1 000 000        |Randomize|3156|8855|1690|
|1 000 000        |Ordered|TOO LONG|7256|210|

As you can see from this table, **with evenly spread values Binary Search tree can be as fast as Red-Black tree**. 

B-tree has a very good search time, but **only if the number of values is large**.
