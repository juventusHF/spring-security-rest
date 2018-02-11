# RESTful Services mit Spring Boot 

Starte die Applikation:

    mvn spring-boot:run
    
Du kannst nun die Applikation unter folgenden URLs aufrufen: 
- http://localhost:8080/employees
- http://localhost:8080/departments

## cURL

Um mit dieser Übung arbeiten zu können, solltest Du cURL installieren. 

- macOS: `brew install curl`
- Windows: https://stackoverflow.com/a/16216825/5155817

Hinweis: Mit `curl -v ...` werden die Details der Kommunikation via HTTP angezeigt.

## Aufgaben

### User Role Tabellen

welche tabellen gibt es? account, role, account_role.

### anmelden und cookie merken

    curl -v -H "Content-Type: application/json" http://localhost:8080/employees
    
fail!
login

    curl -v  -d 'username=bob&password=secret' http://localhost:8080/login
    
check cookie 
    
    curl -v -H "Content-Type: application/json" --cookie 'JSESSIONID=D0053D0ADA4E7BD34CC402EF90A4C5A5;'  http://localhost:8080/employees