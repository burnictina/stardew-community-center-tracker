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

# Service

## ItemService, BundleService, RoomService i SaveService

klase za delegaciju repositorya kao priprema prije izrade gui-ja
