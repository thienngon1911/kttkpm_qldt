Các bước chạy hệ thống microservice springboot:
0. Cài đặt docker trên máy tính (tự làm)
1. Đổi all path application local sang docker: `jdbc:mysql://localhost:3306/` sang `jdbc:mysql://mysql:3306/`
2. Đổi all path: `http://localhost:8761/eureka` sang `http://discovery-server:8761/eureka`
3. Đổi all path: `spring.redis.host=localhost` sang `spring.redis.host=redis`
4. Đổi all path: `http://localhost:8081` sang `http://service-authentication:8081`
5. Đổi tương tự cho 8082 8083 8089
6. Chạy lệnh `docker-compose up -d --build` là xong