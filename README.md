# 📋 WishList App

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

> Capture and organize your dreams in one place. ✨

---

## 📱 Screenshots

| Home Screen | Add Wish | Checklist |
|-------------|----------|-----------|
| ![home]() | ![add]() | ![check]() |

---

## ✨ Features

- 📝 **Add & Update Wishes** — Create wishes with title, description
- ☑️ **Interactive Checklist** — Add items, tick/untick anytime
- 🗑️ **Swipe to Delete** — Remove wishes with smooth gesture
- 💾 **Offline Storage** — Data saved locally using Room Database
- 🎨 **Elegant UI** — Soft pink theme with Jetpack Compose
- 📭 **Empty State** — Friendly prompt when no wishes exist

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Kotlin | Primary Language |
| Jetpack Compose | UI Framework |
| Room Database | Local Storage |
| ViewModel | State Management |
| Coroutines | Async Operations |
| Repository Pattern | Clean Architecture |

---

## 🏗️ Architecture

```
WishlistApp/
│
├── data/
│   ├── Wish.kt           → Entity
│   ├── WishItem.kt       → Checklist Item
│   ├── WishDao.kt        → Database Queries
│   ├── WishDatabase.kt   → Room Database
│   ├── WishRepository.kt → Data Repository
│   └── Converters.kt     → Type Converters
│
├── ui/
│   ├── HomeView.kt       → Main Screen
│   └── AddEditDetailView.kt → Add/Edit Screen
│
├── Graph.kt              → Dependency Container
├── WishViewModel.kt      → ViewModel
├── Navigation.kt         → Nav Controller
└── Screen.kt             → Routes
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or above
- Android SDK 34
- Kotlin 1.9+

### Installation

1. Clone the repo
```bash
git clone https://github.com/Harshal-Deshmukh/WishlistApp.git
```

2. Open in Android Studio

3. Run on device or emulator

---

## 📦 Dependencies

```gradle
// Jetpack Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material:material")

// Room Database
implementation("androidx.room:room-runtime:2.6.0")
implementation("androidx.room:room-ktx:2.6.0")

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.5")

// Gson
implementation("com.google.code.gson:gson:2.10.1")
```

---

## 👨‍💻 Author

**Harshal Deshmukh**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Harshal-Deshmukh)

---

## 📄 License

```
MIT License
Copyright (c) 2026 Harshal Deshmukh
```

---

<p align="center">Made with ❤️ using Kotlin & Jetpack Compose</p>
