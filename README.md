# 简介 
openofiice + pdf.js + Stringboot 写的在线预览demo
## 1 openoffice 安装
window 安装参考 http://blog.csdn.net/buyingfei8888/article/details/13354575
linux 安装
1. 下载 wget https://sourceforge.net/projects/openofficeorg.mirror/files/4.1.3/binaries/zh-CN/Apache_OpenOffice_4.1.3_Linux_x86-64_install-rpm_zh-CN.tar.gz 
http://www.openoffice.org/zh-cn/download/上选择合适版本
2. 解压 tar -zxvf Apache_OpenOffice_4.1.3_Linux_x86-64_install-rpm_zh-CN.tar.gz
3. 解压后生成文件夹zh-CN 进到RPMS目录下 rpm -Uvh *.rpm desktop-integration/openoffice4.1.3-redhat-menus-4.1.3-9783.noarch.rpm 
4. 启动  nohup C:\Program Files (x86)\OpenOffice 4\program\soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard &
5. 查看服务是否启动（端口8100是否被soffice占用）：netstat -lnp |grep 8100
显示结果：tcp        0      0 127.0.0.1:8100              0.0.0.0:*                   LISTEN      19501/soffice.bin
大功告成！！！
## 2 启动Springboot
1.maven 打包mvn clean package -Dmaven.test.skip=true
2. 运行打包后的jar java -jar XXX.jar
## 3 页面
http://localhost:8000/upload 上传页面
pdf.js 通过viewer页面的路径后加参数file={fileurl}来访问对应的pdf示例： http://localhost:8000/index?file=download/xx.pdf  
http://localhost:8000/index  pdf预览主页
## 4 另一种在线预览方式 使用微软的Office在线服务
http://view.officeapps.live.com/op/view.aspx?src= + URLEncode（文件url）
