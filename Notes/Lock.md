# Synchronized

    -  Provides mutual exclusion (only one thread can execute the protected code at a time).
    -  Ensures visibility of shared data (changes made inside a synchronized block are visible to other threads that later acquire the same monitor). 
    -  Same thread can acquire the same monitor multiple times.
    -  automatic unlock
    -  If another thread owns the monitor, waits indefinitely
    -  wait(), notify(), notifyAll() , only work inside synchronized code. Calling them without owning the monitor throws -> IllegalMonitorStateException.

Limitations:- 
    
    -  No timeout, no tryLock

# ReentrantLock
    - Manual locking , unlocking. always unlock inside finally .
    - Otherwise the lock remains held and other threads may block forever.
    - it is reentrant means , same thread can call lock.lock() multiple times. Intenally it maintains 
       1. Owner thread , 2. hold count
    -  Every successful lock() requires one matching unlock().

    -  Only the thread owning the lock may call
       unlock()
          otherwise
       it throws IllegalMonitorStateException.

# Condition of ReentrantLock
    - Condition only works with ReentrantLock.
    - A Condition is always created from a specific ReentrantLock using lock.newCondition().
    - await() releases the lock, puts the thread to sleep, and reacquires the lock before returning.
    - signal() wakes one waiting thread; signalAll() wakes all waiting threads.
    - Always call await(), signal(), and signalAll() while holding the associated lock.
    - Always use a while loop around await() to recheck the condition after waking.
    - Unlike synchronized, ReentrantLock allows multiple independent waiting queues through multiple Condition objects, making coordination between different types of threads (like producers and consumers) much cleaner.


# ReadWriteLock
    - ReadWriteLock separates synchronization into a read lock and a write lock.
    - Multiple threads can hold the read lock simultaneously.
    - Only one thread can hold the write lock at a time.
    - While a writer is active, no readers or other writers can proceed.
    - Use it when reads greatly outnumber writes; otherwise, the added overhead may not be worthwhile.
    - The common implementation is ReentrantReadWriteLock.
    - Readers and writers cannot hold the lock at the same time.
    - The implementation includes logic to reduce writer starvation, so once a writer is waiting, new readers may be blocked until the writer gets a chance to run.

Existing readers

✔ continue

New readers

  ✔ usually allowed

  ✖ but may be blocked if a writer is waiting to avoid writer starvation
  

# StampedLock
    
    1. StampedLock supports read, write, and optimistic read modes.
    2. Every lock acquisition returns a stamp (long), which is required for unlocking or validation.
    3. Optimistic reads do not actually acquire a lock. They read first and then validate whether a write occurred concurrently.
    4. If validation fails, fall back to a normal read lock.
    5. It is not reentrant, so the same thread must not try to acquire the same lock again.
    6. It does not support Condition.
    7. Its main advantage appears in very read-heavy, performance-sensitive applications, where avoiding the overhead of read-lock acquisition can significantly improve throughput.
    8. validate() checks whether any write occurred after the optimistic read started, not whether the value you happened to read is correct.

validate(stamp) is not checking whether the value you read is correct.

It is checking:

"Was there any successful write after this stamp was issued?"

No → Trust the optimistic read.

Yes → Discard the optimistic read and read again under a real read lock.


# Semaphore

It's used frequently in backend systems to limit the number of threads that can access a resource simultaneously.

    1. A Semaphore manages permits, not ownership.
    2. acquire() decreases the permit count; release() increases it.
    3. When no permits are available, threads block (unless using tryAcquire()).
    4. Semaphore(1) behaves like a binary semaphore but is not a replacement for ReentrantLock because it isn't reentrant and doesn't enforce ownership.
    5. Use a Semaphore when you want to allow up to N threads to use a shared resource concurrently.