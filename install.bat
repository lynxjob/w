@echo on
set JAVA_HOME=C:\Program Files\Java\jdk1.7
set classpath=.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;WebRoot/WEB-INF/classes/;WebRoot/WEB-INF/lib/hibernate3.jar;WebRoot/WEB-INF/lib/spring.jar;WebRoot/WEB-INF/lib/commons-logging-1.1.jar;WebRoot/WEB-INF/lib/aspectjweaver.jar;WebRoot/WEB-INF/lib/dom4j-1.6.1.jar;WebRoot/WEB-INF/lib/commons-dbcp.jar;WebRoot/WEB-INF/lib/slf4j-api-1.5.8.jar;WebRoot/WEB-INF/lib/slf4j-log4j12-1.5.8.jar;WebRoot/WEB-INF/lib/log4j-1.2.15.jar;WebRoot/WEB-INF/lib/ejb3-persistence.jar;WebRoot/WEB-INF/lib/hibernate-annotations.jar;WebRoot/WEB-INF/lib/jta-1.1.jar;WebRoot/WEB-INF/lib/commons-pool.jar;WebRoot/WEB-INF/lib/hibernate-commons-annotations.jar;WebRoot/WEB-INF/lib/commons-collections-3.1.jar;WebRoot/WEB-INF/lib/javassist-3.11.0.GA.jar;WebRoot/WEB-INF/lib/mysql-connector-java-5.1.7-bin.jar;WebRoot/WEB-INF/lib/cglib.jar;WebRoot/WEB-INF/lib/cglib-nodep-2.1_3.jar;WebRoot/WEB-INF/lib/asm.jar;WebRoot/WEB-INF/lib/antlr-2.7.6.jar;
set path=C:\Program Files\Java\jdk1.6.0_35\bin

java pams.install.Installer

pause
