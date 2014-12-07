//Rotsching Cristofor 323CA


	Pentru implementare retelei de MessageCenter-uri am folosit un obiect de tip 
ArrayList<MessageCenter> in care am introdus toate MessageCenter-urile create
dupa ce am citit primele n linii de la fisierul de intrare .
Astfel am apelat metoda add() a obiectului de tip ArrayList<MessageCenter>
si am transmis ca parametru un obiect instantiat cu ajutorul unei clase anonime.
   Pentru citirea din fisier am folosit un BufferdReader care primeste ca parametru 
un InputReader. Cu ajutorul metodei readLine() , am reusit sa parcurg fisierul
de configurare linie cu line , iar prelucrarea sa am realizat-o cu ajutorul
unui obiect de tip Scanner .Astfel de la al doilea cuvant pana la al treilea 
cuvant din linia citita , am apelat o metoda implementata de mine pentru a-mi 
returna un obiect de tip Component functie de tipul String de componenta
citid din fisier .

	Pentru implementare metodei start() din SimulationManager am instantiat 
un obiect de tip Scanner ,care primeste ca parametru Stream-ul de intrare 
si anume System.in , iar cu ajutorul acestuia am luat fiecare linie prin apelarea
metodei readLine() , pe care am impartit-o in 3 substringuri specifice fiecarei
tip de faze : precaptura ,captura ,postcaptura . Fiecare substring era apoi
parsat cu ajutorul unui obiect StringTokenizer , cu ajutorul caruia desparteam
substringul in partile sale componente , pe care le introduceam intr-un ArrayList<String>.
Prin apelarea metodei indexOf() am reusit sa iau pozitia in substring a fiecarui
cuvant cheie , stiind ca pe pozitia fix urmatoare acestuia se afla valoare
fiecarei actiuni necesare pentru prelucrarea imaginii . Astfel am reusit sa 
creez mesaje specifice fiecarui tip de actiune si sa le trimit apoi MessageCenter-ului
delegat pentru a le prelucra . Pentru actiunea de tip Zoom a fazei de precaptura
am creat o clasa denumita Point , care memora in doua variabile de tip Integer
valorile x si y de inceput , respectiv de sfarsit pentru actiunea Zoom.
Pentru implementare actinii de tip RAW si NORMAL , daca se intalnea keyword-ul
"raw" atunci trimiteam un mesaj de tip MessageImage catre MessageCenter
cu atributul TaskType setat pe TaskType.RAW .Daca intalneam keyword-ul "normal"
atunci trimiteam catre MessageCenter un mesaj de tip MessageImage de tip RAW ,
apoi trimiteam un mesaj de tip MessageImage cu atributul TaskType setat pe 
TaskType.NORMAL. 
Pentru faza de postcaptura am folosit aceeasi metoda ca si la celelalte faze, 
adica am prelucrat substring-ul specific actiunilor pentru postcaptura,
introducand toate string-urile creeate de un StringTokenizer intr-un ArrayList<String>.
Pe baza string-urilor prezente in ArrayList am creat mesajele specifice pentru
a le trimite MessageCenter-ului . 
Cand se ajungea cu iteratia prin ArrayList, utimul mentionat , la ultimul element,
atunci cream un mesaj de tip MessageSave , pe care il trimiteam la MessageCenter
pentru a salva matricea de pixeli intr-o imagine. 

	Pentru implementarea Componentelor ,mai specific pentru metoda notify()
a fiecarei componente am facut astfel :

-pentru Componenta Zoom , mesajul care venea ca parametru la functia notify
continea doua obiecte de tipul Point. Aceste doua puncte reprezinta cele doua 
puncte intre care se face zoom-ul . Pentru selectarea pixelilor doar intre cele
doua puncte ,am calculat diferenta pe inaltime si pe latime ,pentru a itera in 
matricea de pixeli originala doar intre valorile de la 0 la deltaHeight pe linie ,
respectiv pe coloane de la 0 la deltaWidth ,si adaugam valorile intr-o matrice care
continea doar protiunea cuprinsa intre cele doua puncte .

-pentru Componenta Blur (la care de altfel am intampinat cele mai multe dificultati)
am reusit sa implementez suma valorilor vecinilor sub forma iterativa ,astfel 
am luat un contor pentru linii de la i-1 pana la i+1 ,respectiv pe coloana
de la j-1 la j+1 , iar daca un pixel al carui doream sa ii calculez media vecinilor
era pixel de colt ,sau de muchie atunci verificam prin IF-uri daca valorile de
pozitie ale vecinilor lui erau < 0 , > width +1 ,  < 0 si > height +1 ,respectiv
i si j (nu puteam lua in considerare cazul cand un pixel se considera pe el insusi 
vecin ).Daca se trecea de aceste cazuri exceptionale atunci incrementam contorul
vecinilor valizi(pentru ca la urma sa calculez media fiecarei culori a vecinilor).
La ce am avut dificultati a fost faptul ca a trebuit sa aleg o matrice auxiliara
pentru a realiza blur-ul ,pentru ca schimband valorile pe aceeasi matrice ,noile
valori calculate ale vecinilor unui pixel erau dependente de valorile calculate
cu un pas inainte.

-pentru Componentele BlackWhite si Sepia am aplicat formulele sugerate in
enuntarea temei ,cu o singura nelamurire . Nu am inteles de ce prin folosirea
valorilor cu virgula de tip float (ex.0.393 in loc de 0.393f) valorile culorilor
pozelor difereau doar prin valoarea 1 . (am folosit hexdump si diff pentru a imi da seama
de acest lucru ) .

-pentru Componenta de tip Flash am folosit de asemenea indrumarile din enuntul 
temei . Nu am intalnit nici o dificultate in dezvoltarea acestei componente. 

Observatii : 
	As fi dorit ca in scheletul de cod sa se fi inlocuit System.out.println() din
metoda publish() a unui messageCenter cu metoda System.out.print(mesaj + "System.getProperty("line.separator"))
pentru eliminarea oricarei ambiguitati .Am observat acest lucru cand fisierele de
output erau identice , dar cand le-am analizat cu hexdump aparea un caracter in
plus la sfarsitul fiecarui cuvant .
