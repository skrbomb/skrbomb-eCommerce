# REST API for an E-Commerce Application

## Tech Stack
Java  
Spring Framework  
Spring Boot  
Spring Data JPA  
Spring Security  
JWT Service    
MySQL  
AWS S3  

## Modules
* Auth Module
* User Module
* Product Module
* OrderItem Module
* Category Module
* Address Module

## API Module Endpoints
### Auth Module

POST localhost:2424/auth/register  

![image](https://github.com/user-attachments/assets/c13e5b17-de08-4478-9490-f6054257cdc0)  

![image](https://github.com/user-attachments/assets/a79f4a0a-fa94-4d20-978e-5b43751c6bd5)  

POST localhost:2424/auth/login  

![image](https://github.com/user-attachments/assets/50fdb6a0-1e98-4913-a7ac-3156fc27cb09)

### User Module

GET localhost:2424/user/all ( with ADMIN Role)  

![image](https://github.com/user-attachments/assets/dd78811f-7c49-456b-b7d1-511b618ea47a)

GET localhost:2424/user/profile (當前登入用戶資訊)  

![image](https://github.com/user-attachments/assets/8112134c-2f05-42fa-a858-a0f19657e5a8)

### Category Module

POST localhost:2424/category/save  
創建商品類別 (ADMIN ONLY)  

![image](https://github.com/user-attachments/assets/fa3f4d68-11c2-432a-a94b-3a0ce09e207b)

GET localhost:2424/category/all  
取得所有商品類別 ( 無須權限 )  

![image](https://github.com/user-attachments/assets/6876d902-1907-45f1-ad3d-e5c4505cc827)

PUT localhost:2424/category/update/3  
更新類別名稱 (ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/6a199eab-8331-4d1c-9176-4315e0189567)

GET localhost:2424/category/3  
Get Category By Id ( 無須權限 )  

![image](https://github.com/user-attachments/assets/f18b4d38-6756-4780-8c43-39ec51406c3a)

DELETE localhost:2424/category/delete/6  
刪除商品類別 ( ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/1f98a7a8-9594-48b7-93aa-54ff15779292)

### Address Module

POST localhost:2424/address/save  
新增地址  

![image](https://github.com/user-attachments/assets/ba0533fc-513e-4e4f-9705-87fc610a29ce)

### Product Module

POST localhost:2424/product/create
新增商品 ( ADMIN ONLY )

![image](https://github.com/user-attachments/assets/8af271c4-0cae-4b76-aff5-10f9665ae9e4)

GET localhost:2424/product/all  
取得所有商品 ( 無須權限 )  

![image](https://github.com/user-attachments/assets/0edf6ea8-79f0-4505-a2f6-5445937f66f1)

GET localhost:2424/product/search?keyWord=iphone  
透過關鍵字搜尋商品  

key word = iphone
![image](https://github.com/user-attachments/assets/633ef60e-d8ea-4548-bb14-971fd1c13613)  

key word =samsung
![image](https://github.com/user-attachments/assets/43aee036-5466-4ea5-a70e-5fd0d8fc0230)

PUT localhost:2424/product/update/3  
更新商品資訊 ( ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/49f1c47c-611f-4456-8c82-3277e885e0d3)

GET localhost:2424/product/3  
透過商品ID 查詢  

![image](https://github.com/user-attachments/assets/a08d9566-854d-4b9a-9a82-6277a8155241)

DELETE localhost:2424/product/delete/6  
刪除商品 ( ADMIN ONLY )

![image](https://github.com/user-attachments/assets/70cf3377-3347-4b4e-80e4-827101d43b0b)

GET localhost:2424/product/category/1  
透過商品類別取得商品列表  
![image](https://github.com/user-attachments/assets/2e1bd6fd-93d9-4d3d-a07b-0d572b5a871a)

![image](https://github.com/user-attachments/assets/ce68736b-27ee-4bc3-8ba3-a140d97551d5)

### Order Module

POST localhost:2424/order/create  
下訂單 ( 需要用戶的 TOKEN )  

![image](https://github.com/user-attachments/assets/36b3c1c2-8403-426d-97f1-841aac2acdac)

GET localhost:2424/order/filter  
取得所有的 Order Item 資料 ( ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/7bc94a49-1027-4c7e-b1b1-5e2e0d432191)

GET localhost:2424/order/filter?itemId=1  
透過 Item Id 搜尋 訂單 ( ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/414075dd-ae38-4d93-93cf-89ca4742f533)


GET localhost:2424/order/filter?status=PENDING  
透過訂單狀態搜尋訂單列表  ( ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/211d2c09-4566-4454-97ef-aff9721e648f)

PUT  localhost:2424/order/update-item-status/1?status=CONFIRMED  
更新訂單狀態  ( ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/69d59a18-67bf-41b2-81ba-5e9923c868bb)

![image](https://github.com/user-attachments/assets/f0040be4-dd34-4b87-b54f-4ab2b4cda332)

GET  localhost:2424/user/profile 

![image](https://github.com/user-attachments/assets/8ca52318-dd19-4c99-ae11-8462e14e2a9d)

GET localhost:2424/order/filter  
取得在期間內成立的Order Item列表 ( ADMIN ONLY )  

![image](https://github.com/user-attachments/assets/af2183b1-20d7-4603-965d-432e0e77c6d8)

