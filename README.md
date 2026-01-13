# 🚀 CryptoTrack: High-Performance Reactive Android Application

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-Material3-green.svg)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-MVI--Clean-orange.svg)]()
[![License](https://img.shields.io/badge/License-MIT-purple.svg)](LICENSE)

**CryptoTrack** is a senior-level cryptocurrency tracking application built with a focus on high-end architecture, reactive UI, and robust testing. It demonstrates mastery of the modern Android stack, aiming for performance, security, and scalability.

---

## 🏗️ Architecture & Technical Stack
The project is built using **Clean Architecture** (Data, Domain, and Presentation layers) to ensure separation of concerns and ease of testing.

- **Architecture:** MVI (Model-View-Intent) for a predictable, single-source-of-truth state machine.
- **UI:** 100% Jetpack Compose with custom **Canvas** drawing for data visualization.
- **Asynchrony:** Kotlin Coroutines & Flow (including advanced operators like `zip` for parallel data fetching).
- **Dependency Injection:** Dagger-Hilt with custom test configurations.
- **Networking:** Retrofit + OkHttp with **Interceptors** for automated API security.
- **Image Loading:** Coil with custom transformation and shimmer integration.

---

## 🌟 Technical Highlights

### 1. Custom Canvas Data Visualization
Instead of using third-party libraries, the price history charts are drawn from scratch using the **Compose Canvas API**.
- **Performance:** Direct pixel drawing reduces view hierarchy overhead.
- **Logic:** Implements dynamic coordinate mapping to scale price points into screen pixels.
- **Visuals:** Features anti-aliased strokes and a vertical gradient fill.

### 2. Robust State Management (MVI)
The application utilizes a single `State` object per screen. This prevents "state-bleeding" issues common in standard MVVM where loading, error, and data states might overlap.

### 3. Advanced Networking
- **API Key Security:** Injected via an OkHttp Interceptor to keep business logic clean.
- **Parallel Execution:** Uses the `zip` operator to fetch Coin Details and Market History simultaneously, cutting loading times by 50%.

---

## 🧪 Testing Suite (The Testing Diamond)
This project features a "full-pledged" TDD approach with nearly 100% coverage of business logic.

- **Unit Testing:** Uses **MockK** and **Turbine** to test Flow emissions and UseCase logic.
- **Instrumentation Testing:** Hilt-integrated UI tests using `ComposeTestRule` to verify shimmers and list rendering.
- **End-to-End (E2E):** Uses **MockWebServer** to simulate the entire network-to-UI pipeline, ensuring JSON parsing and repository mappers work in harmony.

---

## 📂 Project Structure
```text
com.skillvault.cryptotrack
├── core                # Shared Resource wrappers and DI modules
├── data                # DTOs, Mappers, and Repository implementations
├── domain              # Clean Kotlin models, Repository interfaces, and UseCases
└── presentation        # UI State, ViewModels, and Compose screens/components

```

---

## 🛠️ Installation & Setup
1. Clone the repository.
2. Get a free API key from CoinGecko.
3. Paste your key into the `local.properties` file with `COIN_GECKO_API_KEY` key.
4. Build and Run.
