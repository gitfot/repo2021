


##推送目标

通过使用标签，别名，Registration ID 和用户分群，开发者可以向特定的一个或多个用户推送消息。

###标签

为安装了应用程序的用户打上标签，其目的主要是方便开发者根据标签，来批量下发 Push 消息。 可为每个用户打多个标签。 举例： game, old_page, women

###别名

每个用户只能指定一个别名。 同一个应用程序内，对不同的用户，建议取不同的别名。这样，尽可能根据别名来唯一确定用户。

Android 开发者参考文档：Android 标签和别名 iOS 开发者参考文档：iOS 标签和别名 使用别名和标签推送请参考文档：[Push API v3 Audience](https://docs.jiguang.cn//jpush/server/push/rest_api_v3_push/#audience)

###用户分群

用户分群的筛选条件有：标签、地理位置、活跃用户、系统版本、智能标签。 比如，开发者可以设置这样的用户分群：位于北京、上海、广州和深圳，并且最近 7 天内的活跃用户。 开发者可以通过在控制台设置好用户分群之后，在控制台推送时指定该分群的名称或使用 API 调用该分群的 id 发送。

用户分群控制台使用指南：[用户分群](https://docs.jiguang.cn//jpush/console/Instructions/#_16)

###Registration ID
客户端初始化 JPush 成功后，JPush 服务端会分配一个 Registration ID，作为此设备的标识（同一个手机不同 App 的 Registration ID 是不同的）。开发者可以通过指定具体的 Registration ID 来进行对单一设备的推送。



参考链接：
https://www.mk192.com/archives/32.html
https://blog.csdn.net/weixin_42404521/article/details/107512865
https://www.cnblogs.com/ywbmaster/p/12436044.html