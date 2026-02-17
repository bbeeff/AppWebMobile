# 📅 RoomBooker - Sistema di Prenotazione Aule

---

## Descrizione del Progetto

**RoomBooker** è un'applicazione web full-stack per la gestione e prenotazione di aule universitarie. Il sistema consente agli utenti autenticati di visualizzare le aule disponibili, effettuare prenotazioni e gestirle nel tempo, mentre gli amministratori dispongono di un pannello di controllo completo per la gestione di aule, utenti e prenotazioni.

### Funzionalità principali

**Utente (USER)**
- Autenticazione sicura tramite JWT
- Dashboard personale con calendario delle prenotazioni
- Visualizzazione aule disponibili con filtri
- Creazione, modifica e cancellazione delle proprie prenotazioni
- Export prenotazioni in formato PDF e CSV

**Amministratore (ADMIN)**
- Tutte le funzionalità utente
- Pannello di amministrazione completo
- Gestione aule (creazione, modifica, eliminazione)
- Gestione utenti (creazione, modifica, eliminazione)
- Visualizzazione di tutte le prenotazioni del sistema
- Statistiche generali del sistema
- Export dati in PDF e CSV

---
## Tecnologie Utilizzate

### Frontend
Angular 17, TypeScript, Angular Material, Bootstrap 5, Font Awesome 6, RxJS
### Backend
Java 21, Spring Boot 3.2.2, Spring Security 6.2.1, Spring Data JPA, Hibernate 6.4.1, H2 Database, JJWT 0.11.5

---

## Struttura del Progetto

```
App_Web_Mobile/
├── roombooker-backend/                  # Backend Spring Boot
│   └── src/main/java/it/unicam/roombooker/
│       ├── config/
│       │   ├── DataInitializer.java     # Inizializzazione dati demo
│       │   └── SecurityConfig.java      # Configurazione sicurezza e CORS
│       ├── controller/
│       │   ├── AuthController.java      # Endpoint autenticazione
│       │   ├── ReservationController.java # Endpoint prenotazioni
│       │   ├── RoomController.java      # Endpoint aule
│       │   └── UserController.java      # Endpoint utenti
│       ├── dto/
│       │   ├── AuthRequest.java         # DTO richiesta login
│       │   ├── AuthResponse.java        # DTO risposta login (con token)
│       │   ├── CreateReservationRequest.java
│       │   ├── DtoMapper.java           # Mapper entità → DTO
│       │   ├── ReservationDTO.java
│       │   ├── RoomDTO.java
│       │   └── UserDTO.java
│       ├── model/
│       │   ├── Reservation.java         # Entità prenotazione
│       │   ├── ReservationStatus.java   # Enum: CONFIRMED, CANCELLED
│       │   ├── Role.java                # Enum: ADMIN, USER
│       │   ├── Room.java                # Entità aula
│       │   └── User.java                # Entità utente
│       ├── repository/
│       │   ├── ReservationRepository.java
│       │   ├── RoomRepository.java
│       │   └── UserRepository.java
│       ├── security/
│       │   ├── CustomUserDetailsService.java # UserDetailsService per Spring Security
│       │   ├── JwtAuthFilter.java       # Filtro JWT per ogni richiesta
│       │   └── JwtService.java          # Generazione e validazione token JWT
│       └── service/
│           ├── ReservationService.java  # Business logic prenotazioni
│           └── RoomService.java         # Business logic aule
│
└── roombooker-frontend/                 # Frontend Angular
    └── src/app/
        ├── core/
        │   ├── models/                  # Interfacce TypeScript
        │   └── services/                # Servizi HTTP e utility
        │       ├── auth.service.ts      # Autenticazione e gestione token
        │       ├── reservation.service.ts
        │       ├── room.service.ts
        │       └── user.service.ts
        ├── features/
        │   ├── auth/                    # Pagina di login
        │   ├── dashboard/               # Dashboard con calendario
        │   ├── rooms/                   # Lista e prenotazione aule
        │   ├── reservations/            # Le mie prenotazioni
        │   └── admin/                   # Pannello amministratore
        └── shared/
            └── components/
                ├── navbar/              # Barra di navigazione
                └── loading/             # Componente di caricamento
```

---

## Architettura della Sicurezza

Il sistema utilizza autenticazione **stateless** basata su token **JWT (JSON Web Token)**:

1. Il client invia email e password all'endpoint `/api/auth/login`
2. Il backend verifica le credenziali e restituisce un token JWT firmato
3. Il client salva il token nel `localStorage` e lo invia in ogni richiesta nell'header `Authorization: Bearer <token>`
4. Il filtro `JwtAuthFilter` intercetta ogni richiesta, valida il token e imposta il contesto di sicurezza
5. Il token scade dopo **120 minuti** (configurabile)

---

## Istruzioni di Installazione e Avvio

### Prerequisiti

- **Java 21** o superiore
- **Maven 3.x**
- **Node.js 18+** e **npm**
- **IntelliJ IDEA** (consigliato per il backend)
- **Angular CLI** (`npm install -g @angular/cli`)

---

### 1. Avvio del Backend

```bash
# Navigare nella cartella del backend
cd roombooker-backend

# Compilare il progetto
mvn clean install

# Avviare l'applicazione
mvn spring-boot:run
```

Il backend sarà disponibile su: **`http://localhost:8080`**

> In alternativa, aprire il progetto con IntelliJ IDEA e avviare la classe `RoombookerBackendApplication`

#### Configurazione (application.properties)

```properties
# Database H2
spring.datasource.url=jdbc:h2:file:./data/roombooker-db
spring.h2.console.enabled=true
spring.h2.console.path=/h2

# JWT
app.jwt.secret=your-secret-key-must-be-at-least-256-bits-long-for-hs256-algorithm
app.jwt.expirationMinutes=120
```

#### Console H2 (database)
Accessibile su: `http://localhost:8080/h2`  
JDBC URL: `jdbc:h2:file:./data/roombooker-db`  
Username: `sa` | Password: *(vuota)*

---

### 2. Avvio del Frontend

```bash
# Navigare nella cartella del frontend
cd roombooker-frontend

# Installare le dipendenze
npm install

# Avviare il server di sviluppo
ng serve
```

Il frontend sarà disponibile su: **`http://localhost:4200`**

---

### 3. Credenziali Demo

Al primo avvio, il sistema crea automaticamente due utenti demo:

| Ruolo | Email | Password |
|-------|-------|---------|
| **ADMIN** | `admin@demo.it` | `Admin123!` |
| **USER** | `user@demo.it` | `User123!` |

---

## Funzionamento del Database

Il progetto utilizza **H2 Database** in modalità file persistente. I dati vengono mantenuti tra i riavvii dell'applicazione nel file `./data/roombooker-db`.

Il `DataInitializer` popola automaticamente il database con:
- 2 utenti demo (ADMIN e USER)
- 2 aule di esempio (Aula 1 - Blocco A, Aula 2 - Blocco B)

---

## Autore

**Nicolo Gregori 118302**  
Corso di Laurea in Informatica  
Università degli Studi di Camerino  
Anno Accademico 2024/2025
