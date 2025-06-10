# 📚 Proyek KatalogBook

![Versi](https://img.shields.io/badge/version-1.0.0-blue)
![Lisensi](https://img.shields.io/badge/license-MIT-green)

> Platform katalog dan rekomendasi buku modern yang membantu pengguna menemukan, melacak, dan mengeksplorasi buku dari perpustakaan luas berisi lebih dari 270.000 judul.

## 📋 Daftar Isi

- [Gambaran Umum](#gambaran-umum)
- [ Fitur](#fitur)
- [ Memulai](#memulai)
- [ Penggunaan](#penggunaan)
- [ Dokumentasi API](#dokumentasi-api)
- [ Kontribusi](#kontribusi)
- [ Kontak](#kontak)

## Gambaran Umum

KatalogBook adalah platform perpustakaan digital komprehensif yang memungkinkan pengguna mencari, menemukan, dan mengatur buku. Dengan memanfaatkan API Rekomendasi Buku yang ekstensif, pengguna dapat mengakses informasi lebih dari 270.000 buku, membuat daftar bacaan personal, dan menerima rekomendasi buku yang disesuaikan berdasarkan preferensi dan riwayat bacaan mereka.

## ✨ Fitur

- **Database Buku yang Luas** - Akses ke lebih dari 270.000 buku dengan informasi detail
- **Pencarian Lanjutan** - Temukan buku berdasarkan judul, penulis, genre, tahun publikasi, dan lainnya
- **Rak Buku Pribadi** - Buat koleksi kustom untuk "Sudah Dibaca", "Sedang Dibaca", dan "Ingin Dibaca"
- **Rekomendasi Personalisasi** - Dapatkan saran buku berdasarkan riwayat bacaan dan preferensi Anda
- **Detail Buku** - Lihat informasi komprehensif termasuk sinopsis, biografi penulis, rating, dan ulasan
- **Pelacak Progres Membaca** - Pantau progres membaca Anda dan tetapkan target membaca
- **Akses Offline** - Simpan buku untuk dilihat offline
- **Berbagi Sosial** - Bagikan buku favorit dan daftar bacaan Anda dengan teman

## 🚀 Memulai

### Prasyarat

- Node.js (v14 atau lebih tinggi)
- npm atau yarn
- Kunci API dari [RapidAPI](https://rapidapi.com/roftcomp-laGmBwlWLm/api/books-recommendation-270k-books)

### Instalasi

1. Kloning repositori:
   ```bash
   git clone https://github.com/frisiliakiki/KatalogBook-Project.git
   ```

2. Pindah ke direktori proyek:
   ```bash
   cd KatalogBook-Project
   ```

3. Pasang dependensi:
   ```bash
   npm install
   ```

4. Buat file `.env` di direktori root dan tambahkan kunci RapidAPI Anda:
   ```
   RAPID_API_KEY=kunci_api_anda_di_sini
   ```

5. Mulai server pengembangan:
   ```bash
   npm start
   ```

## 📱 Penggunaan

### Mencari Buku
```javascript
// Contoh mencari buku berdasarkan judul
const searchBooks = async (title) => {
  const results = await api.searchByTitle(title);
  return results;
};
```

### Mendapatkan Rekomendasi Buku
```javascript
// Contoh mendapatkan rekomendasi buku berdasarkan genre
const getRecommendations = async (genre) => {
  const recommendations = await api.getRecommendationsByGenre(genre);
  return recommendations;
};
```

### Membuat Rak Buku Kustom
```javascript
// Contoh menambahkan buku ke rak buku kustom
const addToBookshelf = (bookId, shelfName) => {
  return api.addBookToShelf(userId, bookId, shelfName);
};
```

## 🔌 Dokumentasi API

KatalogBook menggunakan [API Rekomendasi Buku](https://rapidapi.com/roftcomp-laGmBwlWLm/api/books-recommendation-270k-books) dari RapidAPI, yang menyediakan akses ke database lebih dari 270.000 buku.

### URL Dasar
```
https://books-recommendation-270k-books.p.rapidapi.com
```

### Autentikasi
Semua permintaan API memerlukan header berikut:
```
'X-RapidAPI-Key': 'KUNCI_API_ANDA'
'X-RapidAPI-Host': 'books-recommendation-270k-books.p.rapidapi.com'
```

### Endpoint Utama

#### Mendapatkan Daftar Buku
```
GET /list
```

| Parameter | Tipe   | Deskripsi                                  |
|-----------|--------|-------------------------------------------|
| page      | number | Nomor halaman untuk paginasi (default: 1) |
| limit     | number | Jumlah buku per halaman (default: 20)     |

**Contoh Respons:**
```json
{
  "status": "success",
  "data": [
    {
      "id": "123456",
      "title": "The Great Gatsby",
      "author": "F. Scott Fitzgerald",
      "genre": "Classic",
      "year": 1925,
      "rating": 4.5,
      "cover_image": "https://example.com/cover.jpg"
    },
    // Buku lainnya...
  ],
  "total": 270000,
  "page": 1,
  "limit": 20
}
```

#### Mencari Buku
```
GET /search
```

| Parameter | Tipe   | Deskripsi                                  |
|-----------|--------|-------------------------------------------|
| query     | string | Istilah pencarian                         |
| field     | string | Bidang yang dicari (judul, penulis, genre) |
| page      | number | Nomor halaman (default: 1)                |
| limit     | number | Hasil per halaman (default: 20)           |

#### Mendapatkan Detail Buku
```
GET /book/{id}
```

| Parameter | Tipe   | Deskripsi         |
|-----------|--------|-------------------|
| id        | string | ID unik buku      |

#### Mendapatkan Rekomendasi
```
GET /recommendations
```

| Parameter | Tipe   | Deskripsi                                    |
|-----------|--------|--------------------------------------------|
| book_id   | string | Dapatkan rekomendasi mirip dengan buku ini |
| genre     | string | Dapatkan rekomendasi berdasarkan genre     |
| author    | string | Dapatkan rekomendasi dari penulis yang sama |
| limit     | number | Jumlah rekomendasi (default: 10)           |


##  Kontribusi

Kontribusi sangat disambut! Jangan ragu untuk mengirimkan Pull Request.

1. Fork proyek ini
2. Buat branch fitur Anda (`git checkout -b fitur/FiturLuarBiasa`)
3. Commit perubahan Anda (`git commit -m 'Menambahkan FiturLuarBiasa'`)
4. Push ke branch (`git push origin fitur/FiturLuarBiasa`)
5. Buka Pull Request

## 📞 Kontak

Frisilia Kiki - H071231003 | Universitas Hasanuddin

Tautan Proyek: [https://github.com/frisiliakiki/KatalogBook-Project](https://github.com/frisiliakiki/KatalogBook-Project)

---

Dibuat dengan ❤️ oleh [frisiliakiki](https://github.com/frisiliakiki)
