# Docker 

Từ **nền tảng → thực chiến → best practices**



## 1️⃣ Nền tảng bắt buộc 

### 🔹 Container là gì (so với VM)

* Container **không ảo hóa OS**, mà dùng chung kernel
* Nhẹ, start nhanh, nhưng **không phải máy ảo**
* App trong container **phải tự lo mọi thứ nó cần**

👉 Hiểu sai chỗ này là sẽ:

* Cài service lung tung
* Chạy nhiều process trong 1 container
* Debug rất mệt



### 🔹 Image vs Container

* **Image**: bản thiết kế (read-only)
* **Container**: instance đang chạy của image

```text
Dockerfile → Image → Container
```

👉 Container **có trạng thái**, image thì không  
👉 Xóa container **không mất image**



### 🔹 Layer & Copy-on-write

* Mỗi instruction trong Dockerfile → **1 layer**
* Layer **cache lại**, build rất nhanh nếu viết đúng

👉 Hiểu layer để:

* Build nhanh
* Image nhỏ
* Không leak secret



## 2️⃣ Dockerfile – linh hồn của Docker

### 🔹 Các lệnh phải hiểu RÕ

```dockerfile
FROM
RUN
COPY / ADD
WORKDIR
ENV
EXPOSE
CMD
ENTRYPOINT
```

⚠️ Sai nhiều nhất:

* Dùng `ADD` bừa bãi
* Không phân biệt `CMD` vs `ENTRYPOINT`
* `RUN` quá nhiều → image phình to



### 🔹 CMD vs ENTRYPOINT 

|                           | CMD          | ENTRYPOINT             |
| ---                       | ---           | ---                   |
| Mục đích                  | default args | command chính          |
| Override khi `docker run` | ✔️           | ❌ (trừ `--entrypoint`) |

👉 App Java chuẩn:

```dockerfile
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]
```



### 🔹 Multi-stage build (RẤT QUAN TRỌNG)

Dùng để:

* Build → chạy
* Không mang theo maven, node, gradle vào image final

```dockerfile
FROM maven AS build
FROM eclipse-temurin:21-jre
```

👉 Image nhỏ hơn **5–10 lần**



## 3️⃣ Runtime – chạy container cho đúng

### 🔹 CMD / ENTRYPOINT exec form

❌ Sai:

```dockerfile
CMD java -jar app.jar
```

✅ Đúng:

```dockerfile
CMD ["java", "-jar", "app.jar"]
```

👉 Tránh lỗi:

* `[java,: not found`
* Signal không tới app
* Container không stop đúng


### 🔹 PID 1 & signal

* Process đầu tiên là **PID 1**
* PID 1 **phải handle SIGTERM**

👉 Vì sao `docker stop` không tắt app?
→ app không bắt signal



### 🔹 Pause vs Stop

* `pause`: đóng băng process (cgroup)
* `stop`: gửi SIGTERM → SIGKILL

👉 Production **chỉ dùng stop**



## 4️⃣ Volume & dữ liệu (rất dễ mất data)

### 🔹 Container là ephemeral

* Xóa container = mất data bên trong
* ❌ Không lưu DB trong container filesystem



### 🔹 Volume vs Bind mount

|         | Volume   | Bind  |
| ----    |----------|-------|
| Quản lý | Docker   | Bạn   |
| Prod    | ✅        | ❌     |
| Dev     | OK       | ✅     |

👉 DB → volume  
👉 Source code dev → bind mount



## 5️⃣ Network – container nói chuyện thế nào

### 🔹 Những hiểu lầm phổ biến

❌ `localhost` trong container ≠ máy host  
❌ Container khác **không thấy nhau** nếu không cùng network



### 🔹 Docker network

* bridge (default)
* user-defined bridge (NÊN DÙNG)
* host
* overlay (swarm)

👉 Container cùng network → gọi nhau bằng **container name**



## 6️⃣ Docker Compose – level tiếp theo

Phải nắm:

* `services`
* `networks`
* `volumes`
* `depends_on`
* `env_file`

👉 Compose = **document kiến trúc hệ thống**



## 7️⃣ Best practices 

### ✅ 1 container = 1 process  

❌ Nginx + DB + App chung 1 container



### ✅ Không chạy root

```dockerfile
USER app
```



### ✅ Image nhỏ

* alpine / distroless
* multi-stage
* xóa cache



### ✅ Không hardcode config

* ENV
* secrets
* `.env`



## 8️⃣ Debug & troubleshooting

Phải quen:

```bash
docker logs
docker exec -it
docker inspect
docker stats
```

👉 Debug Docker **không giống debug local**
