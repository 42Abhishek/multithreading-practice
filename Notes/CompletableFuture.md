
Difference between thenApply and thenCompose.

# `thenApply()` vs `thenCompose()` in `CompletableFuture`

## Quick Difference

| `thenApply()` | `thenCompose()` |
|---------------|-----------------|
| Used when the function returns a normal value (`T`) | Used when the function returns another `CompletableFuture<T>` |
| Transforms the result | Chains another asynchronous operation |
| Similar to `map()` | Similar to `flatMap()` |
| Can create nested futures | Flattens nested futures |

---

## `thenApply()`

Use it when the next operation is **synchronous**.

### Example

```java
CompletableFuture<String> future =
    CompletableFuture.supplyAsync(() -> "Abhishek")
        .thenApply(name -> name.toUpperCase());

System.out.println(future.join());
```

**Output**

```
ABHISHEK
```

Here the lambda returns:

```java
String
```

---

## `thenCompose()`

Use it when the next operation is **asynchronous**.

### Example

```java
CompletableFuture<String> getUser() {
    return CompletableFuture.supplyAsync(() -> "Abhishek");
}

CompletableFuture<String> getAddress(String user) {
    return CompletableFuture.supplyAsync(() -> user + " lives in Delhi");
}

CompletableFuture<String> future =
    getUser().thenCompose(user -> getAddress(user));

System.out.println(future.join());
```

**Output**

```
Abhishek lives in Delhi
```

Here the lambda returns:

```java
CompletableFuture<String>
```

---

## Why not `thenApply()` here?

```java
CompletableFuture<CompletableFuture<String>> future =
    getUser().thenApply(user -> getAddress(user));
```

Result:

```
CompletableFuture<
    CompletableFuture<String>
>
```

You would need:

```java
future.join().join();
```

`thenCompose()` automatically flattens this into:

```java
CompletableFuture<String>
```

---

## Visual

### `thenApply()`

```
Future<T>
    |
    v
Function: T -> R
    |
    v
Future<R>
```

### `thenCompose()`

```
Future<T>
    |
    v
Function: T -> Future<R>
    |
    v
Future<R>
```

---

## Real-world Example

```java
CompletableFuture<List<Order>> orders =
    getUser()
        .thenCompose(user -> getOrders(user.getId()));
```

Flow:

```
Fetch User
    ↓
Fetch Orders (Async)
    ↓
Orders
```

---

## Rule to Remember

✅ Use **`thenApply()`** when the function returns:

```java
T
```

✅ Use **`thenCompose()`** when the function returns:

```java
CompletableFuture<T>
```

---

## Easy Analogy

- **`thenApply()` ≈ `Stream.map()`**
- **`thenCompose()` ≈ `Stream.flatMap()`**

Just as `flatMap()` removes nested streams,

```
Stream<Stream<T>>
        ↓
Stream<T>
```

`thenCompose()` removes nested futures,

```
CompletableFuture<CompletableFuture<T>>
                  ↓
CompletableFuture<T>
```

---

## Interview One-liner

- **`thenApply()`** → Transform the result of a `CompletableFuture`.
- **`thenCompose()`** → Chain another asynchronous task and flatten the resulting `CompletableFuture`.


## ImportantPoints :-
1. thenApply() is not guaranteed to run on the thread that completed the previous stage.
2. It runs synchronously in the thread that performs the completion, which is usually the previous stage's thread, but can also be another thread (such as one calling join()) that helps complete pending stages.
3. thenApplyAsync() always submits the callback to an executor, so the callback is executed by a thread from that executor, not inline by the completing thread.