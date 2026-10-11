
# Java Multithreading — Quick Revision

## 1. Important Thread Methods

| Method            | Purpose                                                                                  |
| ----------------- | ---------------------------------------------------------------------------------------- |
| `start()`         | Starts a new thread; the JVM executes its `run()` method.                                |
| `run()`           | Defines the task performed by a thread. Calling it directly does not start a new thread. |
| `sleep(ms)`       | Pauses the current thread for a specified time. Does not release locks.                  |
| `yield()`         | Hints to the scheduler that another thread may run. No guarantee.                        |
| `join()`          | Makes the calling thread wait for another thread to finish.                              |
| `interrupt()`     | Requests interruption of a thread. Does not forcibly terminate it.                       |
| `isInterrupted()` | Checks a thread's interrupt status without clearing it.                                  |
| `interrupted()`   | Checks the current thread's interrupt status and clears it.                              |
| `isAlive()`       | Checks whether a thread has started and not yet terminated.                              |
| `currentThread()` | Returns the currently executing thread.                                                  |
| `setName()`       | Sets the thread's name.                                                                  |
| `getName()`       | Returns the thread's name.                                                               |
| `setPriority()`   | Sets the thread's priority (1–10).                                                       |
| `getPriority()`   | Returns the thread's priority.                                                           |
| `setDaemon(true)` | Marks a thread as a daemon; must be called before `start()`.                             |
| `isDaemon()`      | Checks whether a thread is a daemon.                                                     |

## 2. Synchronization Methods

| Method / Keyword | Purpose                                                                                    |
| ---------------- | ------------------------------------------------------------------------------------------ |
| `synchronized`   | Acquires a monitor lock to protect a critical section.                                     |
| `wait()`         | Releases the object's monitor and waits for notification, interruption, or another wakeup. |
| `notify()`       | Wakes one thread waiting on the same object's monitor.                                     |
| `notifyAll()`    | Wakes all threads waiting on the same object's monitor.                                    |

**Important:** `wait()`, `notify()`, and `notifyAll()` must be called while holding the monitor of the object on which they are invoked.

## 3. Thread Priority Constants

| Constant               | Value |
| ---------------------- | ----: |
| `Thread.MIN_PRIORITY`  |     1 |
| `Thread.NORM_PRIORITY` |     5 |
| `Thread.MAX_PRIORITY`  |    10 |

Higher priority does not guarantee earlier execution.

## 4. Important Differences

### `start()` vs `run()`

* `start()` creates a new thread of execution.
* `run()` called directly behaves like a normal method call.
* A `Thread` object cannot be started twice.

### `sleep()` vs `wait()`

* `sleep()` belongs to `Thread`; `wait()` belongs to `Object`.
* `sleep()` does not release monitor locks.
* `wait()` releases the monitor of the object on which it is called.
* `wait()` requires ownership of that object's monitor.

### `yield()` vs `join()`

* `yield()` gives the scheduler a hint to run another thread.
* `join()` makes the calling thread wait for another thread to terminate, or until a specified timeout.

### Daemon vs Non-Daemon Threads

* Daemon threads provide background services.
* Non-daemon threads generally keep the JVM alive until they terminate.
* The JVM may exit when no non-daemon threads remain, even if daemon threads are still running.

### Instance vs Static Synchronized Methods

* Instance synchronized method: locks `this`.
* Static synchronized method: locks the declaring class's `Class` object.
* Synchronized block: locks the specified object.

## 5. Thread States

| State           | Meaning                                  |
| --------------- | ---------------------------------------- |
| `NEW`           | Created but not started.                 |
| `RUNNABLE`      | Ready to run or currently running.       |
| `BLOCKED`       | Waiting to acquire a monitor lock.       |
| `WAITING`       | Waiting indefinitely for another action. |
| `TIMED_WAITING` | Waiting for a limited time.              |
| `TERMINATED`    | Execution has finished.                  |

Examples:

* `sleep()` → `TIMED_WAITING`
* `wait()` → `WAITING`
* `join()` → `WAITING` (untimed) or `TIMED_WAITING` (timed)
* Waiting to enter a synchronized block → `BLOCKED`

## 6. Interview Reminders

1. `sleep()` and `wait()` can throw `InterruptedException`.
2. `wait()` should generally be used inside a `while` loop that checks the condition.
3. `notify()` does not immediately release the monitor; the notified thread must reacquire it.
4. `interrupt()` is a cooperative interruption mechanism.
5. Thread priority and `yield()` do not guarantee execution order.
6. Call `setDaemon(true)` before starting the thread.
7. Synchronization prevents race conditions only when shared access is correctly coordinated.



