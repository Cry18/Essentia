# Essentia — Fragrance Catalog

A web application inspired by [Fragrantica](https://www.fragrantica.com/) — the go-to reference site for perfume lovers. Essentia lets you browse a curated catalog of fragrances, discover the perfumers behind them, explore their olfactory profiles, and build your own personal collection.

---
## Tech Stack

**Backend** · Java 17 · Spring Boot · Spring Security · JPA / Hibernate · Apache Maven  
**Database** · MySQL  
**Testing** · JUnit · Mockito · Postman  
**DevOps** · Docker · Docker Compose  
**Frontend** · React 18 · React Router v6 · Axios · CSS

---
## What can you do with Essentia?

**As a visitor (no account needed)**
- Browse the full fragrance catalog
- Filter perfumes by name, brand, perfumer, or olfactory note
- Read detailed fragrance profiles — description, olfactory pyramid (top / heart / base notes), creators
- Explore brand pages and perfumer profiles

**As a registered user**
- Write reviews — rate a fragrance, describe your experience, and note its seasonality, sillage and longevity
- Edit or delete your own reviews
- Add fragrances to your **Favourites**
- Set your personal **Signature** fragrance
- Create custom **Shelves** (like wishlists or themed collections) and add perfumes to them

**As an admin**
- Full management of the catalog: add, edit and delete fragrances, brands, perfumers and olfactory notes
- Delete any user review
- View catalog statistics

---

## Running the project

The easiest way to run Essentia is with **Docker** — one command starts everything.

**Prerequisites:** [Docker Desktop](https://www.docker.com/products/docker-desktop/)

```bash
git clone <repo-url>
cd Essentia
docker compose up --build
```

Once running, open your browser at **[http://localhost:3000](http://localhost:3000)**.

### Pre-loaded sample data

On first startup the database is automatically populated with realistic sample data:

| | |
|---|---|
| **Brands** | Dior · Chanel · Guerlain · Creed · Thierry Mugler · Maison Francis Kurkdjian |
| **Perfumers** | François Demachy · Olivier Polge · Thierry Wasser · Erwin Creed · Alberto Morillas |
| **Fragrances** | Sauvage · J'adore · Bleu de Chanel · Chance · Shalimar · Aventus · Angel · Baccarat Rouge 540 |
| **Olfactory notes** | 17 notes — bergamot, rose, iris, amber, sandalwood, patchouli and more |
| **Test accounts** | `admin` / `Admin2024!` (admin) · `mario` / `Mario2024!` (regular user) |

> To reset everything to a clean state: `docker compose down -v && docker compose up --build`

### Useful commands

```bash
# Stop without losing data
docker compose down

# Full reset — wipes the database and re-seeds from scratch
docker compose down -v && docker compose up --build

# View live logs
docker compose logs -f
```

---

## Design

The interface is inspired by the aesthetic of luxury perfume houses — dark background, warm gold accents, generous spacing and elegant serif typography. Think quiet luxury.

---

## About the project

Essentia started as a **university internship project** built over roughly 2 months between April and May 2025. The goal was to put into practice real-world backend development: designing a proper data model, building a REST API with Java and Spring Boot, handling authentication, and containerising everything with Docker.

In June 2026, the project was revisited and extended — fixing bugs,
completing missing features, and improving overall consistency.
Part of the work was done using AI-assisted development workflows.

The frontend was designed and built entirely by Claude, based on the stylistic direction provided by the project author.

---

## Contributing

This is a small personal project, so there's no formal roadmap — but if you run into a bug or something that doesn't work as expected, contributions are very welcome.

**Ways to help:**
- **Open an issue** describing the problem (what you did, what you expected, what happened instead)
- **Submit a pull request** if you've already found and fixed the issue

No contribution is too small. If something feels broken or off, chances are it is — feel free to flag it.
