# Docker Compose

Từ **bản chất → cách dùng → kiến thức bắt buộc phải nắm → lỗi hay gặp**.



## 1️⃣ Docker Compose là gì 

**Docker Compose = công cụ orchestration nhẹ**
→ dùng để **chạy nhiều container cùng lúc** và **kết nối chúng với nhau** bằng **1 file YAML**.

Thay vì:

```bash
docker run ...
docker run ...
docker run ...
```

Bạn chỉ cần:

```bash
docker compose up
```

👉 Rất hợp cho:

* Backend + DB + Redis
* Local dev
* Demo / staging nhỏ

🚫 **Không phải Kubernetes**

* Không auto-scale
* Không self-heal
* Không dùng cho production lớn



## 2️⃣ Cấu trúc docker-compose.yml 

```yaml
version: "3.9"   # optional với compose v2

services:
  app:
    image: myapp:latest
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: dev
    depends_on:
      - db

  db:
    image: postgres:16
    environment:
      POSTGRES_USER: app
      POSTGRES_PASSWORD: secret
      POSTGRES_DB: mydb
    volumes:
      - db-data:/var/lib/postgresql/data

volumes:
  db-data:
```



## 3️⃣ Những kiến thức **PHẢI nắm** để dùng đúng

### 🔹 1. Service ≠ Container

* **service**: định nghĩa
* **container**: instance đang chạy

```bash
docker compose up --scale app=3
```

👉 1 service → 3 containers



### 🔹 2. Network trong Compose (rất quan trọng)

Compose **tự tạo network riêng**:

```text
myproject_default
```

👉 Các service **gọi nhau bằng tên service**, KHÔNG dùng `localhost`

❌ SAI:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
```

✅ ĐÚNG:

```properties
spring.datasource.url=jdbc:postgresql://db:5432/mydb
```

> `db` = tên service



### 🔹 3. ports vs expose (hay nhầm)

```yaml
ports:
  - "8080:8080"
```

* Mở cổng ra **máy host**
* Dùng để browser / Postman gọi

```yaml
expose:
  - "8080"
```

* Chỉ mở **nội bộ trong network**
* Host **không truy cập được**

👉 **Backend + DB** → không cần `ports` cho DB



### 🔹 4. volumes (sống còn cho database)

❌ Không volume:

```yaml
db:
  image: postgres
```

→ restart là **mất sạch dữ liệu**

✅ Có volume:

```yaml
volumes:
  - db-data:/var/lib/postgresql/data
```

📌 Có 2 loại:

* **named volume** (khuyên dùng)
* **bind mount** (`./data:/data`)



### 🔹 5. depends_on ≠ đợi app sẵn sàng

```yaml
depends_on:
  - db
```

👉 Chỉ đảm bảo:

* DB container **được start trước**
* ❌ **Không đảm bảo DB đã sẵn sàng nhận kết nối**

✅ Cách đúng:

* Healthcheck
* Retry logic trong app
* Wait-for-it script



### 🔹 6. build vs image (đừng trộn bừa)

```yaml
build: .
image: myapp:latest
```

* `build`: build image từ Dockerfile
* `image`: dùng image có sẵn

📌 Best practice:

* **Local dev**: `build`
* **CI/CD**: `image`



### 🔹 7. env_file vs environment

```yaml
env_file:
  - .env
```

```yaml
environment:
  SPRING_PROFILES_ACTIVE: dev
```

Ưu tiên:

1. `environment`
2. `env_file`
3. `.env`



## 4️⃣ Các lệnh Docker Compose cần thuộc

```bash
docker compose up
docker compose up -d
docker compose down
docker compose down -v     # xoá cả volume
docker compose ps
docker compose logs
docker compose logs -f app
docker compose exec app sh
docker compose build
docker compose pull
```

📌 **Không dùng nữa**:

```bash
docker-compose   ❌ (deprecated)
```



## 5️⃣ Những sai lầm kinh điển 🚨

❌ Dùng `localhost` trong container  
❌ Không dùng volume cho DB  
❌ Nhét hết mọi thứ vào 1 container  
❌ Dùng Compose cho production lớn  
❌ Commit file `.env` lên Git  



## 6️⃣ Khi nào NÊN / KHÔNG NÊN dùng Docker Compose

### ✅ NÊN

* Local development
* Demo project
* Side project
* Học Docker

### ❌ KHÔNG NÊN

* Auto scaling
* Multi-node cluster
* High availability

→ Lúc này chuyển sang **Kubernetes**

## 7️⃣ Network trong Docker Compose


> ❌ **`depends_on` KHÔNG liên quan gì tới network**  
> ✅ **Network trong Docker Compose được tạo tự động**

---

### 1️⃣ `depends_on` vs `network`

👉 **Không cần set network thủ công trong đa số trường hợp**  
👉 **`depends_on` chỉ để điều khiển thứ tự start**, KHÔNG phải để kết nối network

---

### 2️⃣ Docker Compose tự làm gì với network?

Khi bạn chạy:

```bash
docker compose up
```

Compose **tự động tạo**:

```text
<project_name>_default
```

Ví dụ:

```text
springbootweb1st_default
```

Tất cả service:

* Nằm chung network
* Nhìn thấy nhau
* Gọi nhau bằng **tên service**


### 3️⃣ Kết nối giữa các container hoạt động thế nào?

Ví dụ:

```yaml
services:
  app:
    image: myapp
  db:
    image: postgres
```

👉 `app` gọi DB như sau:

```text
jdbc:postgresql://db:5432/mydb
```

📌 **KHÔNG BAO GIỜ** dùng:

```text
localhost
```

Vì:

* `localhost` trong container = chính nó
* Không phải container khác


### 4️⃣ Vậy `depends_on` dùng để làm gì?

```yaml
depends_on:
  - db
```

Nó chỉ đảm bảo:

1. `db` container **được start trước**
2. ❌ **Không đảm bảo DB sẵn sàng**

⚠️ Đây là chỗ nhiều người sai:

* App start
* DB chưa accept connection
* App crash


### 5️⃣ Khi nào KHÔNG cần khai báo network?

✅ Trường hợp 90% project:

```yaml
services:
  app:
    depends_on:
      - db
  db:
    image: postgres
```

→ **KHÔNG cần network**
→ Compose tự xử lý đủ rồi


### 6️⃣ Khi nào BẮT BUỘC phải set network?

#### 🔹 1. Muốn tách network (security)

```yaml
networks:
  backend:
  frontend:
```

```yaml
services:
  app:
    networks:
      - backend
      - frontend
  db:
    networks:
      - backend
```

👉 Frontend **không thấy DB**


#### 🔹 2. Nhiều compose dùng chung network

Ví dụ:

* `infra-compose.yml` (db, redis)
* `app-compose.yml` (backend)

```yaml
networks:
  shared-net:
    external: true
```


#### 🔹 3. Muốn custom subnet / IP (hiếm)

Dùng cho:

* Legacy system
* Firewall rule
* Debug đặc biệt


### 7️⃣ Tóm tắt 🧠

| Thứ          | Có cần set không?     |
| ------------ | --------------------- |
| network      | ❌ (đa số)             |
| depends_on   | ✅ (thứ tự start)      |
| service name | ✅ (dùng làm hostname) |
| localhost    | ❌ trong container     |

> **Network trong Docker Compose là auto**  
> **`depends_on` KHÔNG tạo network, KHÔNG đảm bảo service ready**



