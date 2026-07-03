# Groww Paper Trading App

An Android application for learning NSE (National Stock Exchange) paper trading with ₹1,00,000 virtual demo funds per user.

## Features

- 📱 Real-time stock market data integration with Groww API
- 💰 ₹1,00,000 virtual funds for paper trading
- 📊 Portfolio tracking and P&L calculation
- 🔄 Buy/Sell virtual orders
- 👁️ Watchlist management
- 📋 Trade history and analytics
- 💾 Local data persistence with Room Database

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **UI Framework**: Jetpack Compose
- **Database**: Room
- **Networking**: Retrofit + OkHttp
- **Async**: Kotlin Coroutines
- **DI**: Hilt
- **API**: Groww API

## Installation

1. Clone the repository
```bash
git clone https://github.com/naveen-janagama/groww-paper-trading.git
```

2. Open in Android Studio

3. Build and run on device/emulator

## Project Structure

```
groww-paper-trading/
├── app/
│   ├── src/main/
│   │   ├── kotlin/com/groww/papertrading/
│   │   │   ├── data/
│   │   │   │   ├── api/
│   │   │   │   ├── database/
│   │   │   │   ├── models/
│   │   │   │   └── repository/
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   └── usecase/
│   │   │   ├── presentation/
│   │   │   │   ├── ui/
│   │   │   │   ├── viewmodel/
│   │   │   │   └── state/
│   │   │   ├── di/
│   │   │   └── MainActivity.kt
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Getting Started

### Prerequisites
- Android Studio Giraffe or later
- Android SDK 24 or higher
- Groww API credentials

## Architecture

### Clean Architecture Layers

**Presentation Layer**
- UI Components (Compose)
- ViewModels
- State Management

**Domain Layer**
- Use Cases
- Entities
- Repository Interfaces

**Data Layer**
- API Clients (Groww)
- Database (Room)
- Repository Implementation

## Demo Account

- Initial Funds: ₹1,00,000
- Can buy/sell NSE stocks
- Track portfolio P&L
- Practice trading without real money
