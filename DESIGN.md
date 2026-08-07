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