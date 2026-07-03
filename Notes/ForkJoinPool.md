
# Key Points: 
- ForkJoinPool is optimized for recursive divide-and-conquer algorithms.
- It uses work stealing to keep all CPU cores busy.
- Each worker has its own deque, reducing lock contention.
- The owner thread processes tasks in LIFO order for better cache locality, while stealing threads take tasks in FIFO order to improve load balancing.
- Prefer the pattern fork() one subtask, compute() the other, then join() to avoid unnecessary scheduling overhead.
- It excels for CPU-bound tasks but performs poorly for blocking I/O operations like database access, HTTP requests, or Thread.sleep().
- CompletableFuture methods such as supplyAsync() and thenApplyAsync() use the common ForkJoinPool by default if you don't provide a custom Executor.