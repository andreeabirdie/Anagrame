# Anagrame
Proiectați și implementați o aplicație client-server pentru următoarea problemă.
Un joc cu 3 jucători numit Anagrame. Trei utilizatori autentificați pot juca acest joc. Pentru un set de litere primite
de la server, fiecare jucător alcătuiește un cuvânt format din literele respective. Jucătorul/Jucătorii care obține/obțin
cele mai multe puncte după 4 runde, câștigă jocul.
Fiecare jucător poate să facă următoarele:
1. Login. După autentificarea cu succes se deschide o nouă fereastră în care este afișat un buton “Start joc”.
Doar după ce trei jucători se autentifică în aplicație, butonul respectiv poate fi apăsat de ultimul jucător
autentificat. Când se apasă butonul de pornire a jocului, serverul va alege aleator un set de litere din baza de
date (ex. {‘a’, ‘c’, ‘e’,’r’}) și îl va trimite celor 3 jucători.
2. Alcătuiește cuvânt. După primirea unui set de litere de la server, fiecare jucător alcătuiește un cuvânt cu cât
mai multe litere din setul respectiv și îl trimite la server. După ce toți jucătorii au trimis cuvintele la server,
serverul verifică cuvintele primite astfel:
• dacă jucătorul nu a completat nimic la un cuvânt sau cuvântul nu este corect, jucătorul va primi 0
puncte pe răspunsul respectiv;
• dacă cuvântul alcătuit este corect, dar nu a folosit toate literele din setul dat, jucătorul va primi
numărul de puncte asociat cuvântului în baza de date, din care se va scădea numărul de litere
nefolosite din setul dat;
• dacă cuvântul alcătuit este corect și a folosit toate literele din setul dat, jucătorul va primi numărul de
puncte asociat cuvântului propus în baza de date.
Dupa verificarea cuvintelor, serverul trimite tuturor jucătorilor punctajul obținut de fiecare jucător la runda
respectivă și setul de litere pentru runda următoare. Aceste informații vor apărea automat pe interfața grafică a
fiecărui jucator.
Acest pas se repeta de încă 3 ori. La finalul celor 4 runde, serverul va trimite tuturor jucătorilor punctajul
total obținut de fiecare jucător și toți jucătorii vor vedea clasamentul pe interfața grafică.
3. Un serviciu REST care permite vizualizarea setului de litere alese de server pentru fiecare rundă a unui
anumit joc.
4. Un serviciu REST care pentru un anumit joc si un anumit jucător permite vizualizarea setului de litere
generate de server la fiecare rundă, a cuvântului alcătuit de jucătorul respectiv la fiecare runda și a
punctajului obținut de jucător la fiecare rundă .
Exemplu:
Pentru setul de litere{‘a’, ‘c’, ‘e’, ’r’}, la server sunt considerate corecte următoarele cuvinte și au punctajul:
Observație:
Seturile de litere, cuvintele corecte alcătuite cu literele din set si punctajul pentru fiecare cuvânt se păstrează
în baza de date.
Cerințe:
• Aplicația poate fi dezvoltată în orice limbaj de programare (Java, C#, etc).
• Datele vor fi preluate/salvate dintr-o bază de date relațională.
• Pentru o entitate (exceptând jucator) se va folosi un instrument ORM pentru stocarea datelor.
• Pentru testarea serviciilor REST se va folosi o extensie a unui browser web/aplicație (Postman,
REST client, etc).
Set litere Cuvânt Punctaj
{‘a’, ‘c’, ‘e’,’r’} care 4
{‘a’, ‘c’, ‘e’,’r’} acre 6
{‘a’, ‘c’, ‘e’,’r’} arce 7
{‘a’, ‘c’, ‘e’,’r’} cer 3
{‘a’, ‘c’, ‘e’,’r’} arc 4
{‘a’, ‘c’, ‘e’,’r’} car 3
