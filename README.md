# 🐧 Pinguinskat

Ein Java-basiertes Skat-Kartenspiel mit grafischer Benutzeroberfläche, das als Teil einer Spielbibliothek entwickelt wurde.

## 📋 Überblick

Pinguinskat ist ein vollständig funktionsfähiges Skat-Spiel, das in Java mit Swing implementiert wurde. Das Spiel bietet eine intuitive grafische Benutzeroberfläche und verwaltet automatisch die Spielregeln, Kartenverteilung und Punktevergabe.

## 🎮 Features

- **Vollständiges Skat-Spiel** mit allen Standardregeln
- **Grafische Benutzeroberfläche** mit Kartenbildern und Spieltisch
- **Scoreboard-System** zur Verfolgung der Spielergebnisse
- **Automatische Spielverwaltung** (Kartenverteilung, Punkteberechnung)
- **Responsive Design** mit 1400x800 Auflösung
- **Eingebettet in Spielbibliothek** mit Hauptmenü

## 🏗️ Projektstruktur

```
Pinguinskat/
├── src/
│   ├── Main.java                 # Hauptklasse der Spielbibliothek
│   ├── menu/                     # Menü-System
│   │   ├── Game.java            # Spiel-Objekt für Menü
│   │   └── MenuPanel.java       # Hauptmenü-Panel
│   └── skat/                     # Skat-Spiel Implementierung
│       ├── Skat.java            # Skat Hauptklasse
│       ├── GamePanel.java       # Spiel-Panel mit Game Loop
│       ├── Board.java           # Spiellogik und -zustand
│       ├── Card.java            # Karten-Objekt
│       ├── CardManager.java     # Kartenverwaltung
│       ├── Deck.java            # Kartendeck
│       ├── Functions.java       # Hilfsfunktionen
│       ├── GameStats.java       # Spielstatistiken
│       ├── ScoreboardManager.java # Scoreboard-Verwaltung
│       └── ClickableBox.java    # Interaktive UI-Elemente
├── res/                         # Ressourcen (Bilder, Icons)
├── data/                        # Spielerdaten
│   └── scoreboard.csv          # Scoreboard-Daten
└── out/                         # Kompilierte Klassen
```

## 🚀 Installation & Ausführung

### Voraussetzungen
- Java 8 oder höher
- IntelliJ IDEA (empfohlen) oder anderer Java-IDE

### Kompilierung und Ausführung

1. **Spielbibliothek starten:**
   ```bash
   javac -cp src src/Main.java
   java -cp src Main
   ```

2. **Skat direkt starten:**
   ```bash
   javac -cp src src/skat/Skat.java
   java -cp src skat.Skat
   ```

3. **Mit IntelliJ IDEA:**
   - Projekt öffnen
   - `Main.java` ausführen für die Spielbibliothek
   - `Skat.java` ausführen für direktes Skat-Spiel

## 🎯 Spielregeln

Das Spiel folgt den Standard-Skat-Regeln:
- 3 Spieler (2 Computer + 1 Mensch)
- 32 Karten (7, 8, 9, 10, Jack, Queen, King, Ace in 4 Farben)
- Bieten und Reizen
- Trumpfbestimmung
- Stich-Spiel
- Punkteberechnung

## 📊 Scoreboard

Das Spiel speichert automatisch Spielergebnisse in `data/scoreboard.csv`:
- Datum und Uhrzeit
- Spielername
- Erreichte Punkte

## 🛠️ Technische Details

- **Sprache:** Java
- **GUI Framework:** Swing
- **Auflösung:** 1400x800 Pixel
- **FPS:** 60 Frames pro Sekunde
- **Threading:** Game Loop in separatem Thread
- **Datenformat:** CSV für Scoreboard

## 🎨 Ressourcen

Das Spiel verwendet verschiedene Bildressourcen:
- `icon.png` - Anwendungsicon
- `table.png` - Spieltisch-Hintergrund
- `karten2.jpeg` - Kartenset
- `rückseite.png` - Kartenrückseite
- `Bib.jpeg` - Spielbibliothek-Hintergrund

## 🧪 Tests

Das Projekt enthält Test-Klassen:
- `Tests.java` - Skat-spezifische Tests
- `TestMain.java` - Allgemeine Tests

## 📝 Lizenz

Dieses Projekt ist für private Zwecke entwickelt worden.

## 👨‍💻 Autor

Entwickelt von Tom Tiedtke

---

*Viel Spaß beim Skat spielen! 🃏*
