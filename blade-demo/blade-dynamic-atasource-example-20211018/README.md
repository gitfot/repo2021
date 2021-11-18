

## PageHelper使我们自定义的Mybatis Interceptor插件失效的原因

不是所有的拦截器都必须要指定先后顺序。
对于常见的Executor 类型拦截器和StatementHandler类型拦截器来说，它们在整体执行的逻辑上是不同的，在 Executor 中的 query 方法执行过程中会调用StatementHandler。所以StatementHandler 属于 Executor 执行过程中的一个子过程。 所以这两种不同类别的插件在配置时，一定是先执行 Executor 的拦截器，然后才会轮到 StatementHandler。所以这种情况下配置拦截器的顺序就不重要了，在 MyBatis 逻辑上就已经控制了先后顺序。

所以如果你一个是Executor 类型的拦截器，一个是StatementHandler类型的拦截器，你可以不用管他顺序，也就是说你只须要定义好类型都Executor的拦截器顺序。

类型都为Executor的拦截器顺序问题
如果你的拦截器定义的顺序是这样的（你可以通过获取sqlSessionFactory.getConfiguration()去查看里面的InterceptorChain然后看到各个interceptor的顺序）：

<plugins>
    <plugin interceptor="com.github.pagehelper.ExecutorQueryInterceptor1"/>
    <plugin interceptor="com.github.pagehelper.ExecutorQueryInterceptor2"/>
    <plugin interceptor="com.github.pagehelper.ExecutorQueryInterceptor3"/>
</plugins>

他执行的顺序不是先执行1,2,3，而执行的顺序是3,2,1。

Interceptor3:{
    Interceptor2: {
        Interceptor1: {
            target: Executor
        }
    }
}
从这个结构应该就很容易能看出来，将来执行的时候肯定是按照 3>2>1>Executor>1>2>3 的顺序去执行的。

MyBatis的拦截器采用责任链设计模式，多个拦截器之间的责任链是通过动态代理组织的。我们一般都会在拦截器中的intercept方法中往往会有invocation.proceed()语句，其作用是将拦截器责任链向后传递，本质上便是动态代理的invoke。

回到pagehelper源码查看，可以看到其inercept方法直接获取了excutor然后开始分页查询，当查询到结果时，便返回了。在此，我们发现了关键点，那就是pagehelper的intercept方法中没有invocation.proceed()，这意味着什么？这意味着pagehelper没有继续向后传递责任链，而是自行处理直接返回。由此，我们可以猜出该问题大概率与拦截器的执行顺序有关。通过断点调试，验证了该猜想，当遇到分页查询时，执行到pagehelper就结束了，没有进入我们的自定义拦截器。

解决方案如下:

1、创建一个监听器，确保我们自定义的拦截器最后加入到拦截器队列中

参考->SqlSessionListener.java

来源：
hydra-Mybatis自定义拦截器与插件开发https://juejin.cn/post/6945614751334400014