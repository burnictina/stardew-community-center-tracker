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