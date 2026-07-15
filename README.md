# POSCE – Point of Sale for City Electronics

POSCE (Point Of Sale – City Electronics) is a **desktop Point of Sale (POS) application** built with **Java Swing** on the **NetBeans Platform**. It is designed for an electronics retail store ("City Electronics") to manage products, categories, suppliers, customers, and product orders/billing through a simple graphical interface.

---

## 🖥️ Tech Stack

| Component        | Details                                    |
|-------------------|---------------------------------------------|
| Language          | Java (Swing / AWT)                          |
| IDE / Build       | NetBeans (Ant-based project — `build.xml`, `nbproject/`) |
| UI Framework      | Java Swing with NetBeans GUI Builder (`.form` files) + `AbsoluteLayout` |
| Data Storage      | In-memory Java collections (`ArrayList`) — **no external database is used** |
| Printing          | `java.awt.print` (invoice printing) |

---

## 📁 Project Structure

```
POSCE/
├── build.xml                  # Ant build script
├── nbproject/                 # NetBeans project metadata
└── src/
    ├── Login.java / Login.form            # Login screen (Admin + Customer)
    ├── Register.java                      # Customer sign-up screen
    ├── UserStore.java                     # In-memory user credential store
    ├── Admin.java / Admin.form            # Admin dashboard (navigation hub)
    ├── Products.java / Products.form      # Product CRUD management
    ├── Categories.java / Categories.form  # Category CRUD management
    ├── Supplier.java / Supplier.form      # Supplier CRUD management
    ├── Customers.java / Customers.form    # Customer CRUD management
    ├── Order.java / Order.form            # Product browsing, cart & invoice
    ├── CustomerHome.java / CustomerHome.form  # Customer landing page
    ├── img/                                # Backgrounds, login art, sidebar icons
    └── image/                              # Category/product icons (icons8 set)
```

---

## 👥 User Roles

The system supports two types of users from a single **Login** screen:

### 1. Admin
- **Hardcoded credentials:** `username: admin`, `password: abc`
- On successful login, lands on the **Admin Dashboard**, which provides navigation to:
  - **Products** – Add / Edit / Delete / List products
  - **Categories** – Add / Edit / Delete / List product categories
  - **Supplier** – Add / Edit / Delete / List suppliers
  - **Customers** – Add / Edit / Delete / List customers
  - **Order** – View/manage orders
  - **Logout**

### 2. Customer
- New customers can **Register** (Username, Email, Password, Retype Password) — stored via `UserStore` (in-memory, not persisted to disk).
- Registered customers log in through the same **Login** screen and land on **CustomerHome**, from which they can:
  - **Order Products** – browse categories and place an order
  - **Log out**

---

## 🧩 Core Modules / Features

### 🔐 Login & Registration
- Single login form validates empty fields, checks hardcoded admin credentials first, then falls back to registered customers (`UserStore.loginValid`).
- "Show password" checkbox toggle.
- Registration screen validates username uniqueness (`UserStore.usernameExists`) and password confirmation before creating an account.

### 📦 Product Management (Admin)
- Fields: Product Id, Product Name, Category, Price, Quantity, Supplier Id, Supplier Name.
- Operations: **Add**, **Delete**, **Clear form**, and view as a **List of Products** (table).

### 🗂️ Category Management (Admin)
- Fields: Category Id, Category Name.
- Operations: **Add**, **Delete**, **Clear**, **List of Categories**.

### 🚚 Supplier Management (Admin)
- Fields: Supplier Id, Supplier Name, Address, Phone.
- Operations: **Add**, **Delete**, **Clear**, **List of Suppliers**.

### 🙍 Customer Management (Admin)
- Fields: Customer Id, Customer Name, Phone No.
- Operations: **Add**, **Delete**, **Clear**, **List of Customers**.

### 🛒 Ordering & Billing (Order.java)
The largest module — an electronics catalogue browser + cart + invoice generator. Products are organized under category buttons:
- Entertainment & Communication
- Kitchen Appliances
- Home Appliances
- Office & Productivity Devices
- Security & Monitoring
- Personal Care & Accessories

Each category expands into individual item tiles (with icons), for example:
`Television, HomeTheatre, Laptop, Mobile, Radio, HeadSet, PlayStation, DishWasher, Water Purifier, Oven, GasStove, Toaster, MixerGrinder, Kettle, RiceCooker, CoffeeMaker, Refrigerator, Fan, WashingMachine, Heater, Clock, VacuumCleaner, AirConditioner, Lamp, Scanner, Camera, Printer, WifiRouter, Drill, CCTV, IronBox, SewingMachine`, etc.

Order screen actions:
- Select items and quantities to build an order.
- **Total** – calculates and displays the total price (formatted as `Rs. #,##0.00`).
- **Print Invoice** – prints the order details/invoice using `java.awt.print`.
- **Delete** – remove an item/order entry.
- **Back to Home Page** / **Log out** navigation.

---

## ▶️ How to Run

1. Open the `POSCE` folder as a project in **Apache NetBeans** (or any Ant-compatible Java IDE).
2. Ensure a JDK compatible with Swing (JDK 8+) is configured.
3. Build via Ant (`build.xml`) or NetBeans' **Clean and Build**.
4. Run the project — configured entry point (`main.class`) opens the **Login** screen.
   > Note: `nbproject/project.properties` currently lists `Supplier` as `main.class`; for the intended flow (Login → Admin/Customer), set `main.class` to `Login` before running.
5. Login as:
   - **Admin:** `admin` / `abc`
   - **Customer:** Register a new account first, then log in with those credentials.

---

## ⚠️ Known Limitations

- **No persistent database** — all data (users, products, categories, suppliers, customers, orders) is held in memory (`ArrayList`s) and is **lost when the application closes**.
- Admin credentials are **hardcoded** in `Login.java` (not secure for production use).
- No password hashing/encryption — passwords are stored and compared as plain text.
- UI is built with NetBeans' `AbsoluteLayout`, so it's not responsive to window resizing.

---

## 🚀 Possible Future Enhancements

- Integrate a real database (MySQL/SQLite) via JDBC for persistent storage.
- Add password hashing (e.g., BCrypt) for secure authentication.
- Externalize the admin credentials (config file / DB) instead of hardcoding.
- Add order history and reporting (daily sales, low-stock alerts).
- Migrate UI to a modern layout manager or a JavaFX/web front-end.

---

