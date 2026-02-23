# *Anita Khoirun Nisa - 2306152273*
# **Refleksi 1 – Clean Code & Secure Coding Practices (Modul 1)**

**Soal:**
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you find any mistake in your source code, please explain how to improve your code.

**Jawaban:**

Pada soal ini, saya diminta mengembangkan fitur *Edit Product* dan *Delete Product*, dan saya mencoba menerapkan beberapa prinsip clean code yang telah dipelajari di modul ini. Salah satu prinsip yang saya terapkan adalah *consistency*. Saya memastikan struktur controller untuk create, edit, dan delete memiliki pola yang sama, yaitu menggunakan method GET untuk menampilkan halaman dan POST untuk memproses data, lalu melakukan redirect ke halaman list. Dengan struktur yang konsisten, kode menjadi lebih mudah dibaca dan dipahami oleh developer lain. Dengan ini berarti selain konsisten saya juga memastikan codenya reusable.

Saya juga menerapkan prinsip *single responsibility*, yakni suatu modul, fungsi, kelas hanya boleh menangani satu tanggung jawab. Contohnya: Repository hanya bertanggung jawab terhadap manipulasi data, service menangani business logic, dan controller hanya mengatur alur request–response. Dan untuk menghindari duplikasi kode dengan memanfaatkan method `findById()` di repository untuk fitur edit dan delete, sehingga logic pencarian data tidak ditulis berulang.

Dari sisi clean code, saya berusaha menggunakan penamaan method yang jelas seperti `create`, `edit`, `findAll`, `findById`, dan `delete` agar mudah dipahami tanpa perlu membaca detail implementasi. Saya juga memperbaiki beberapa typo dan memastikan struktur HTML konsisten dengan template yang sudah ada agar tampilan lebih rapi dan mudah di-maintenance.

Untuk secure coding practices, saya memastikan bahwa operasi yang mengubah data (edit dan delete) menggunakan HTTP POST, bukan GET, karena GET seharusnya tidak digunakan untuk operasi yang mengubah state aplikasi. Selain itu, saya menggunakan hidden field pada form edit untuk memastikan `productId` tetap terkirim ke server sehingga proses update tidak bergantung pada input user secara manual.

Walaupun demikian, masih ada beberapa hal yang bisa ditingkatkan. Saat ini penyimpanan data masih menggunakan `ArrayList` di memory, sehingga data akan hilang ketika aplikasi di-restart. Menurut saya, sebenarnya aplikasi sebaiknya menggunakan database agar lebih realistis dan aman. Selain itu, validasi input seperti memastikan quantity tidak bernilai negatif juga belum ditambahkan.

Secara keseluruhan, implementasi fitur ini membantu saya memahami bagaimana menerapkan clean architecture sederhana pada Spring Boot serta pentingnya menjaga konsistensi, keterbacaan, dan keamanan dasar dalam pengembangan aplikasi.

---

# **Refleksi 2 – Clean Code & Secure Coding Practices (Modul 1)**

**Soal 1:**
After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?

**Jawaban:**

Setelah menulis unit test, perasaan saya menjadi lebih percaya diri. Setidaknya jika di masa mendatang kode akan digunakan lagi, tidak akan ada error yang tiba-tiba muncul. Tidak ada batasan atau aturan wajib, misalnya 10 unit test untuk tiap class. Namun, harus ada satu test untuk setiap path yang unik. Kita harus melakukan uji input yang valid dan pastikan outputnya sesuai, dan uji input yang tidak valid, null, angka ekstrem, atau format salah. Lalu, kita perlu melihat bagaimana sistem menangani error tersebut. Selain itu, harus menggunakan prinsip SRP (Single Responsibility Principle) yakni jika sebuah kelas memiliki terlalu banyak unit test (misalnya ratusan), itu tandanya kelas tersebut mungkin terlalu gemuk dan perlu dipecah.

Untuk memverifikasi program dengan benar, jangan hanya mengejar kuantitas, tapi kejarlah kualitas verifikasi, misalnya pastikan satu test tidak bergantung pada test lainnya. Lalu, test harus menghasilkan output yang sama setiap kali dijalankan (deterministik). 

Jika memiliki code coverage 100%, apakah itu berarti kode Anda tidak memiliki bug atau error? Jawabannya `TIDAK`, karena memiliki coverage 100% hanya berarti setiap baris kode telah dijalankan selama pengetesan, tetapi tidak menjamin logikanya benar.

**Soal 2:**
Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables.

What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

**Jawaban:**

'Apakah mengurangi kualitas code?' Ya, benar. Secara teknis kualitas kode akan menurun. Meskipun tujuannya untuk memisahkan fungsionalitas, menyalin prosedur setup dan variabel instans yang sama persis ke kelas baru akan menciptakan Technical Debt. Kode menjadi lebih sulit dipelihara karena jika ada perubahan pada konfigurasi dasar, harus mengubahnya di banyak tempat. Seperti yang sudah disebutkan, selain menurunkan kualitas code, hal tersebut menyebabkan potensi masalah clean code di antaranya:

- Menulis ulang prosedur @BeforeEach atau inisialisasi driver/database yang sama adalah bentuk redundansi.
- Jika prosedur setup berubah (misal: perubahan URL basis atau autentikasi), harus memperbarui semua kelas test. Jika ada yang terlewat, test akan gagal bukan karena konfigurasi yang usang, bukan karena adanya bug.
- Semakin banyak kelas dengan boilerplate yang sama, semakin besar beban kognitif bagi pengembang lain untuk memahami mana bagian yang benar-benar berbeda di setiap test, sehingga perlu maintenance yang tinggi.

Mungkin, hal yang bisa dilakukan untuk mencegah/mengatasi masalah clean code adalah dengan menerapkan inheritance. Lalu, jika ada logika pengisian data yang sering dipakai, pindahkan ke kelas utility khusus. Jangan biarkan logika bisnis test bercampur dengan logika teknis penyiapan data.

# **Refleksi Modul 2 – CI/CD, Code Quality Analysis, dan Deployment**

## **Soal 1:**
List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them

**Jawaban:**

Selama exercise Modul 2, saya menjalankan code scanning menggunakan PMD (versi 7.0.0-rc4). Dari hasil `pmdTest`, saya menemukan pelanggaran rule **AvoidDuplicateLiterals** pada file test `ProductRepositoryTest.java`. PMD mendeteksi beberapa string literal yang digunakan berulang kali yang terdapat pada nama produk dan id produk sehingga dianggap mengurangi maintainability dan meningkatkan risiko inkonsistensi apabila string tersebut perlu diubah di masa depan.

Strategi perbaikan yang saya lakukan adalah:
- Mengganti string literal yang muncul berulang menjadi **konstanta** `private static final String ...`.
- Menggunakan konstanta tersebut di seluruh test case agar tidak ada duplikasi literal.
- Menjalankan ulang `./gradlew pmdTest` untuk memastikan pelanggaran hilang.
- Menyimpan perbaikan ini sebagai **commit terpisah** agar history dapat dilihat dengan jelas.

Dengan pendekatan ini, unit test menjadi lebih mudah di maintanance dan perubahan pada nilai string cukup dilakukan di satu tempat.

## **Soal 2:**
Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

**Jawaban:**

Menurut saya, workflow yang saya buat sudah sesuai dengan konsep **Continuous Integration (CI)** karena setiap ada push maupun pull request, pipeline langsung berjalan otomatis: melakukan checkout repository, menyiapkan environment Java, lalu menjalankan test suite menggunakan Gradle. Dengan mekanisme ini, saya bisa mendapat umpan balik cepat jika ada perubahan yang membuat build rusak atau test gagal, sehingga proses integrasi ke branch utama menjadi lebih aman.

Untuk **Continuous Deployment (CD)**, implementasi ini juga dapat dikatakan memenuhi prinsip CD selama proses deploy ke PaaS berjalan otomatis ketika ada perubahan yang masuk ke branch utama yakni setelah merge ke main. Dengan fitur auto-deploy dari PaaS (pull-based Koyeb), perubahan yang sudah lolos CI bisa langsung dipublikasikan tanpa langkah manual tambahan. Meski begitu, perlu tetap ada kontrol seperti membatasi deploy hanya dari branch utama dan mengelola secrets dengan benar agar deployment tetap aman dan stabil.
