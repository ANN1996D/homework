项目文件结构：（说明：本文件所在文件夹即为当前路径"."）
相关依赖配置：	"./pom.xml"
源文件夹：	"./src/"
编译目标文件夹: "./target/"
服务器文件存储路径："./files/"

Java源文件均在".\src\main\java\com\example\homework"下，其中：
	controller-----控制器相关类
	item----数据类
	repository-----数据库操作接口
	service-----控制器相关服务操作类
	HomeworkApplication.java为主程序入口
资源文件夹为：".\src\main\resources\",其中
	resources存放的网页图标
	static存放网页静态资源
	templates是前端的页面.html文件
	application.properties为项目的相关配置（数据库连接等）
可执行文件：
	".\target\homework-0.0.1-SNAPSHOT.jar"