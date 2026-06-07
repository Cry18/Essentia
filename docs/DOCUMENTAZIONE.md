# Documentazione Tecnica — Essentia

> Documento prodotto in fase di tirocinio | Versione 1.00.000 | Aprile 2025

---

## Sommario

1. [Requisiti Funzionali](#1-requisiti-funzionali)
2. [Use Case Model](#2-use-case-model)
3. [Elenco Entità](#3-elenco-entità)
4. [Definizione Tabelle Database](#4-definizione-tabelle-database)
5. [Definizione API](#5-definizione-api)

---

## 1. Requisiti Funzionali

Ogni requisito è identificato in modo univoco con la nomenclatura **RF.XXX.YY** dove:

- `RF` = Requisito Funzionale
- `XXX` = Acronimo per tipologia (es. `RDE` = Ricerca Delle Entità)
- `YY` = Progressivo numerico su 2 cifre

---

### RF_RDE — Ricerca delle entità

| ID | Nome | Descrizione | Priorità |
|---|---|---|---|
| RF_RDE_1.0 | Ricerca profumo per nome | Il sistema deve consentire la ricerca di un profumo per nome all'interno del catalogo. | Elevata |
| RF_RDE_1.1 | Ricerca profumo per naso profumiere | Il sistema deve consentire la ricerca di un profumo per nome di un naso profumiere associato, all'interno del catalogo. | Elevata |

---

### RF_VD — Visualizzazione dei dati

| ID | Nome | Descrizione | Priorità |
|---|---|---|---|
| RF_VD_1 | Visualizzazione di tutti i profumi nel catalogo | Il sistema deve restituire una lista di tutti i profumi presenti all'interno del catalogo. | Elevata |
| RF_VD_2 | Visualizzazione dei dettagli di un profumo | Il sistema deve restituire i dati dettagliati di un profumo: Nome, brand, naso profumiere, piramide olfattiva, descrizione, recensioni degli utenti. | Elevata |

---

### RF_A — Amministrazione

| ID | Nome | Descrizione | Priorità |
|---|---|---|---|
| RF_A_1.0 | Inserimento profumo nel catalogo | Il sistema deve consentire a un amministratore di inserire un nuovo profumo specificando nome, descrizione, brand, profumieri e note olfattive. | Elevata |

---

## 2. Use Case Model

---

### UC_RC_PRF_1.0 — Ricerca profumi per nome

| Campo | Valore |
|---|---|
| **Identificativo** | UC_RC_PRF_1.0 |
| **Area** | Ricerca |
| **Data** | 15/04/2025 |
| **Versione** | 1.00.000 |
| **Descrizione** | Specifica come il client può effettuare una richiesta per cercare un profumo nel catalogo. |
| **Attore principale** | Client (utente del catalogo) |
| **Entry condition** | — |
| **Exit condition (success)** | Al client viene restituita una lista di profumi che rispettano i criteri di ricerca. |
| **Exit condition (failure)** | Nessun profumo nel catalogo rispetta i criteri di ricerca inseriti. |
| **Requisiti di riferimento** | RF_RDE_1.0, RF_VD_1 |

**Flusso principale**

| Step | Attore | Azione |
|---|---|---|
| 1 | Client | Effettua una richiesta al server specificando il campo `name` del profumo. |
| 2 | Sistema | Elabora la richiesta restituendo una lista di profumi il cui nome contiene i caratteri specificati. |

**Scenario alternativo — Nessun risultato**

| Step | Attore | Azione |
|---|---|---|
| 2.1 | Server | Restituisce un messaggio che notifica l'assenza di profumi corrispondenti ai criteri. |

**Scenario alternativo — Errore server**

| Step | Attore | Azione |
|---|---|---|
| 2.1 | Server | Restituisce un messaggio che notifica il client di un problema tecnico. |

---

### UC_DT_PRF_1.0 — Dettaglio profumo

| Campo | Valore |
|---|---|
| **Identificativo** | UC_DT_PRF_1.0 |
| **Area** | Dettaglio |
| **Data** | 15/04/2025 |
| **Versione** | 1.00.000 |
| **Descrizione** | Specifica come il client può recuperare le informazioni di dettaglio di un profumo. |
| **Attore principale** | Client (utente del catalogo) |
| **Entry condition** | — |
| **Exit condition (success)** | Al client viene restituito un oggetto con le informazioni di dettaglio del profumo richiesto. |
| **Exit condition (failure)** | Il sistema notifica l'assenza del profumo identificato dalla chiave specificata. |
| **Requisiti di riferimento** | RF_VD_2 |

**Flusso principale**

| Step | Attore | Azione |
|---|---|---|
| 1 | Client | Effettua una richiesta al server specificando l'`id` del profumo. |
| 2 | Sistema | Elabora la richiesta restituendo nome, descrizione, lista profumieri, brand e piramide olfattiva. |

**Scenario alternativo — Profumo non trovato**

| Step | Attore | Azione |
|---|---|---|
| 2.1 | Server | Restituisce un messaggio che notifica l'assenza di profumi con l'id specificato. |

**Scenario alternativo — Errore server**

| Step | Attore | Azione |
|---|---|---|
| 2.1 | Server | Restituisce un messaggio che notifica il client di un problema tecnico. |

---

### UC_ADM_IP — Inserimento profumo nel catalogo

| Campo | Valore |
|---|---|
| **Identificativo** | UC_ADM_IP |
| **Area** | Amministrazione — Inserimento |
| **Data** | 15/04/2025 |
| **Versione** | 1.00.000 |
| **Descrizione** | Specifica come un amministratore può inserire una nuova entità "profumo" nel catalogo. |
| **Attore principale** | Client amministratore |
| **Entry condition** | — |
| **Exit condition (success)** | Il profumo viene correttamente salvato nel database. |
| **Exit condition (failure)** | Uno o più campi non rispettano i criteri di validazione. Il profumo non viene salvato. |
| **Requisiti di riferimento** | RF_A_1.0 |

**Flusso principale**

| Step | Attore | Azione |
|---|---|---|
| 1 | Client | Effettua una richiesta di inserimento specificando almeno i campi obbligatori: nome, descrizione, naso profumiere, brand, note olfattive. |
| 2 | Sistema | Salva il profumo nel database e restituisce una response con stato `201 Created`. |

**Scenario alternativo — Campo obbligatorio mancante**

| Step | Attore | Azione |
|---|---|---|
| 2.1 | Server | Restituisce un messaggio strutturato con l'elenco delle violazioni di validazione per campo (es. `campo: nome, tipo-errore: non compilato`). Il messaggio generale sarà: "Il profumo non può essere salvato". |

**Scenario alternativo — Errore server**

| Step | Attore | Azione |
|---|---|---|
| 2.1 | Server | Restituisce un messaggio che notifica il client dell'impossibilità di salvare il profumo per un problema tecnico. |

---

## 3. Elenco Entità

| Entità | Definizione |
|---|---|
| Perfume | Informazioni relative ai profumi |
| Brand | Informazioni relative ai brand produttori |
| Parfumer | Informazioni relative ai nasi profumieri |
| PerfumeNote | Informazioni relative alle note olfattive |
| User | Informazioni relative agli utenti |
| Shelf | Scaffali personali degli utenti |
| Review | Recensioni lasciate dagli utenti sui profumi |

### Attributi entità (livello applicativo)

#### Perfume

| Nome | Descrizione | Tipo | Chiave |
|---|---|---|---|
| id | Identificativo profumo | Int | PK |
| name | Nome | String | — |
| description | Descrizione | String | — |
| brand | Brand che ha prodotto il profumo | Brand | FK |

#### Brand

| Nome | Descrizione | Tipo | Chiave |
|---|---|---|---|
| id | Identificativo brand | Int | PK |
| name | Nome | String | — |
| description | Descrizione | String | — |
| nationality | Nazionalità | String | — |

#### Parfumer

| Nome | Descrizione | Tipo | Chiave |
|---|---|---|---|
| id | Identificativo naso profumiere | Int | PK |
| name | Nome | String | — |
| description | Descrizione | String | — |
| nationality | Nazionalità | String | — |

#### PerfumeNote

| Nome | Descrizione | Tipo | Chiave |
|---|---|---|---|
| id | Identificativo nota olfattiva | Int | PK |
| name | Nome | String | — |
| description | Descrizione | String | — |

#### Shelf

| Nome | Descrizione | Tipo | Chiave |
|---|---|---|---|
| id | Identificativo scaffale | Int | PK |
| name | Nome | String | — |
| user | Utente proprietario | User | FK |

#### User

| Nome | Descrizione | Tipo | Chiave |
|---|---|---|---|
| id | Identificativo utente | Int | PK |
| name | Nome | String | — |
| surname | Cognome | String | — |
| role | Definisce se l'utente è un amministratore | Boolean | — |
| signature | Profumo scelto come signature | Perfume | FK |

#### Review

| Nome | Descrizione | Tipo | Chiave |
|---|---|---|---|
| id | Identificativo recensione | Int | PK |
| perfume | Profumo associato | Perfume | FK |
| user | Utente che ha scritto la recensione | User | FK |
| title | Titolo | String | — |
| description | Testo della recensione | String | — |
| vote | Voto (1-5) | Int | — |
| seasonality | Stagionalità (1-4) | Int | — |
| gender | Genere (1-2) | Int | — |
| sillage | Scia (1-5) | Int | — |
| longevity | Durata (1-5) | Int | — |

---

## 4. Definizione Tabelle Database

| Tabella | Descrizione |
|---|---|
| perfume | Profumi |
| brand | Brand produttori |
| parfumer | Nasi profumieri |
| perfume_notes | Note olfattive |
| user | Utenti |
| shelf | Scaffali personali |
| perfume_parfumer | Relazione N:N tra profumo e profumiere |
| perfume_prfnotes | Relazione N:N tra profumo e nota (include il tipo: testa/cuore/fondo) |
| perfume_shelf | Relazione N:N tra profumo e scaffale |
| favorites | Relazione N:N tra profumo e utente (lista preferiti) |
| reviews | Recensioni (con tutti gli attributi valutativi) |

### Schema tabelle (livello SQL)

#### perfume

| Colonna | Tipo | Chiave |
|---|---|---|
| id | INT | PK |
| name | VARCHAR | — |
| description | VARCHAR | — |
| brand_id | INT | FK → brand |

#### brand

| Colonna | Tipo | Chiave |
|---|---|---|
| id | INT | PK |
| name | VARCHAR | — |
| description | VARCHAR | — |
| nationality | VARCHAR | — |

#### parfumer

| Colonna | Tipo | Chiave |
|---|---|---|
| id | INT | PK |
| name | VARCHAR | — |
| description | VARCHAR | — |
| nationality | VARCHAR | — |

#### perfume_notes

| Colonna | Tipo | Chiave |
|---|---|---|
| id | INT | PK |
| name | VARCHAR | — |
| description | VARCHAR | — |

#### shelf

| Colonna | Tipo | Chiave |
|---|---|---|
| id | INT | PK |
| name | VARCHAR | — |
| user_id | INT | FK → user |

#### user

| Colonna | Tipo | Chiave |
|---|---|---|
| id | INT | PK |
| name | VARCHAR | — |
| surname | VARCHAR | — |
| role | BOOLEAN | — |
| signature_id | INT | FK → perfume |

#### perfume_parfumer *(N:N)*

| Colonna | Tipo | Chiave |
|---|---|---|
| perfume_id | INT | FK → perfume |
| parfumer_id | INT | FK → parfumer |

> PK = combinazione delle due FK

#### perfume_prfnotes *(N:N con attributo)*

| Colonna | Tipo | Chiave |
|---|---|---|
| perfume_id | INT | FK → perfume |
| perfume_note_id | INT | FK → perfume_notes |
| type | ENUM (1=testa, 2=cuore, 3=fondo) | — |

> PK = combinazione delle due FK

#### perfume_shelf *(N:N)*

| Colonna | Tipo | Chiave |
|---|---|---|
| perfume_id | INT | FK → perfume |
| shelf_id | INT | FK → shelf |

> PK = combinazione delle due FK

#### favorites *(N:N)*

| Colonna | Tipo | Chiave |
|---|---|---|
| perfume_id | INT | FK → perfume |
| user_id | INT | FK → user |

> PK = combinazione delle due FK

#### reviews

| Colonna | Tipo | Chiave | Note |
|---|---|---|---|
| id | INT | PK | — |
| perfume_id | INT | FK → perfume | — |
| user_id | INT | FK → user | — |
| title | VARCHAR | — | — |
| description | VARCHAR | — | — |
| vote | ENUM (1-5) | — | — |
| seasonality | ENUM (1-4) | — | 1=Primavera, 2=Estate, 3=Autunno, 4=Inverno |
| gender | ENUM (1-2) | — | 1=Maschile, 2=Femminile |
| sillage | ENUM (1-5) | — | Intensità della scia |
| longevity | ENUM (1-5) | — | Durata sul corpo |

---

## 5. Definizione API

### Elenco endpoint

| Endpoint | Microservizio |
|---|---|
| `GET /api/catalog/perfumes` | Catalogo |
| `GET /api/catalog/perfume/{id}` | Catalogo |
| `GET /api/catalog/brands` | Catalogo |
| `GET /api/catalog/brand/{id}` | Catalogo |
| `GET /api/catalog/parfumers` | Catalogo |
| `GET /api/catalog/parfumer/{id}` | Catalogo |
| `GET /api/catalog/perfume-notes` | Catalogo |
| `PUT /api/user/signature` | Utenza |
| `POST /api/user/shelf/create` | Utenza |
| `DELETE /api/user/shelf/delete` | Utenza |
| `PUT /api/user/shelf/add` | Utenza |
| `PUT /api/user/shelf/remove` | Utenza |
| `GET /api/user/shelf/detail` | Utenza |
| `PUT /api/user/favorites/add` | Utenza |
| `PUT /api/user/favorites/remove` | Utenza |
| `POST /api/user/review/create` | Utenza |
| `DELETE /api/user/review/delete` | Utenza |
| `POST /api/admin/add/perfume` | Amministrazione |
| `POST /api/admin/add/brand` | Amministrazione |
| `POST /api/admin/add/parfumer` | Amministrazione |
| `POST /api/admin/add/perfume-note` | Amministrazione |
| `PUT /api/admin/edit/perfume/{id}` | Amministrazione |
| `PUT /api/admin/edit/brand/{id}` | Amministrazione |
| `PUT /api/admin/edit/parfumer/{id}` | Amministrazione |
| `PUT /api/admin/edit/perfume-note/{id}` | Amministrazione |
| `DELETE /api/admin/delete/perfume/{id}` | Amministrazione |
| `DELETE /api/admin/delete/brand/{id}` | Amministrazione |
| `DELETE /api/admin/delete/parfumer/{id}` | Amministrazione |
| `DELETE /api/admin/delete/note/{id}` | Amministrazione |
| `DELETE /api/admin/delete/review/{id}` | Amministrazione |
| `GET /api/admin/statistics/most-desired` | Amministrazione |
| `GET /api/admin/statistics/most-appreciated` | Amministrazione |

---

### Formato risposta di errore

In caso di errore, tutti i microservizi restituiscono un oggetto `ErrorResponse` con la seguente struttura:

| Campo | Descrizione | Tipo | Obbligatorio |
|---|---|---|---|
| timestamp | Data e ora dell'errore | LocalDateTime | Sì |
| error | Tipo di errore | String | Sì |
| message | Messaggio descrittivo | String | Sì |
| violations | Lista di violazioni (solo per errori di validazione) | List\<Violation\> | No |

**Violation**

| Campo | Descrizione | Tipo |
|---|---|---|
| fieldName | Nome del campo con errore | String |
| message | Descrizione dell'errore sul campo | String |

**Esempio — errore di validazione:**

```json
{
    "timestamp": "2025-04-22T10:15:31.2375443",
    "error": "Errore di validazione",
    "message": "Uno o più campi non rispettano i criteri di validazione",
    "violations": [
        {
            "fieldName": "notes[0].type",
            "message": "Il tipo deve essere compreso tra 1 e 3"
        },
        {
            "fieldName": "name",
            "message": "Il nome deve contenere almeno un carattere diverso da spazio"
        }
    ]
}
```

---

### `GET /api/catalog/brands`

Restituisce la lista di tutti i brand, con filtro opzionale per nome.

**Input (query param)**

| Campo | Descrizione | Tipo | Obbligatorio |
|---|---|---|---|
| name | Nome del brand (ricerca parziale) | String | No |

**Output**

```json
[
    {
        "id": 1,
        "name": "Dior",
        "description": "...",
        "nationality": "Francese"
    },
    {
        "id": 2,
        "name": "Prada",
        "description": "...",
        "nationality": "Italiana"
    }
]
```

---

### `GET /api/catalog/brand/{id}`

Restituisce le informazioni di dettaglio di un singolo brand.

**Input (path variable)**

| Campo | Descrizione | Tipo | Obbligatorio |
|---|---|---|---|
| id | Identificativo del brand | Int | Sì |

**Output**

```json
{
    "id": 1,
    "name": "Dior",
    "description": "Dior è un famoso brand di moda",
    "nationality": "Francese"
}
```
