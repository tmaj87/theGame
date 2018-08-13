# theGame
Praca Dyplomowa

Tomasz Maj

Gra dostępna jest pod adresem http://pracadyplomowa.ddns.net:1234

# Wstęp
Coraz częściej duże firmy programistyczne opierają swoje usługi o środowiska rozproszone, gdzie jeden fizyczny serwer zaczyna mieścić w sobie wiele wirtualnych maszyn. W środowisku takim skalowalność nie jest problemem, wystarczy tylko wystartować kolejną kopię już uruchomionej usługi i przekierować na nią ruch sieciowy.

Weźmy za przykład platformę oferującą filmy na żądanie, która w ostatnich latach zyskała dużą popularność – Netflix. Są oni pionierami rozwiązań opartych o architekturę mikroserwisów, czyli luźno powiązanych elementów, które dzielą ten sam kontekst. W tym przypadku celem jest dostarczenie strumienia wideo dla każdego użytkownika platformy, niezależnie czy korzysta z przeglądarki, smart tv czy aplikacji na tablecie. Obecnie Firma Netflix sporą część swoich rozwiązań udostępnia za darmo, na zasadzie otwartego kodu źródłowego. Rozwijali swoje produkty mając na uwadze wysoką dostępność swoich usług.

Kluczowym elementem do konstruowania aplikacji w architekturze mikroserwisów jest Spring Boot, pozwalający wykorzystać funkcjonalności dostępnych w pakiecie Java EE (ang. Enterprise Edition). Takich jak JMS (ang. Java Message Service), EJB (ang. Enterprise Java Beans) oraz rozwiązania IoC (ang. Inversion of Control).

W mojej pracy opiszę grę opartą o środowisko mikrousług. Wygranym będzie jeden z graczy, losowy wybrany przez serwer, aktualnie biorący udział w grze. Opiszę również sposób, w jaki można zaimplementować obsługę wielu takich gier. Program odporny będzie na usterki, jak i posiadać będzie możliwość skalowalności w przypadku większego ruchu. Praca będzie napisana przy użyciu języka Java w wersji 8.


# Spis treści
1. Wstęp
2. Parę słów o języku Java
3. Co to są mikroserwisy
    1. Wysoka dostępność dzięki skalowaniu
    2. Tolerancja awarii
    3. Obsługa wielu platform
    4. Korzyści płynące z tworzenia mikroserwisów
    5. Problemy płynące z tworzenia mikroserwisów
    6. Komunikacja między mikroserwisami
4. Gra wieloosobowa
    1. Koncepcja rozgrywki
    2. Aspekt wieloosobowości
    3. Użyte biblioteki
        1. Spring Boot (Pivotal)
        2. Eureka (Netflix)
        3. Feign (Netflix)
        4. Hystrix (Netflix)
        5. SockJs i STOMP
        6. Bootstrap (Twitter)
        7. jQuery (jQuery Foundation)
        8. Inne
5. Mikroserwisy
    1. Klient gry
    2. Serwer gry
    3. Tablica wyników
    4. Repozytorium wygranych
    5. Eureka
    6. Serwer konfiguracji
    7. Monitoring
6. Możliwości skalowania
    1. Skalowanie pionowe (w górę)
    2. Skalowanie poziome (w bok)
7. Metodologia pisania programu
    1. SOLID
        1. Single responsibility principle
        2. Open/closed principle
        3. Liskov substitution principle
        4. Interface segregation principle
        5. Dependency inversion principle
    2. Zasady wielowątkowego programu
    3. System kontroli wersji
    4. Testy
8. Podsumowanie pracy
9. Bibliografia
