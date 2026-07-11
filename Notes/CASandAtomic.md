# CAS(Compare and Swap)

CAS(

Memory Value,

Expected Value,

New Value

)

    1. It atomically compares the current value with an expected value and updates it only if they match.
    2. compareAndSet(expected, newValue) returns true on success and false if another thread modified the value first.
    3. CAS is lock-free, but not wait-free—a thread may have to retry many times.
    4. Under low to moderate contention, CAS is often faster than locks because it avoids blocking and context switches.
    5. Under very high contention, repeated CAS failures can waste CPU due to spinning.


# AtomicInteger
    1. Atomic classes provide lock-free, thread-safe operations using CAS.
    2. incrementAndGet() returns the new value; getAndIncrement() returns the old value.
    3. compareAndSet() is the core operation behind all atomic updates.
    4. Atomic classes are ideal for single-variable atomic operations.
    5. They do not automatically make multiple operations atomic—combinations like get() followed by decrementAndGet() can still have race conditions.
    6. Use LongAdder instead of AtomicInteger for very high-frequency counters with many concurrent updates.

AtomicReference<String> ref = new AtomicReference<>("A");