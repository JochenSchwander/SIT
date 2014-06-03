SIT
===
Client

Der Client liegt als ausführbares Jar-File mit dem Namen "Client.jar" im Ordner Rlease vor. Der Quellcode des Clients ist Ordner Client vorzufinden.

===
Server

Der Server liegt als Servlet mit dem Namen "SIT.war" im Ordner Release vor. Der Quellcode des Servers ist im Ordner Server vorzufinden.
Um den Server auszuführen wird ein Servlet-Container wie zum Beispiel der Apache-Tomcat benötigt, auf dem die Server-Anwendung installiert sein muss.

===
Webclient

Der Webclient ist unter der URL host:port/SIT/WebClient vorzufinden. Sein Quellcode ist Teil des Servers.

===
Java Lauzeitumgebung:

Als Java Laufzeitumgebung wird mindestens Java 7 mit der "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy" erwartet. Diese ist zum Beispiel für Java 7 hier http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html vorzufinden. Diese wird sowohl für den Client, als auch den Server erwartet.

===
Datenbank:

Als Datenbank wird eine MySQL Datenbank mit folgenden Daten erwartet:
- Host: localhost
- Port: 3306
- Name: sit 
- User: root
- Passwort: gargelkarx

Um die benötigte Tabellenstruktur anzulegen, kann die Klasse Main im Package de.hs_mannheim.sit.ss14.database des Servers ausgeführt werden.
