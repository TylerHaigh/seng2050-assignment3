JC := javac
SOURCES := $(wildcard WEB-INF/classes/rgms/**/*.java)

all: build

build:
	$(JC) $(SOURCES)

clean: FILES = $(SOURCES:.java=.class)
clean:
	$(RM) $(FILES)

.PHONY: clean all