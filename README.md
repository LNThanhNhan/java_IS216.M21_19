
# Hệ thống hỗ trợ người bị Covid-19
## Mô tả đồ án
Xây dựng ứng dụng nơi mà bệnh nhân mắc Covid-19 có thể tạo yêu cầu (lương thực, nhu yếu phẩm, vật trang y tế) đến các trung tâm thiện nguyện để nhận được sự giúp đỡ, đồng thời bệnh nhân cũng có thể tạo yêu cầu tư vấn với các bác sĩ tham gia trên hệ thống để nhận được sự tư vấn từ các bác sĩ.
## Mục tiêu đồ án
Đây là đồ án của môn học Lập trình java. Ứng dụng phải đảm bảo được các mục tiêu:
* Giao diện thân thiện, dễ nhìn
* Dễ sử dụng đối với tất cả loại người dùng
* Chạy nhanh, dễ bảo trì

## Các thành viên tham gia project

STT | Họ và tên | MSSV | Facebook | Số điện thoại | Nhiệm vụ | Đánh giá
---|---|---|---|---|---|---|
1|Lương Nguyễn Thành Nhân| 20520667 | [Thành Nhân][1]| 0768631426| Trưởng nhóm, tester, code màn hình Bác sĩ | 20%
2| Nguyễn Hoàng Trung | 20522071 | [Nguyễn Trung][2]|0919112751|Code màn hình Nhân viên |50%
3| Trần Nhật Sinh | 20521837 | [Trần Nhật Sinh][3]|0394298534 |Code màn hình Trung tâm thiện nguyện, Người cần giúp đỡ | 30%

[1]:https://www.facebook.com/nhan.29.08.2002
[2]:https://www.facebook.com/nguyenhoangtrunghhh
[3]:https://www.facebook.com/sinh.trannhat.376

## Framework sử dụng
* Java Swing
* Ireport
## Tóm tắt chức năng 
* Người cần giúp đỡ
    * Đăng ký, đăng nhập, đổi mật khẩu
    * Yêu cầu tiếp tế (**thêm, xóa, sửa**)
    * Yêu cầu tư vấn (**thêm, xóa, sửa**)
* Nhân viên
    * Quản lý thông tin người cần giúp đỡ (**thêm người dùng hotline, thêm người dùng bình thường, xóa, sửa**)
    * Quản lý thông tin nhân viên (**thêm, xóa, sửa**)
    * Quản lý thông tin trung tâm thiện nguyện (**thêm, xóa, sửa**)
    * Quản lý thông tin bác sĩ (**thêm, xóa, sửa**)
    * Quản lý thông tin yêu cầu tiếp tế (**lọc, xác thực, hủy, thêm, xóa, sửa**)    
* Trung tâm thiện nguyện
    * Tiếp nhận yêu cầu tiếp tế
    * Hoàn thành, hủy yêu cầu tiếp tế
* Bác sĩ
    * Tiếp nhận yêu cầu tư vấn
    * Hoàn thành yêu cầu tư vấn
* Xuất report 
    * Nhân viên thực hiện việc xuất các report (**danh sách bác sĩ, danh sách nhân viên, hoạt động của các trung tâm thiện nguyện, thống kê yêu cầu tiếp tế trên tỉnh, thành phố**)

## Công nghệ sử dụng
### 1. NetBeans
Vì ngôn ngữ chính trong đồ án môn học chính là Java nên nhóm chọn sử dụng phần mềm IDE NetBean để viêt chương trình, giao diện cho hệ thống
### 2. Hệ quản trị cơ sở dữ liệu Oracle
Các file SQL script được nhóm viết và cài đặt trên lược đồ cơ sở dữ liệu Oracle
### 3. Power Designer
Phần mô hình ERD được nhóm sử dụng phần mềm Power Designer phiên bản 16.5 để vẽ
### 4. Lucidchart
Mô hình Class Diagram được thiết kế và vẽ trên nền tảng [LucicChart](https://www.lucidchart.com/pages/)
### 5. Smart Git
Phần mềm này được [anh Nhựt][1] là giảng viên Thực hành môn Lập trình java giới thiệu và hướng dẫn nên nhóm mới có thể hiểu thêm về Git và GitHub để phục vụ cho đồ án môn học.

[1]:https://www.facebook.com/nguyenminh.nhut.75436

## Tài liệu tham khảo
* [Form thông tin bác sĩ](https://docs.google.com/forms/d/e/1FAIpQLSeW2X0EzYHDp61EjPcB9SK2ZQ5n-Ujdc7PKpRAZ2j_4kypGiw/viewform)
* [Dự án Tiếp sức (Vietnam SOS Tech)](https://docs.google.com/presentation/d/16YFSi3jXSpjBSxiZBllrW93--C29ZCQWUYjZ6Tp4pas/edit#slide=id.ge99a61bc02_0_31)
* [Dự án Giuptoi.vn](https://www.youtube.com/watch?v=XntE5vAkHcI)
* [Tính năng Zalo Connect](https://www.qdnd.vn/giao-duc-khoa-hoc/tin-tuc/tinh-nang-zalo-connect-giup-nguoi-dan-de-dang-tim-kiem-su-giup-do-khan-cap-tu-cong-dong-667449)


