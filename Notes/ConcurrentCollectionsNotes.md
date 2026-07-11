


# Concurrent Collections - Short Interview Notes

## ConcurrentHashMap ⭐⭐⭐⭐⭐

-   Thread-safe HashMap.
-   Reads mostly lock-free.
-   Writes: CAS + bucket locking (`synchronized` on first node).
-   Important internals: `Node`, `TreeNode`, `TreeBin`,
    `ForwardingNode`, `sizeCtl`, `helpTransfer()`.
-   No `null` key/value.
-   Atomic APIs: `putIfAbsent()`, `computeIfAbsent()`, `compute()`,
    `merge()`.

------------------------------------------------------------------------

## ConcurrentLinkedQueue

-   Lock-free (CAS).
-   FIFO.
-   Michael-Scott queue.
-   Non-blocking (`offer`, `poll`, `peek`).

------------------------------------------------------------------------

## BlockingQueue

-   `put()` → blocks if full.
-   `take()` → blocks if empty.
-   `offer()/poll()` → immediate/timed versions.

------------------------------------------------------------------------

## ArrayBlockingQueue

-   Fixed capacity.
-   Circular array.
-   **1 ReentrantLock**
-   Conditions: `notFull`, `notEmpty`.
-   Low memory, predictable.

------------------------------------------------------------------------

## LinkedBlockingQueue ⭐⭐⭐⭐⭐

**Internals** - Linked List - `head`, `last` - `AtomicInteger count` -
`putLock` - `takeLock` - `notFull`, `notEmpty`

**Why 2 locks?** - Producer & Consumer work on opposite ends. - Better
concurrency.

**Why AtomicInteger?** - `count` modified under both locks.

**Signalling** - Producer → acquires `takeLock` → `notEmpty.signal()` -
Consumer → acquires `putLock` → `notFull.signal()`

------------------------------------------------------------------------

## PriorityBlockingQueue

-   Binary Heap.
-   Unbounded.
-   Priority order (not FIFO).
-   Only `notEmpty`.
-   Equal priorities are **not** insertion ordered.

------------------------------------------------------------------------

## DelayQueue

-   Elements implement `Delayed`.
-   Ordered by expiry time.
-   `take()` waits until delay expires.
-   Built on PriorityQueue.

------------------------------------------------------------------------

## SynchronousQueue ⭐⭐⭐⭐⭐

-   Capacity = **0**
-   No storage.
-   Direct Producer ↔ Consumer handoff.
-   `put()` waits for consumer.
-   Used by `Executors.newCachedThreadPool()`.
-   Fair=true → Queue(FIFO)
-   Fair=false → Stack(LIFO)

------------------------------------------------------------------------

## LinkedTransferQueue

-   Lock-free.
-   `put()` → enqueue.
-   `transfer()` → waits until consumer receives item.
-   `tryTransfer()` → transfers only if consumer waiting.

------------------------------------------------------------------------

## LinkedBlockingDeque

-   Blocking double-ended queue.
-   Insert/remove from both ends.

------------------------------------------------------------------------

## ConcurrentLinkedDeque

-   Lock-free deque.
-   CAS based.

------------------------------------------------------------------------

## ConcurrentSkipListMap

-   Sorted concurrent map.
-   Skip List.
-   O(log n).
-   Alternative to TreeMap.

------------------------------------------------------------------------

## ConcurrentSkipListSet

-   Sorted concurrent set.
-   Built on ConcurrentSkipListMap.

------------------------------------------------------------------------

# Important Interfaces

-   ConcurrentMap
-   BlockingQueue
-   TransferQueue
-   BlockingDeque
-   NavigableMap
-   NavigableSet

------------------------------------------------------------------------

# Important Abstract Classes

-   AbstractCollection
-   AbstractList
-   AbstractSet
-   AbstractQueue
-   AbstractMap

------------------------------------------------------------------------

# Marker Interfaces

-   RandomAccess
-   Cloneable
-   Serializable

------------------------------------------------------------------------

# Most Asked Interview Questions

-   HashMap vs ConcurrentHashMap
-   Why no null in ConcurrentHashMap?
-   Why LinkedBlockingQueue has 2 locks?
-   Why AtomicInteger count?
-   ArrayBlockingQueue vs LinkedBlockingQueue
-   PriorityBlockingQueue vs DelayQueue
-   Why SynchronousQueue capacity is 0?
-   transfer() vs put()
-   ConcurrentHashMap resize (`sizeCtl`, `helpTransfer()`)


# Important Concurrent Collections Internals (Interview Revision)


## 1. ConcurrentHashMap ⭐⭐⭐⭐⭐

### Purpose

Thread-safe HashMap with high concurrency.

### Internals

    table[]
       |
     Bucket
       |
     Node -> Node
       |
     TreeBin (if many collisions)

**Important Fields** - `table` - `sizeCtl` - `baseCount` - `Node` -
`TreeNode` - `TreeBin` - `ForwardingNode`

**Locking** - Reads: mostly lock-free - Empty bucket insert: CAS -
Existing bucket update: `synchronized(firstNode)`

**Resize** - `sizeCtl` controls resize threshold. - Threads cooperate
using `helpTransfer()`. - `ForwardingNode` marks migrated buckets.

**Interview** - Why no `null`? - CAS + synchronized together? -
Treeification? - Purpose of `sizeCtl`?

------------------------------------------------------------------------

## 2. ArrayBlockingQueue ⭐⭐⭐⭐⭐

### Internals

    Circular Array
    head --> [][][] <-- tail

**Fields** - `items[]` - `count` - `putIndex` - `takeIndex` - One
`ReentrantLock` - `notFull` - `notEmpty`

**Locking** - Single lock protects entire queue.

**Why one lock?** - Array updates affect shared indices.

**Complexity** - put/take: O(1)

**Interview** - Circular array? - Why one lock?

------------------------------------------------------------------------

## 3. LinkedBlockingQueue ⭐⭐⭐⭐⭐

### Internals

    dummy -> node -> node -> last

**Fields** - `head` - `last` - `AtomicInteger count` - `putLock` -
`takeLock` - `notFull` - `notEmpty`

**Why two locks?** - Producers modify tail. - Consumers modify head. -
Can run concurrently.

**Why AtomicInteger?** - `count` is shared between both locks.

**Cross Signalling** - Producer inserts → signals `notEmpty` - Consumer
removes → signals `notFull`

**Complexity** - put/take: O(1)

------------------------------------------------------------------------

## 4. PriorityBlockingQueue ⭐⭐⭐⭐

### Internals

-   Binary Heap
-   Unbounded
-   One `ReentrantLock`
-   Only `notEmpty`

**Why no `notFull`?** - Queue is unbounded.

**Complexity** - put/take: O(log n) - peek: O(1)

------------------------------------------------------------------------

## 5. DelayQueue ⭐⭐⭐⭐

### Internals

-   Built on `PriorityQueue`
-   Elements implement `Delayed`
-   Ordered by expiry time

**take()** - Waits until head delay expires.

**Optimization** - Leader-Follower: only one timed waiter.

------------------------------------------------------------------------

## 6. SynchronousQueue ⭐⭐⭐⭐⭐

### Internals

    Producer <----> Consumer
    (No storage)

**Capacity** - 0

**Implementations** - Fair=true → `TransferQueue` (FIFO) - Fair=false →
`TransferStack` (LIFO)

**Used By** - `Executors.newCachedThreadPool()`

**Interview** - Why size() == 0? - Why cached thread pool uses it?

------------------------------------------------------------------------

## 7. LinkedTransferQueue ⭐⭐⭐

### Internals

-   Lock-free
-   CAS
-   Linked list

**Methods** - `put()` - `transfer()` - `tryTransfer()`

**Difference** - `transfer()` waits until consumer receives item.

------------------------------------------------------------------------

# Quick Comparison

  -----------------------------------------------------------------------------
Collection              Structure         Locking           Special
  ----------------------- ----------------- ----------------- -----------------
ConcurrentHashMap       Hash Table        CAS + bucket lock Cooperative
resize

ArrayBlockingQueue      Circular Array    One Lock          Fixed capacity

LinkedBlockingQueue     Linked List       Two Locks         Better
concurrency

PriorityBlockingQueue   Binary Heap       One Lock          Priority

DelayQueue              Priority Heap     One Lock          Time based

SynchronousQueue        No storage        Direct handoff    Capacity 0

LinkedTransferQueue     Linked List       Lock-free         transfer()
-----------------------------------------------------------------------------

# Last Minute Revision

-   ConcurrentHashMap → CAS + bucket lock + sizeCtl + helpTransfer()
-   ArrayBlockingQueue → Circular array + 1 lock
-   LinkedBlockingQueue → Linked list + 2 locks + AtomicInteger
-   PriorityBlockingQueue → Heap + unbounded
-   DelayQueue → Delayed + Leader/Follower
-   SynchronousQueue → Capacity 0 + TransferStack/TransferQueue
-   LinkedTransferQueue → transfer() waits for consumer





# Concurrent Collections - Short Interview Notes

## ConcurrentHashMap ⭐⭐⭐⭐⭐

-   Thread-safe HashMap.
-   Reads mostly lock-free.
-   Writes: CAS + bucket locking (`synchronized` on first node).
-   Important internals: `Node`, `TreeNode`, `TreeBin`,
    `ForwardingNode`, `sizeCtl`, `helpTransfer()`.
-   No `null` key/value.
-   Atomic APIs: `putIfAbsent()`, `computeIfAbsent()`, `compute()`,
    `merge()`.

------------------------------------------------------------------------

## ConcurrentLinkedQueue

-   Lock-free (CAS).
-   FIFO.
-   Michael-Scott queue.
-   Non-blocking (`offer`, `poll`, `peek`).

------------------------------------------------------------------------

## BlockingQueue

-   `put()` → blocks if full.
-   `take()` → blocks if empty.
-   `offer()/poll()` → immediate/timed versions.

------------------------------------------------------------------------

## ArrayBlockingQueue

-   Fixed capacity.
-   Circular array.
-   **1 ReentrantLock**
-   Conditions: `notFull`, `notEmpty`.
-   Low memory, predictable.

------------------------------------------------------------------------

## LinkedBlockingQueue ⭐⭐⭐⭐⭐

**Internals** - Linked List - `head`, `last` - `AtomicInteger count` -
`putLock` - `takeLock` - `notFull`, `notEmpty`

**Why 2 locks?** - Producer & Consumer work on opposite ends. - Better
concurrency.

**Why AtomicInteger?** - `count` modified under both locks.

**Signalling** - Producer → acquires `takeLock` → `notEmpty.signal()` -
Consumer → acquires `putLock` → `notFull.signal()`

------------------------------------------------------------------------

## PriorityBlockingQueue

-   Binary Heap.
-   Unbounded.
-   Priority order (not FIFO).
-   Only `notEmpty`.
-   Equal priorities are **not** insertion ordered.

------------------------------------------------------------------------

## DelayQueue

-   Elements implement `Delayed`.
-   Ordered by expiry time.
-   `take()` waits until delay expires.
-   Built on PriorityQueue.

------------------------------------------------------------------------

## SynchronousQueue ⭐⭐⭐⭐⭐

-   Capacity = **0**
-   No storage.
-   Direct Producer ↔ Consumer handoff.
-   `put()` waits for consumer.
-   Used by `Executors.newCachedThreadPool()`.
-   Fair=true → Queue(FIFO)
-   Fair=false → Stack(LIFO)

------------------------------------------------------------------------

## LinkedTransferQueue

-   Lock-free.
-   `put()` → enqueue.
-   `transfer()` → waits until consumer receives item.
-   `tryTransfer()` → transfers only if consumer waiting.

------------------------------------------------------------------------

## LinkedBlockingDeque

-   Blocking double-ended queue.
-   Insert/remove from both ends.

------------------------------------------------------------------------

## ConcurrentLinkedDeque

-   Lock-free deque.
-   CAS based.

------------------------------------------------------------------------

## ConcurrentSkipListMap

-   Sorted concurrent map.
-   Skip List.
-   O(log n).
-   Alternative to TreeMap.

------------------------------------------------------------------------

## ConcurrentSkipListSet

-   Sorted concurrent set.
-   Built on ConcurrentSkipListMap.

------------------------------------------------------------------------

# Important Interfaces

-   ConcurrentMap
-   BlockingQueue
-   TransferQueue
-   BlockingDeque
-   NavigableMap
-   NavigableSet

------------------------------------------------------------------------

# Important Abstract Classes

-   AbstractCollection
-   AbstractList
-   AbstractSet
-   AbstractQueue
-   AbstractMap

------------------------------------------------------------------------

# Marker Interfaces

-   RandomAccess
-   Cloneable
-   Serializable

------------------------------------------------------------------------

# Most Asked Interview Questions

-   HashMap vs ConcurrentHashMap
-   Why no null in ConcurrentHashMap?
-   Why LinkedBlockingQueue has 2 locks?
-   Why AtomicInteger count?
-   ArrayBlockingQueue vs LinkedBlockingQueue
-   PriorityBlockingQueue vs DelayQueue
-   Why SynchronousQueue capacity is 0?
-   transfer() vs put()
-   ConcurrentHashMap resize (`sizeCtl`, `helpTransfer()`)

