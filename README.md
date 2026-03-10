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

# **Refleksi Modul 3 – Maintainability & OO Principles**

## **Soal 1:**
Explain what principles you apply to your project!

**Jawaban:**

## 1. Prinsip yang Diterapkan
Pada proyek e-shop ini, saya menerapkan beberapa prinsip SOLID, yaitu **SRP, ISP, DIP, dan OCP**.
### 1. Single Responsibility Principle (SRP)
Saya memisahkan komponen berdasarkan tanggung jawabnya masing-masing.
* Controller dipisah menjadi:
  * `HomeController`
  * `ProductController`
  * `CarController`
* Logika bisnis ditempatkan pada layer service:
  * `ProductService`
  * `CarService`
* Akses data diletakkan pada layer repository:
  * `ProductRepository`
  * `CarRepository`
Dengan struktur ini, setiap kelas memiliki satu tanggung jawab yang jelas dan tidak bercampur dengan domain lain.

### 2. Interface Segregation Principle (ISP)
Saya memisahkan interface service sesuai kebutuhan domain.
* `ProductService` khusus untuk domain product
* `CarService` khusus untuk domain car
Dengan cara ini, masing-masing controller hanya bergantung pada interface yang memang dibutuhkan dan tidak dipaksa menggunakan method yang tidak relevan.

### 3. Dependency Inversion Principle (DIP)
Controller tidak bergantung langsung pada implementasi konkret, melainkan pada interface (`ProductService` dan `CarService`).
Hal ini membuat dependency lebih fleksibel dan mudah diuji. Pada proses testing, saya menggunakan `@MockBean` untuk menyediakan dependency service saat menjalankan `@WebMvcTest`, sehingga pengujian dapat fokus pada controller tanpa harus memuat implementasi service atau repository yang sebenarnya.

### 4. Open/Closed Principle (OCP)
Saya menambahkan method `update(Car newCar)` pada model `Car` untuk mengenkapsulasi proses pembaruan data.
Dengan pendekatan ini, jika aturan update berubah, saya cukup memodifikasi method tersebut tanpa harus mengubah logika di berbagai tempat seperti controller atau repository.

## **Soal 2:**
Explain the advantages of applying SOLID principles to your project with examples.

**Jawaban:**

Penerapan SOLID memberikan beberapa keuntungan nyata dalam proyek ini.
### 1. Kode Lebih Mudah Dipelihara
Dengan SRP, pemisahan `CarController` dan `ProductController` membuat perubahan pada fitur car tidak memengaruhi fitur product. Struktur proyek juga menjadi lebih rapi dan mudah dipahami.
### 2. Dependensi Lebih Terkontrol
Dengan ISP, setiap controller hanya menggunakan service yang sesuai dengan domainnya.
Contohnya, `CarController` tidak perlu mengetahui atau mengakses method yang berkaitan dengan product.
### 3. Mudah Diuji dan Fleksibel
Dengan DIP, controller hanya bergantung pada interface.
Jika suatu saat implementasi service berubah, controller tidak perlu ikut diubah.
Dalam testing, saya bisa menggunakan `@MockBean` untuk menggantikan dependency service, sehingga test tidak bergantung pada database atau konfigurasi bean yang sebenarnya.
### 4. Mudah Dikembangkan
Dengan OCP, proses update pada entity `Car` terpusat di satu method (`update()`), sehingga tidak terjadi duplikasi logika.
Jika aturan update berubah (misalnya ada validasi tambahan), perubahan cukup dilakukan di satu tempat.

## **Soal 3:**
Explain the disadvantages of not applying SOLID principles to your project with examples

**Jawaban:**
Jika prinsip SOLID tidak diterapkan, beberapa masalah berikut bisa terjadi:
### 1. Kode Menjadi Terlalu Kompleks
Tanpa SRP, satu controller bisa menangani banyak domain sekaligus. Hal ini membuat kode sulit dibaca, sulit dirawat, dan berisiko menimbulkan bug saat ada perubahan kecil.
### 2. Interface Menjadi “Gemuk”
Tanpa ISP, interface service bisa berisi terlalu banyak method yang tidak relevan untuk semua implementasi. Akibatnya, kelas harus mengimplementasikan method yang sebenarnya tidak digunakan.
### 3. Sulit Diuji dan Tidak Fleksibel
Tanpa DIP, controller akan langsung bergantung pada implementasi konkret.
Hal ini membuat:
* Testing menjadi lebih sulit
* Sulit mengganti implementasi
* Test lebih rentan gagal karena ketergantungan terhadap konfigurasi nyata
### 4. Duplikasi Logika
Tanpa OCP, logika seperti proses update bisa tersebar di banyak tempat (controller, repository, dll).
Akibatnya:
* Terjadi duplikasi kode
* Sulit menjaga konsistensi
* Perubahan aturan membutuhkan modifikasi di banyak file

## Kesimpulan

Penerapan SOLID pada proyek ini membantu menjaga struktur kode tetap bersih, modular, dan mudah dikembangkan.
Selain itu, proses testing menjadi lebih sederhana dan risiko bug akibat perubahan fitur dapat diminimalkan.


# **Refleksi Modul 4 – TDD dan F.I.R.S.T. Principle**

## **Soal 1:**
Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.

**Jawaban:**
Dalam pengerjaan exercise ini, saya merasa alur Test-Driven Development (TDD) cukup membantu, terutama untuk memecah implementasi menjadi langkah-langkah kecil yang lebih terarah. Dengan menulis test untuk Order, OrderRepository, OrderServiceImpl, Payment, PaymentRepository, dan PaymentServiceImpl, saya menjadi lebih mudah memahami perilaku yang diharapkan dari setiap class sebelum atau saat implementasi dilakukan. TDD juga membantu saya memverifikasi apakah perubahan pada model, repository, atau service masih konsisten dengan kebutuhan fitur, misalnya pada validasi status order, penyimpanan payment, dan perubahan status payment terhadap order.
Namun, jika saya refleksikan lebih jauh, alur TDD yang saya lakukan masih belum sepenuhnya ideal. Walaupun saya sudah memiliki unit test untuk banyak class, dari hasil akhir project terlihat bahwa beberapa test masih berperan lebih sebagai verifikasi setelah implementasi, bukan selalu murni sebagai pemandu desain sejak awal. Selain itu, masih ada beberapa bagian yang pengujiannya belum terlalu kaya pada edge case, sehingga TDD yang saya lakukan belum sepenuhnya memaksimalkan eksplorasi perilaku sistem sebelum coding. Dengan kata lain, TDD yang saya lakukan sudah berguna, tetapi masih bisa ditingkatkan agar lebih disiplin dan lebih dekat dengan siklus RED → GREEN → REFACTOR yang ideal.
Hal yang saya rasakan paling membantu dari TDD adalah:
- saya lebih cepat mengetahui jika ada perilaku class yang tidak sesuai;
- saya lebih percaya diri saat melakukan perubahan karena sudah ada safety net berupa test;
- saya terdorong untuk memikirkan contract dari method, misalnya apa yang harus terjadi ketika status payment berubah atau ketika order tidak ditemukan.
Meski begitu, ada beberapa hal yang perlu saya perbaiki di masa depan. Pertama, saya perlu lebih disiplin menulis test yang benar-benar gagal terlebih dahulu sebelum menulis implementasi. Kedua, saya perlu menambahkan lebih banyak test untuk kondisi batas dan kondisi error, bukan hanya skenario utama. Ketiga, saya perlu memastikan setiap commit benar-benar mencerminkan fase TDD, sehingga proses pengembangan tidak hanya memiliki test, tetapi juga menunjukkan alur berpikir yang sistematis.
Jika saya membuat lebih banyak test di masa mendatang, saya akan:
- menulis test failure lebih dulu untuk setiap requirement kecil;
- menambahkan test untuk edge case, invalid input, dan exception path;
- melakukan refactor setelah test hijau agar struktur test dan kode produksi tetap rapi;
- menghindari membuat test yang terlalu besar atau menguji terlalu banyak hal sekaligus;
- menggunakan test sebagai alat desain, bukan hanya alat verifikasi di akhir.

Secara keseluruhan, menurut saya TDD dalam exercise ini cukup berguna karena membantu menjaga arah implementasi dan mengurangi rasa ragu saat mengubah kode. Namun, saya masih perlu meningkatkan kedisiplinan dalam mengikuti siklus TDD secara penuh agar manfaatnya lebih maksimal.

## **Soal 2:**
You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.

**Jawaban:**
Menurut saya, unit test yang saya buat sudah cukup mendekati prinsip F.I.R.S.T., tetapi belum sepenuhnya sempurna di semua aspek.

*Fast*
Sebagian besar test yang saya buat tergolong cepat karena berfokus pada unit kecil seperti model, repository in-memory, dan service. Test seperti OrderTest, PaymentTest, OrderRepositoryTest, PaymentRepositoryTest, dan PaymentServiceImplTest tidak membutuhkan database sungguhan atau koneksi jaringan, sehingga secara umum dapat dijalankan dengan cepat. Dalam hal ini, prinsip Fast sudah cukup terpenuhi.

*Independent*
Sebagian besar test juga cukup independen karena masing-masing test membuat data sendiri atau menggunakan @BeforeEach untuk reset kondisi awal. Ini membantu agar test tidak terlalu bergantung pada urutan eksekusi test lain. Namun, saya masih merasa beberapa test bisa dibuat lebih independen lagi dengan setup yang lebih ringkas dan lebih eksplisit, sehingga setiap test benar-benar berdiri sendiri dan tidak terlalu bergantung pada shared preparation yang besar.

*Repeatable*
Test yang saya buat pada umumnya repeatable karena menggunakan data yang deterministik dan tidak bergantung pada layanan eksternal. Selama environment-nya sama, hasil test seharusnya konsisten. Jadi, prinsip Repeatable sudah cukup baik diterapkan.

*Self-validating*
Saya juga merasa prinsip Self-validating sudah cukup terpenuhi karena test-test saya menggunakan assertion yang jelas seperti assertEquals, assertNull, assertNotNull, assertThrows, dan verifikasi Mockito. Dengan begitu, hasil test langsung menunjukkan pass atau fail tanpa perlu interpretasi manual. Ini merupakan salah satu bagian yang menurut saya sudah cukup baik.

*Timely*
Untuk aspek Timely, saya merasa ini yang paling perlu saya refleksikan. Secara hasil, saya memang sudah menulis test untuk banyak class. Namun, belum semua test dapat dipastikan benar-benar ditulis sebelum implementasi secara disiplin pada setiap langkah kecil. Artinya, prinsip timely sudah saya upayakan, tetapi pelaksanaannya masih belum konsisten sepenuhnya seperti semangat TDD yang ideal.
Selain itu, saya juga menyadari masih ada beberapa hal yang bisa diperbaiki agar test saya lebih sesuai dengan F.I.R.S.T.:
- beberapa setup test masih cukup panjang dan berulang;
- beberapa class masih lebih banyak diuji dari jalur normal dibandingkan jalur ekstrem atau tidak valid;
- struktur data uji bisa dibuat lebih ringkas agar fokus test lebih jelas;
- nama test sudah cukup informatif, tetapi masih bisa dibuat lebih spesifik terhadap satu perilaku.

Jika ke depannya saya membuat lebih banyak unit test, saya akan:
- menjaga agar setiap test hanya memverifikasi satu perilaku utama;
- mengurangi duplikasi setup dengan helper method yang tetap mudah dibaca;
- memperbanyak pengujian terhadap edge case dan invalid case;
- memastikan test benar-benar ditulis sebelum implementasi ketika menerapkan TDD;
- menjaga agar test tetap sederhana, cepat, dan tidak bergantung pada resource eksternal.

Secara keseluruhan, saya menilai test yang saya buat sudah cukup mengikuti prinsip F.I.R.S.T., terutama pada aspek Fast, Repeatable, dan Self-validating, tetapi masih perlu ditingkatkan pada aspek Timely dan pada kedisiplinan menyusun test yang lebih kecil, fokus, dan benar-benar lahir sebelum implementasi ditulis.
