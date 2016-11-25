java -jar libs/jflex-1.6.1.jar -d ../java/com/trhoanglee/ast SimpleC.lex 
java -jar libs/java-cup-11a.jar -interface -destdir ../java/com/trhoanglee/ast SimpleC.cup