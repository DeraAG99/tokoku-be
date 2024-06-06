# Spring rest API Tokoku :coffee:

## 📝 Prerequisites

- Java 17.
- Apache Maven 3.9.6.
- PostgreSQL 16.2.

## Tech Stack :computer:

- Spring Web.
- Spring Data JPA.
- Spring security.
- JWT.

## Fitur Utama :fire:

#### untuk user

- Register account.
- Menambah produk ke dalam keranjang.
- Membuat pesanan.
- Melihat riwayat pesanan.

#### untuk admin

- Manajemen produk & kategori produk.
- Manajemen pengguna.
- Manajemen pesanan.

## Modules

- Auth.
- Kategori.
- Produk.
- Keranjang.
- Pengguna.
- Pesanan.

## 📙 Panduan Penggunaan 📙

- Kamu dapat melakukan clone repository dengan cara :
  ```
    git clone https://github.com/DeraAG99/tokoku-be.git
  ```
- Sebelum menjalankan Aplikasi mohon untuk menyesuaikan konfigurasi database [application.properties](Tokoku-be\src\main\resources\application.properties) file.
- Update username dan password sesuai kan dengan config database yang berada di lokal komputer kamu.

  ```
    spring.application.name=tokoku-be
    spring.datasource.url=jdbc:postgresql://localhost:5432/tokoku
    spring.datasource.username=postgres
    spring.datasource.password=admin
  ```

- Setelah menyesuaikan configurasi database kamu dapat melakukan build project melalui terminal :
  ```
    mvn-clean package
  ```
- Setelah berhasil build kamu dapat menjalankan aplikasi dengan perintah :
  ```
    java -jar target/tokoku-be-0.0.1-SNAPSHOT.jar
  ```

## API Root Endpoint

- Default port server : 8080

`https://localhost:8080/`

## API Module Endpoints

### Auth Module

- `POST /auth/signup` : Register pengguna baru.
- `POST /auth/signin` : Login pengguna menggunakan username dan password.
- `POST /auth/refreshToken` : Refresh Token.

### Kategori Module

- `POST /api/kategoris` : Menambah kategori.
- `GET /api/kategoris` : Menampilkan seluruh kategori.
- `GET /api/kategoris/{id}` : Mencari kategori berdasarkan Id.
- `PUT /api/kategoris` : Update data kategori berdasarkan Id.
- `DEL /api/kategoris/{id}` : Delete data kategori berdasarkan Id.

### Produk Module

- `POST /api/produks` : Menambah produk.
- `GET /api/produks` : Menampilkan seluruh produk.
- `GET /api/produks/{id}` : Mencari produk berdasarkan Id.
- `PUT /api/produks` : Update data produk berdasarkan Id.
- `DEL /api/produks/{id}` : Delete data produk berdasarkan Id.

### Keranjang Module

- `POST /api/keranjangs` : Menambah produk kedalam keranjang.
- `GET /api/keranjangs` : Menampilkan isi keranjang pengguna.
- `PATCH /api/keranjangs/{id}` : Update data keranjang berdasarkan Id.
- `DEL /api/keranjangs/{id}` : Delete data keranjang berdasarkan Id.

### Pengguna Module

- `POST /api/penggunas` : Menambah pengguna admin.
- `GET /api/penggunas` : Menampilkan list pengguna admin.
- `GET /api/penggunas/{id}` : Mencari pengguna berdasarkan Id.
- `PATCH /api/penggunas/{id}` : Update data pengguna berdasarkan Id.
- `DEL /api/penggunas/{id}` : Delete data pengguna berdasarkan Id.

### Pesanan user Modole

- `POST /api/pesanans` : User membuat pesanan.
- `PATCH /api/pesanans/{pesananId}/cancel` : User cancel pesanan.
- `PATCH /api/pesanans/{pesananId}/terima` : User terima pesanan.
- `GET /api/pesanans` : Menampilkan daftar pesanan.

### Pesanan admin Modole

- `PATCH /api/pesanans/{pesananId}/konfirmasi` : Admin konfirmasi pesanan.
- `PATCH /api/pesanans/{pesananId}/packing` : Admin packing pesanan.
- `GET /api/pesanans/admin` : Menampilkan daftar pesanan.

### Sample API Response untuk registrasi user

`POST  localhost:8080/auth/signup`

- Request Body

```
    {
        "username": "dera.abdul",
        "password": "dera",
        "nama": "dera",
        "email": "dera@gmail.com"
    }
```

- Response

```
    {
        "code": 201,
        "status": "CREATED",
        "data": {
            "id": "dera.abdul.gani",
            "nama": "dera",
            "alamat": null,
            "email": "deraag@gmail.com",
            "hp": null,
            "roles": "user",
            "isAktif": true
        },
        "timestamp": "2024-06-06 10:25:24"
    }
```

- Error Response

```
    "code": 400,
    "status": "BAD REQUEST",
    "errors": {
        "id": "Username dera.abdul.gani sudah terdaftar",
        "email": "Email deraag@gmail.com sudah terdaftar"
    },
    "timestamp": "2024-06-06 10:26:39"
```

## Need improvement :question:

1. Penambahan file image upload untuk produk
2. Penambahan SMTP Email service untuk notifikasi
