# Zugriffskontrolle mit Spring Security

Starte die Applikation:

    mvn spring-boot:run
    
Du kannst nun die Applikation unter folgenden URLs aufrufen: 
- http://localhost:8080/employees
- http://localhost:8080/departments

Das Resultat der Aufrufe, ist dass die Ressource für unberechtigten Zugriff gesperrt sind.

## cURL

Um mit dieser Übung arbeiten zu können, solltest Du cURL installieren. 

- macOS: `brew install curl`
- Windows: https://stackoverflow.com/a/16216825/5155817

Hinweis: Mit `curl -v ...` werden die Details der Kommunikation via HTTP angezeigt.

## Aufgaben

Diese Applikation zeigt Spring Security konfiguriert für RESTful Services und basiert auf 
http://ryanjbaxter.com/2015/01/06/securing-rest-apis-with-spring-boot/.

### Verstehen der Konfiguration

Schau Dir die Klasse `ch.juventus.example.security.WebSecurityConfiguration` an. Was genau ist konfiguriert?

Schau Dir in der Klasse `ch.juventus.example.ExampleApplication` folgenden Code an:

    Role userRole = new Role("user");
    Account bob = new Account("bob", "secret");
    bob.addRole(userRole);

Was genau passiert hier?

Schau Dir die Klasse `ch.juventus.example.security.AccountBasedUserDetailsService.java` an. Was ist der Zweck dieser Klasse?

### User Role Tabellen

Die Applikation implementiert formbasierte Authentifizierung mit datenbankbasierten Benutzermanagement
(mit H2 als In-Memory DB).

Zur Persistenz mit Spring Data: 
- Die Klassen `Account` und `Role` repräsentieren Datenbanktabellen. 
- Instanzen dieser Klassen repräsentieren Zeilen in diesen Tabellen.

Welche Tabellen gibt es insgesamt, um die Benutzer der Anwendung zu verwalten?
Wie sind die Beziehungen der Tabellen zueinander?
Ist das Passwort sicher abgelegt?

### Authentifizieren

Welche Response gibt der Server bei unauthentifiziertem Request auf eine geschützte Ressource?

    curl -v -H "Accept:application/json" http://localhost:8080/employees
    
Um sich zu authentifizieren, kann ein Request mit den Credentials an die Webapplikation geschickt werden.

Dabei gilt folgendes:

- `/login`: Default-URL von Spring Security für formularbasierten Login
- `username=...&password=...'`: HTTP Body des POST Requests mit Spring Security's Default-Namen 
der Credentials.

Sende nun folgenden Request:

    curl -i -X POST -d username=bob -d password=secret http://localhost:8080/login -c cookie.txt

In der Response siehst Du, dass die Anmeldung erfolgreich war. 
Mit Hilfe des HTTP Cookies, welcher in `cookie.txt` abgelegt wurde, kannst Du einen weiteren Request senden. 
Dieser Request wird von Spring Securityanhand des Wertes des Cookies anhand des Werted des Cookies der existierenden Security-Session zugeordnet.

Sende nun folgenden Request:
    
    curl -i --header "Accept:application/json" -X GET -b cookie.txt http://localhost:8080/employees
    
Die Response sollte jetzt aus einer Liste von Employees bestehen.

### @Secured

Die `create`-Methode im `EmployeeController` ist mit der `ADMIN`-Rolle geschützt.
Was passiert, wenn man die Operation als "Bob" ausführen möchte?

    curl -X POST -H "Content-Type:application/json" -b cookie.txt -d '{"firstName":"Heidi","lastName":"Keppert","_links":{"department":{"href":"http://localhost:8080/departments/1"}}}' http://localhost:8080/employees
    
    < HTTP/1.1 403 
    < ...

Authentifiziere Dich am System mit `joe`/`secret` und versuche den Request erneut.
Diesmal sollte der Request akzeptiert werden.

    < HTTP/1.1 201   
    
### Bonus

Setze einen Breakpoint in der Methode `loadUserByUsername` in der Klasse `AccountBasedUserDetailsService`.
Starte die Applikation im Debug-Modus und authentifiziere Dich mit `curl`.

Wie funktioniert der `UserDetailsService`?
