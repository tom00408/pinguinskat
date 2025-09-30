# Java-Compiler und -Laufzeit
JAVAC = javac
JAVA = java

# Verzeichnisse
SRC_DIR = src
OUT_DIR = out
RES_DIR = res

# Classpath
CLASSPATH = $(SRC_DIR):$(RES_DIR)

# Hauptklassen
MAIN_CLASS = Main
SKAT_CLASS = skat.Skat

# Alle Java-Dateien finden
JAVA_SOURCES = $(shell find $(SRC_DIR) -name "*.java")

# Standard-Ziel
all: compile

# Kompilieren aller Java-Dateien
compile:
	@echo "Kompiliere Java-Dateien..."
	@mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) -cp $(CLASSPATH) $(JAVA_SOURCES)
	@echo "Kompilierung abgeschlossen!"

# Spielbibliothek starten
run: compile
	@echo "Starte Spielbibliothek..."
	$(JAVA) -cp $(OUT_DIR) $(MAIN_CLASS)

# Skat direkt starten
skat: compile
	@echo "Starte Pinguinskat..."
	$(JAVA) -cp $(OUT_DIR) $(SKAT_CLASS)

# Aufräumen (entfernt kompilierte Klassen)
clean:
	@echo "Räume auf..."
	rm -rf $(OUT_DIR)
	@echo "Aufräumen abgeschlossen!"

# Alle .class-Dateien im src-Verzeichnis entfernen
clean-src:
	@echo "Entferne .class-Dateien aus src/..."
	find $(SRC_DIR) -name "*.class" -delete
	@echo "Aufräumen von src/ abgeschlossen!"

# Hilfe anzeigen
help:
	@echo "Verfügbare Ziele:"
	@echo "  all      - Kompiliert alle Java-Dateien (Standard)"
	@echo "  compile  - Kompiliert alle Java-Dateien"
	@echo "  run      - Startet die Spielbibliothek"
	@echo "  skat     - Startet Pinguinskat direkt"
	@echo "  clean    - Entfernt alle kompilierten Dateien"
	@echo "  clean-src- Entfernt .class-Dateien aus src/"
	@echo "  help     - Zeigt diese Hilfe an"

# Phony-Ziele (keine Dateien)
.PHONY: all compile run skat clean clean-src help