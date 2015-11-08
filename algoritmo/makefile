JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Tunnel.java \
	Link.java
default: classes
classes: $(CLASSES:.java=.class)
clean:
	$(RM) *.class
	$(RM) *.log
	$(RM) *.aux
