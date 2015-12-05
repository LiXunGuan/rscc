--[V_1.0.4] 2015-09-25

* [修复和优化]
1.修复了呼通之后保存意向和成交的类型都一样的问题
2.修复了呼通之后保存意向在我的客户中也出现了此号码的问题
3.修复了技能组状态排队的号码重复的问题
4.修复了删除用户，用户任务管理没有对应删除的问题
5.修复了部门数据管理中页面排序刷新之后错乱的问题
6.修复了呼通之后保存客户客户池为空，号码可重复的问题
7.修复了呼通之后保存为客户，我的客户无此条号码的问题
8.修复了添加工单重复两条的问题
9.修复了删除坐席没有解绑分机的问题
10.修复了部门分配数据的方式问题
11.修复了报表部门字段问题
12.修复了呼入路由默认json格式的问题
13.修复了技能组管理设置静态队列无修改按钮的问题
14.修复了高级选项中坐席各个上限的问题
15.修复了部门数据管理分配不均匀的问题
16.修复了网关配置名称不允许输入汉字的提示问题
17.修复了角色去掉管理员所有的用户都无默认的四个菜单的问题
18.修复了录音质检中的录音都一样的问题
19.修复了成交客户量没有统计的问题
20.修复了部门数据管理获取数据条数不对的问题
21.修复了我的任务勾选批次获取数据的问题
22.修复了我的共享池重复获取耗损cpu的问题
23.修复了删除意向类型失败的问题
24.修复了保存意向客户和成交客户合并同一个表的问题
25.修复弹屏页面保存和呼叫按钮的位置问题
26.修复了角色管理中无管理员角色的问题
27.修复了修改人员部门无法获取该部门的数据的问题
28.修复了录音质检中存在no agent的问题
29.修复了客户信息中预约提醒没绑定客户的问题
30.修复了呼叫中心性能测试的问题
31.修复了群呼任务重复，页面卡住的问题
32.修复了坐席角色不能修改订单的问题
33.修复了点击一条录音页面无反应的的问题
34.修复了意向客户未保存黑名单的问题
35.修复了客户管理中拥有者是admin时无数据结果的问题
36.修复了弹屏tab间歇性不弹的问题
37.修复了批次与部门更换后数据数量不同步的问题
38.修复了tab页面可菜单可拖动的问题
39.修复了群呼中断的问题
40.优化了订单管理的逻辑处理问题
41.优化了弹屏获取来电的归属地的问题
42.优化了freeswitch页面自动应用的问题
43.优化了部门无上限限制设置的问题
44.优化了部门数据管理中无回收和回收次数的问题
45.优化了高级选项的显示排版问题
46.优化了各个菜单英文显示以及文字相关项不一致的问题
47.优化了我的任务去除用户名的问题
48.优化了数据管理菜单结构的功能
49.优化了我的录音和录音质检合并的问题
50.优化了用户管理中无角色字段和角色筛选的问题
51.优化了静态群呼结束之后还有残留的问题
52.优化了群呼的数据无再次回收利用的问题
53.优化了群呼出现坐席分机不振铃的问题
54.优化了部门排序问题
55.优化了产品管理中产品图片是必填项的问题
56.优化了坐席菜单的排序问题
57.优化了拨打已存在客户的号码，未绑定客户类型和拥有者的问题
58.优化了录音质检中的时间筛选和增加坐席筛选字段的功能
59.优化了数据模块性能问题
60.优化了系统配置和IPPBX配置的菜单问题
61.优化了自动表管理显示全部字段的位置问题
62.优化了安装脚本没有无初始化用户的问题
63.优化了删除批次的模板位置问题
64.优化了批次中上传的进度和分配的进度颜色一致的问题
65.优化了当前上传的批次未首条显示的问题

* [新增功能]

1.增加了共享池的管理页面功能
2.增加了删除批次的功能
3.增加了自动呼叫的功能
4.增加了录音质检删除的功能
5.增加了业务报表客户管理模块功能
6.增加了业务报表数据管理模块功能
7.增加了订单管理导出功能
8.增加了CRM授权系统功能
9.增加了坐席置忙置闲原因配置的功能

--[V_1.0.3] 2015-09-15

* [修复和优化]

1.修复了全选筛选数据默认是全部数据的问题
2.修改了部门单次上限可以大于单日上限的问题
3.修复了不勾选直接点击回收数据没有提示的问题
4.修复了用户任务管理参数无结果匹配的问题
5.修复了隐藏号码连续拨打没有新弹屏和号码隐后加长的问题
6.修复了坐席获取数据无数据限制提示的问题
7.修复了一级回收数据需手动刷新才能看到结果的问题
8.修复了接入号-话务报表统计不准确的问题
9.修复了坐席-话务报表统计不准确的问题
10.修复了分机-话务报表统计不准确的问题
11.修复了新数据管理中号码没有隐藏的问题
12.修复了意向客户量和共享量统计不对的问题
13.修复了新数据管理连续呼叫不同号码没有新弹屏的问题
14.修复了部门内无数据再分配的报错提示问题
15.修复了面板中号码未隐藏的问题
16.修复了人员数据管理中未获取到数据的问题
17.修复了批次管理中转共享量，黑名单和废弃量没有统计的问题
18.修复了我的任务和意向客户管理都存在获取共享池数据的问题
19.修复了我的任务中无添加呼叫次数和呼叫时间字段的问题
20.修复了意向客户转共享池意向类型不对的问题
21.修复了我的任务呼叫数据转意向客户任务中还残留此数据的问题
22.修复了新数据管理中未关联权限的问题
23.修复了坐席无单次上限单日上限和总数据量限制的问题
24.修复了部门单日上限未起作用的问题
25.修复了一级领用数据成功的提示信息不对问题
26.修复了点击批次名没有相关数据的问题
27.修复了大量数据页面出来数据慢的问题
28.修复了录音质检中呼出的接入号不对的问题
29.修复了已经是客户的数据还可以再次被他人编辑的问题
30.修复了可以删除占用的队列的问题
31.修复了录音质检权限和被叫号为空的问题
32.修复了sys_call_log表中会出现多条call_session_uuid相同的数据的问题
33.修复了群呼不振铃的问题
34.修复了呼叫号码无归属地的问题
35.修复了报表时长没有按时分秒统计的问题
36.修复了网关配置名称可以重复的问题
37.修复了群呼中相同队列的任务未开启的跟随者开启的任务一同刷新的问题
38.修复了群呼管理中默认策略未填写的问题
39.修复了群呼未摘机已弹屏的问题
40.修复了计费明细中呼通时长与客服记录中的呼通时长不一致问题‘
41.修复了我的客服记录未按时间排序以及面板呼叫无呼叫标记的问题
42.修复了群呼未接通的数据不能再呼的问题
43.修复了录音质检群呼的记录的接入号不对的问题
44.修复了报表的导出功能问题
45.修复了技能组状态筛选不匹配的问题
46.修复了已删除的用户在可以被分配数据的问题
47.修复了新数据管理中部门无单次上限和单日上限的问题
48.修复了一个数据对应到不属于此条数据的沟通记录的问题
49.修复了新装的呼叫中心技能组显示报错的问题
50.修复了ivr配置添加之后无法保存的问题
51.修复了部门数据管理勾选几条或者本页数据未分配成功的问题
52.修复了同一个客户不同号码分别呼叫之后多个弹屏的问题
53.修复了添加的客户号码在数据库里被占用的问题
54.修复了数据管理中数据被操作后原来的页面查询按钮失效的问题
55.修复了部门数据管理中的参数无法筛选问题
56.修复了大数据量页面打开慢的问题
57.修复了共享量里面的筛选参数对数部门不对的问题
58.修复了我的客户中加0呼叫后出来一个新的弹屏的问题
59.修复了单次上限可以大于单日上限的问题
60.修复了批次管理中回收数据之后要手动刷新才能看到结果的问题
61.修复了坐席单次/单日上限条数的约束提示问题
62.修复了部门数据二次领用全选本页未回收成功的问题
63.修复了批次管理中回收数据提示不对的问题
64.修复了部门数据中意向客户量和共享量统计不对的问题
65.修复了用户任务管理中部门筛选的问题
66.修复了开启号码隐藏连续呼叫号码没有新弹屏隐号变长的问题
67.修复了开启号码隐藏部分号码未隐藏的问题
68.优化了报表打开慢的问题
69.优化了群呼模块的功能
70.优化了nginx在系统配置的功能
71.优化了报表字段的显示功能
72.优化了初始化SQL中添加相关表索引的功能
73.优化了批次管理的基础功能
74.优化了数据管理的体系和页面功能
75.优化了无批量坐席修改的功能
76.优化了系统首页的功能
77.优化了弹屏页面的布局功能
78.优化了高级选项的显示功能
79.优化了弹屏的弹出方式的功能
80.优化了支付方式和运费不为空的功能
81.优化了自动外呼无法检测数据进度的功能
82.优化了自动置忙置闲的功能
83.优化了计费报表的统计方式功能
84.优化了初始化配置内容的功能
85.优化了多方通话未绑定分机不能用的功能
86.优化了字段管理更改为高级选项的功能

* [新增功能]

1.增加新数据管理的菜单结果和坐席配置功能
2.增加了字典管理功能
3.增加了计费报表当天和历史记录的筛选功能
4.增加了业务报表计费管理的功能
5.增加了业务报表数据管理统计的功能
6.增加了共享池页面的功能
7.增加了坐席的意向客户页面的功能
8.增加了数据呼叫结果的后续流转功能
9.增加了我的任务管理页面
10.增加了部门数据管理页面的功能
11.增加了数据初始导入页面的功能
12.增加了业务报表的导出功能
13.增加了批次管理删除数据的逻辑功能
14.增加了报表权限限制功能
15.增加了标签页弹窗方式的功能

 --[V_1.0.2] 2015-08-22
* [修复和优化]
1.修复了增加网关端口号会自动加到IP后面的问题
2.修复了我的数据保存为客户数据中没有删除此号码的问题
3.修复了Gateway网关配置判断状态的问题
4.修复了客户信息中添加客户保存不了的问题
5.修复了用户管理批量添加前缀必填的问题
6.修复了非系统用户也可以找回密码的问题
7.修复了IPPBX配置中默认路由保存提示输入正确格式的问题
8.修复了修改admin邮箱不能显示默认邮箱的问题
9.修复了客户池修改和删除不了的问题
10.修复了呼出路由保存之后没有应用的问题
11.修复了客户编码可以修改的问题
12.修复了录音质检和我的录音查询不匹配和详情里面为空时报错以及接通时长不对的问题
13.修复了群呼呼通进来的录音字段接入号不对的问题
14.修复了我的数据保存为客户没有反应，呼叫一条再次保存提示操作失败的问题
15.修复了我的数据中保存为客户，数据中还存在的问题
16.修复了我的数据中点击呼叫和摘机之后弹出两个同页面的问题
17.修复了群呼任务中已被占用的数据可以再次被另个任务选用的问题
18.修复了群呼接通之后页面弹屏为null的问题
19.优化了登录框下面的公司信息不可配置的问题
20.优化了群呼任务修改数据的问题
21.优化了群呼任务的字段
22.优化了客户管理中呼叫和加0呼叫更改点击号码或者查询即可呼叫的功能
23.优化了页面显示可选行数的条数
24.优化了客户池管理添加无客户池创建人的问题
25.优化了admin默认权限问题
26.优化了tomcat和webapp合并的功能
27.优化了字典管理更改为高级选项
28.优化了管辖范围移动到用户管理中的功能
29.优化了客户池添加客户池创建人
30.优化了弹屏窗口显示接入号分机号和描述的功能
31.优化了技能组管理中的字段
32.优化了客户池添加归属部门的功能
33.优化了计费报表默认显示数据的问题
34.优化了rscc2000页面和中间件页面合并的问题
35.修复了工单、客户、录音质检的权限查看问题

* [新增功能]
1.新增了号码隐藏功能
2.新增了系统导出功能
3.新增了我的数据中获取数据功能
4.增加了自动编辑打包脚本
5.新增了费率字段

--[ V_1.0.1 ] 日期：2015-08-10

* [修复和优化]
1.修复了待办事项加载页面或者重新登录反复提醒的问题。
2.修复了IPPBX配置添加网关不能输入密码的问题。
3.修复了客户管理中移动客户没有限制部门权限的问题
4.修复了我的数据中客户池选项没有数据的问题
5.修复了计费明细中没有费率类型筛选和撤销功能的问题
6.修复了我的数据中将号码保存为客户不成功的问题
7.修复了滚动菜单内容跟着滚动的问题
8.优化了用户管理批量添加的密码生成的问题
9.优化了会议室功能被邀请人退出或者被踢出所在行也删除的问题
10.优化了超级用户不能修改邮箱的问题
11.优化了导入数据批次单线程的问题
12.优化了坐席置忙初始化的问题

3.增加了费率设置和计费明细查询的功能
