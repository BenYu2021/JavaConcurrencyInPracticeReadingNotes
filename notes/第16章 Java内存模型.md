# 第16章 Java内存模型

## 16.1 什么是内存模型，为什么需要它
### 16.1.1 平台内存模型
* 内存栅栏（特殊指令）
* 串行一致性

程序清单16-1 如果在程序中没有包含足够的同步，那么可能产生奇怪的结果（不要这么做）  
[PossibleReordering.java](../src/main/java/ch16/PossibleReordering.java) 

### 16.1.2 重排序
* 同步将限制编译器、运行时和硬件对内存操作重排序的方式，从而在实施重排序时不会破坏JMM提供的可见性保证。

### 16.1.3 Java内存模型简介
* JMM为程序中所有的操作定义了一个偏序关系，称之为Happen-Before。
* 偏序关系是集合上的一种关系，具有反对称、自反和传递属性。
* 要想保证执行操作的B的线程看到操作A的结果（无论A和B是否在同一个线程中执行），那么A和B之间必须满足Happen-Before关系。
>Happen-Before的规则包括：
>* 程序顺序规则：如果程序中操作A在操作B之前，那么在线程中A操作将在B操作之前执行。
>* 监视器锁规则：在监视器上的解锁操作必须在同一个监视器锁上的加锁操作之前执行。
>* volatile变量规则：对volatile变量的写入操作必须在对该变量的读操作之前执行。
>* 线程启动规则：在线程上对Thread.start的调用必须在该线程中执行任何操作之前执行。
>* 线程结束规则：线程中的任何操作都必须在其他线程检测到该线程已经结束周期执行，
>或者从Thread.join中成功返回，或者在调用Thread.isAlive时返回false。
>* 中断规则：当一个线程在另一个线程上调用interrupt时，必须在被中断线程检测到interrupt调用之前执行
>（通过抛出InterruptedException或者调用isInterrupted和interrupted）。
>* 终结器规则：对象的构造函数必须在启动该对象的终结器之前执行完成。
>* 传递性：如果操作A在操作B之前执行，并且操作B在操作C之前执行，那么操作A必须在操作C之前完成

### 16.1.4 借助同步

## 16.2 发布
* 造成不正确发布的真正原因：在“发布一个共享对象”与“另一个线程访问该对象”之间缺少一种Happen-Before规则。

### 16.2.1 不安全的发布
>除了不可变对象以外，使用被另一个线程初始化的对象通常都是不安全的，除非对象的发布操作是在使用该对象的线程开始使用之前执行。

程序清单16-3 不安全的延迟初始化（不要这么做）  
[UnsafeLazyInitialization.java](../src/main/java/ch16/UnsafeLazyInitialization.java) 

### 16.2.2 安全的发布
### 16.2.3 安全初始化模式
程序清单16-5 提前初始化  
[EagerInitialization.java](../src/main/java/ch16/EagerInitialization.java)  
程序清单16-6 延长初始化占位模式    
[ResourceFactory.java](../src/main/java/ch16/ResourceFactory.java) 

### 16.2.4 双重检查锁
程序清单16-7 双重检查锁（不要这么做）  
[DoubleCheckedLocking.java](../src/main/java/ch16/DoubleCheckedLocking.java) 
## 16.3 初始化过程中的安全性





