class App {

    static ERROR_400 = "Giao dịch không thành công, vui lòng kiểm tra lại dữ liệu.";
    static ERROR_401 = "Access Denied! Invalid credentials.";
    static ERROR_403 = "Access Denied! You are not authorized to perform this function.";
    static ERROR_404 = "An error occurred. Please try again later!";
    static ERROR_500 = "Lưu dữ liệu không thành công, vui lòng liên hệ quản trị hệ thống.";

    static SweetAlert = class {
        static showSuspendConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn có chắc muốn xoá sản phẩm này không?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đồng ý!',
                cancelButtonText: 'Huỷ',
            })
        }

        static showSuspendConfirmDialogCustomer() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn có chắc muốn khoá tài khoản này không?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đồng ý!',
                cancelButtonText: 'Huỷ',
            })
        }

        static showUnlockConfirmDialogCustomer() {
            return Swal.fire({
                icon: 'success',
                text: 'Bạn có chắc muốn mở khoá tài khoản này không?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Đồng ý!',
                cancelButtonText: 'Huỷ',
            })
        }

        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 2500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }
    }

    static IziToast = class {
        static showSuccessAlert(t) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: t
            });
        }

        static showErrorAlert(t) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 3500,
                message: t
            });
        }
    }
}

class Customer {
    constructor(id, userName, password, name, phone, email, address, status) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.name = name;
        this.phone = phone;

        this.email = email;
        this.address = address;
        this.status = status
    }
}

class Status {
    constructor(id, status) {
        this.id = id;
        this.statusName = status;
    }
}

class Address {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }

}

class Product {
    constructor(id, name, image, amount, price, category, isDeleted) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.price = price;
        this.category = category;
        this.isDeleted = isDeleted;
    }
}

class Category {
    constructor(id, category) {
        this.id = id;
        this.categoryName = category;
    }
}

class Sender extends Customer {
    constructor() {
        super()
    }
}

class Recipient extends Customer {
    constructor() {
        super()
    }
}
