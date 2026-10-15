# Design Journal

## 05.08.2026

## Item i BundleItem dizajn

Prvotno sam planirala staviti boolean collected u Item klasu.
Odlučila sam to maknuti jer Item predstavlja samo definiciju predmeta.

Primjer:
Parsnip uvijek ostaje Parsnip.

Status predmeta ovisi o save fileu i bundleu.

Zato BundleItem sadrži:
- Item
- completed status

## Dodavanje Room klase

Dodana je Room klasa jer Community Center ima više prostorija.

Room će sadržavati bundleove.

Razlog:
Kasniji UI će prikazivati prostorije slično kao Stardew Valley.

## Dodavanje CommunityCenterData

Ova klasa služi za kreiranje svih početnih objekata potrebnih za rad aplikacije. 

Na primjer kreira Room Pantry u kojoj se nalaze bundles, jedan od njih Spring Crops Bundle u kojem se nalaze 4 Itema: Parsnip, Green Bean, Cauliflower i Potato.

Ti objekti su uvijek isti i nikad se ne mjenjaju nakon kreiranja.


## 06.08.2026

## Resources i repository

maknuta je klasa CommunityCenterData koja je bila samo među klasa prije nego što sam krenula raditi resources i repositories.

U resources su stavljene .json datoteke sa stvarnim podacima objekata. 

U repository folderu će biti klase koje će se baviti korištenjem .json datoteka u aplikaciji.

Za sada se radi na refaktorizaciji ostalih klasa modela kako bi se mogle koristiti sa .json datotekama.

## ItemRepository

Odgovornost:
- učitati item.json
- spremiti Item objekte
- omogućiti dohvat Item objekata

## 07.08.2026

## Refaktor na Maven 

Napravljen je novi projekt u mavenu gdje se sve kopiralo iz starog projekta kako bi moglo se raditi sa Json.

Dalje bi se trebalo krenuti sa ostalim repositories, za bundle i room klase pošto za njih postoje napravljeni .json datoteke unutar resources mape.

## JsonReader

Dodana klasa sa metodom readJson koju će korisiti svaka repository klasa pa nema potrebe za duplanjem te metode za svaku klasu.

Ovako ju imaju svi samo pozivom JsonReader.readJson()

## BundleRepository

Odgovornost:
- učitati bundle.json
- spremiti Bundle objekte
- omogućiti dohvat Bundle objekata

## JsonRepositoryHelper

Dodana klasa sa jednom generičkom metodom kojom se parsira lista <T> objekata koji su parsirani.

Bila je to metoda unutar vise repositories koja se mogal generički napisati kako bi se smanjio višak gotovo istih kodova.

## RoomRepository

Odgovornost:
- učitava room.json
- sprema Room objekte
- omogućava dohvat Room objekata

## BaseRepository

Napravljena generička klasa koja ima sve potrebne metode za pretragu i dohvatu objekata.

Te sve metode se dalje koriste u svim repository klasama. Opet smanjenje dupliciranog koda.

## SaveFileRepository

Klasa napravljenja da se bavi SaveFile objektom koje čita i sprema u .json datoteke. Svaka datoteka je poseban SaveFile objekt.

## Service

## ItemService, BundleService, RoomService i SaveService

klase za delegaciju repositorya kao priprema prije izrade gui-ja

## 09.08.2026.

Završen backend i sve pripreme prije nego što se krene raditi GUI.

Dodatno razrješeni maleni buggovi kod testiranja prije gui-ja.

## 7.10.2026.

## JavaFX GUI

Početak izrade GUI-ja

## GUI setup

Aplikacija koristi JavaFX sa Mavenom.

GUI prati FXML baziranu strukturu:

- 'App.java' je zaslužna za pokretanje JavaFX aplikacije i loading prvotnog FXML view.
- FXML datoteke definiraju strukturu i raspored korisničkog sučelja.
- Controller klase obrađuju interakciju korisnika i povezuje GUI sa logikom aplikacije.
- Scene BUilder je korišten za vizualno uređivanje FXML datoteka.

Trenutna struktura:

```text
App
  ↓
FXML View
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
Model / JSON
```

Napravljeni su minimalni main-view.fxml i MainController za potrebe testiranja.

Sljedeći korak je zamjena testnog sučelja sa pravim GUI-jem povezanim sa backendom aplikacije.


## GUI navigacija kroz Room, Bundle i Item

Implementiran je prvi funkcionalni dio JavaFX korisničkog sučelja koji povezuje GUI s postojećim backend slojem aplkacije

Glavni prikaz koristi BorderPane kao osnovni layout:

- gornjy dio sadrži naslov aplikacije
- lijevi dio sadrži popis Community Center Rooms
- središnji dio prikazuje sadržaj odabranog Room i Bundlea
- donji dio rezerviran je za prikaz napretka i buduće kontrole vezane uz spremanje

## Učitavanje Room

MainController koristi RoomService za dohvaćanje Room iz backenda

Tok podataka:

```
JSON
  ↓
RoomRepository
  ↓
RoomService
  ↓
MainController
  ↓
ListView<Room>
```

Rooms se učitava prilikom inicijalizacije FXMl controllera sa initialize()

ListView sadrži stvarne Room objekte.

## Odabir Rooms

Promjena selekcije u roomListView prati se pomoću selectedItemProperty() listenera.

Kada korisnik odabere room:

```
Room selection
  ↓
Room objekt
  ↓
showRoom(Room)
  ↓
room.getRequiredBundles()
  ↓
ListView<Bundle>
```
Naziv odabranog rooma prikazuje se u središnjem dijelu aplikacije, a njegova lista bundleova učitava se u bundleListView.

## Odabir Bundlea i prikaz Itema

Promjena selekcije u bundleListView također se prati pomoću listenera.

Kada korisnik odabere bundle:

```
Bundle selection
  ↓
Bundle objekt
  ↓
showBundle(Bundle)
  ↓
bundle.getRequiredItems()
  ↓
Item prikaz
```

Itemi odabranog bundlea dinamički se dodaju u itemContainer.

itemContainer je VBox definiran u FXML-u, dok se njegov sadržaj stvara u controlleru jer broj i sadržaj itema ovise o trenutno odabranom bundleu.

Time je napravljena podjela između:
- FXML/Scene Builder - statična struktura korisničkog sučelja
- Controller - dinamički sadržaj i reakcije na korisničke akcije
- Service sloj - pristup poslovnoj logici i podacima
- Repository sloj - učitavanje podataka iz JSON datoteka

## Trenutno stanje

GUI trenutno omogućuje:

- učitavanje Room podataka iz backenda
- prikaz naziva Room i odabir Room
- prikaz pripadajućih bundles i odabir jednog bundle
- prikaz pripadajućih items

trenutni prikaz je read-only. Items nisu još povezani sa stanjem korisničkog save filea.


## 8.10.2026.

## Model podataka i JSON struktura

Podaci potrebni za Community Center tracker pohranjeni su kao statički JSON resursi u src/main/resources/data/. Repository sloj učitava te podatke i pretvara ih u Java objekte koji se koriste u ostatku aplikacije.

## Seasons (Item atribut)

Jedan item može biti dostupan u jednoj ili više sezona, zbog čega Item koristi:

List<Season> seasons

umjesto jedne vrijednosti Season season.

U JSON-u se sezone uvijek zapisuju kao polje, čak i kada item pripada samo jednoj sezoni.

Primjeri:

"seasons": ["SPRING"]

"seasons": ["SUMMER", "FALL"]

Za iteme koji nisu ograničeni na određenu sezonu koristi se:

"seasons": ["ANY"]

Vrijednost ANY ne kombinira se s drugim sezonama.

ItemRepository prolazi kroz JSON polje seasons i svaku tekstualnu vrijednost pretvara u odgovarajući Season enum.

## Bundles s izborom Itema

Nisu svi bundleovi definirani tako da je potrebno prikupiti svaki ponuđeni item. Neki bundleovi imaju više mogućih itema nego što ih je potrebno za završetak.

Zbog toga Bundle osim liste requiredItems sadrži i:

int requiredItemCount

requiredItems predstavlja sve iteme koji mogu zadovoljiti bundle, dok requiredItemCount određuje koliko ih je stvarno potrebno.

Primjer:

Quality Crops Bundle

dostupna su 4 moguća itema

potrebno je prikupiti 3

requiredItems sadrži sva 4 itema

requiredItemCount iznosi 3

Kod običnih bundleova requiredItemCount jednak je broju itema u requiredItems.

Ovaj model omogućuje da GUI prikaže sve moguće opcije, dok se logika završetka bundlea kasnije može temeljiti na broju završenih itema, a ne na veličini liste.

## Povezivanje JSON podataka

Repositoryji povezuju podatke preko ID-eva:

items.json → bundle.json → room.json

BundleRepository za svaki ID iz requiredItems pronalazi odgovarajući Item preko ItemRepository.

RoomRepository na isti način povezuje sobe s pripadajućim bundleovima.

Ako JSON referencira ID koji ne postoji, repository prekida učitavanje i javlja grešku. Time se neispravni odnosi između podataka otkrivaju odmah pri pokretanju aplikacije.

## Trenutno stanje

Statički podaci potrebni aplikaciji uneseni su u JSON resurse i uspješno se učitavaju pri pokretanju aplikacije.

GUI trenutno omogućuje:

prikaz soba

prikaz bundleova odabrane sobe

prikaz itema odabranog bundlea

odabir save filea

prikaz spremljenog stanja itema

promjenu stanja itema pomoću checkboxa

ručno spremanje promjena u save file


## 13.10.2026.

## Završne funkcionalnosti

### Praćenje napretka

Za računanje napretka koristi se `ProgressService`. Time je logika računanja odvojena od `MainControllera`, čija je odgovornost prikaz rezultata u korisničkom sučelju.

Napredak se računa hijerarhijski:

`Item → Bundle → Room → ukupni Community Center`

Za svaki bundle broji se koliko je njegovih ponuđenih itema završeno u trenutno odabranom `SaveFileu`.

Budući da neki bundleovi imaju više ponuđenih itema nego što ih je potrebno za završetak, broj završenih zahtjeva ograničen je vrijednošću `requiredItemCount`.

Zbog toga se doprinos bundlea ukupnom napretku računa kao:

`min(broj završenih itema, requiredItemCount)`

Primjer je Quality Crops Bundle koji ima četiri moguća itema, ali zahtijeva samo tri. Označavanje četvrtog itema nakon što su tri već završena ne povećava ukupni napredak.

`ProgressService` sadrži odvojene metode za računanje napretka bundlea, sobe i liste svih soba. Više razine koriste već postojeću logiku nižih razina umjesto ponavljanja istog računanja.

Ukupan potreban broj zahtjeva dobiva se zbrajanjem `requiredItemCount` vrijednosti svih bundleova. `SaveFile.getTotalItemCount()` ne koristi se kao nazivnik jer save koristi sparse model i ne mora sadržavati zapis za svaki mogući item.

`MainController` koristi `ProgressService` za prikaz ukupnog napretka. Prikaz se automatski osvježava kada korisnik promijeni trenutno odabrani save ili označi odnosno odznači item.

### Izrada novog savea

Novi save moguće je napraviti iz korisničkog sučelja pomoću gumba `New Save`.

Korisnik unosi naziv pomoću JavaFX `TextInputDialoga`.

Tok kreiranja savea je:

`GUI → MainController → SaveService → SaveFileRepository → JSON`

`SaveService.createSave()` stvara novi `SaveFile`, dok je `SaveFileRepository` odgovoran za dodavanje savea i zapisivanje JSON datoteke na disk.

Novi save koristi sparse model i zato započinje s praznim `bundleProgress`.

Item koji nije prisutan u `bundleProgress` smatra se nezavršenim. Zapis za item dodaje se kada korisnik prvi put označi taj item kao završen.

Nakon uspješnog stvaranja novi `SaveFile` dodaje se u postojeći ComboBox i automatski se odabire. Postojeći listener ComboBoxa zatim postavlja novi `currentSave`, osvježava prikazane checkboxove i ponovno računa napredak.

Naziv savea ne smije biti prazan. Repository također sprječava stvaranje savea s nazivom koji već postoji. Greške se u GUI-ju prikazuju korisniku pomoću JavaFX `Alert` dijaloga, umjesto prekidanja rada aplikacije.

### Završni funkcionalni tok

Aplikacija nakon završetka funkcionalne implementacije omogućuje:

- učitavanje soba, bundleova i itema iz JSON resursa
- prikaz sadržaja odabrane sobe i bundlea
- odabir postojećeg savea
- prikaz stanja itema za odabrani save
- označavanje i odznačavanje završenih itema
- spremanje promjena u JSON save datoteku
- podršku za bundleove kod kojih je potreban samo dio ponuđenih itema
- računanje i automatsko osvježavanje ukupnog napretka
- izradu novog savea iz GUI-ja
- ponovno učitavanje spremljenih podataka nakon ponovnog pokretanja aplikacije

Funkcionalni dio aplikacije time je završen. Sljedeća faza razvoja usmjerena je na vizualno oblikovanje JavaFX korisničkog sučelja bez promjene postojeće poslovne logike.

## 15.10.2026.

## Vizualno oblikovanje GUI-ja

Nakon završetka funkcionalnog dijela započeto je vizualno oblikovanje JavaFX korisničkog sučelja.

Dodana je `style.css` datoteka koja odvaja vizualni izgled aplikacije od FXML strukture i Java logike.

GUI koristi toplu paletu smeđih, zlatnih i svijetlih tonova inspiriranu izgledom Stardew Valley Community Centera.

Stilizirani su:

- glavni dijelovi aplikacije
- naslovi
- Room i Bundle liste
- gumbi
- ComboBox
- ProgressBar
- checkbox prikaz itema

`itemContainer` promijenjen je iz `VBox` u `FlowPane` kako bi se itemi mogli prikazivati u više stupaca i automatski prilagođavati dostupnoj širini.

FlowPane se nalazi unutar ScrollPanea kako bi veći bundleovi ostali pregledni bez povećavanja cijelog prozora.

Povećana je i početna veličina aplikacijskog prozora kako bi se bolje iskoristio prostor za prikaz podataka.


## Vizualni prikaz završenih Roomova i Bundleova

`ProgressService` koristi se i za određivanje je li cijeli Room ili Bundle završen.

Završeni Room trenutno se označava zvjezdicom, a završeni Bundle simbolom poklona.

Nakon promjene stanja itema osvježavaju se Room i Bundle liste kako bi se oznake završetka odmah ažurirale.

Ove oznake trenutno predstavljaju funkcionalnu verziju vizualnog feedbacka i mogu se kasnije zamijeniti vlastitim grafičkim elementima.


## Lokacija korisničkih save datoteka

Save datoteke više se ne spremaju relativno uz direktorij iz kojeg je aplikacija pokrenuta.

Takav pristup nije prikladan za instaliranu desktop aplikaciju jer radni direktorij nije pouzdano mjesto za korisničke podatke.

`SaveFileRepository` sada koristi stabilnu lokaciju korisničkih podataka na Windowsu:

`%LOCALAPPDATA%\StardewCommunityCenterTracker\saves`

Ako `LOCALAPPDATA` nije dostupan, koristi se fallback direktorij unutar `user.home`.

Time su programske datoteke odvojene od korisničkih podataka, a saveovi ostaju dostupni neovisno o tome pokreće li se aplikacija kroz Maven ili kao instalirana Windows aplikacija.

Testirano je stvaranje savea, spremanje promjena, zatvaranje aplikacije i ponovno učitavanje podataka nakon pokretanja.


## Priprema aplikacije za distribuciju

Aplikacija je pripremljena za pokretanje izvan razvojnog okruženja.

Maven `package` faza stvara glavni JAR i kopira runtime dependencyje u `target/libs`.

`maven-dependency-plugin` koristi `runtime` scope kako testne biblioteke poput JUnita i Hamcresta ne bi završile u distribucijskom paketu.

Za Windows distribuciju koristi se JDK `jpackage`.

JavaFX moduli potrebni aplikaciji uključeni su u vlastiti runtime pomoću `jlink` procesa koji pokreće `jpackage`.

Time krajnji korisnik ne mora zasebno instalirati:

- JDK ili JRE
- JavaFX
- Maven
- VS Code


## Windows installer

Za izradu Windows installera koristi se `jpackage` zajedno s WiX Toolsetom.

Prvo je napravljen i testiran `app-image`, čime je potvrđeno da aplikacija radi s vlastitim runtimeom.

Nakon toga napravljen je Windows EXE installer verzije `1.0.0`.

Installer omogućuje instalaciju aplikacije te stvaranje Start Menu i desktop prečaca.

Finalni installer:

`Stardew Community Center Tracker-1.0.0.exe`

Testirana je instalirana verzija aplikacije.

Potvrđeno je:

- aplikacija se uspješno instalira
- aplikacija se pokreće bez VS Codea i Mavena
- Java i JavaFX runtime dolaze zajedno s aplikacijom
- postojeći saveovi iz `%LOCALAPPDATA%` pravilno se učitavaju
- promjene se mogu spremiti i ponovno učitati nakon ponovnog pokretanja aplikacije

Time je napravljena prva samostalna Windows verzija aplikacije spremna za distribuciju.