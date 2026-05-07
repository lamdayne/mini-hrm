package com.lamdayne.minihrm.enums;

public enum TenantStatus {
    ACTIVE, // Đang hoạt động bình thường
    LOCKED, // Bị khóa — toàn bộ user không thể đăng nhập
    PENDING, // Mới đăng ký, chờ kích hoạt (nếu có bước xác thực)
}
