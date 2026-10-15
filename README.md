# Task 5: Bank Account Simulation (Java OOP)

An enterprise-grade, console-based banking application built in Java demonstrating core **Object-Oriented Programming (OOP)** principles: **Classes & Objects**, **Inheritance**, **Encapsulation**, **Method Overriding vs Overloading**, **Runtime Polymorphism (Dynamic Method Dispatch)**, and **Transaction Auditing**.

---

## 📋 Table of Contents
1. [Project Overview](#-project-overview)
2. [Key OOP Concepts Demonstrated](#-key-oop-concepts-demonstrated)
3. [Repository Structure](#-repository-structure)
4. [Features & Capabilities](#-features--capabilities)
5. [How to Compile and Run](#-how-to-compile-and-run)
6. [Automated Real-World Simulation Output](#-automated-real-world-simulation-output)
7. [Comprehensive Interview Questions & Answers (1-10)](#-comprehensive-interview-questions--answers)
8. [GitHub Submission Guide](#-github-submission-guide)

---

## 🎯 Project Overview
- **Objective**: Simulate real-world banking operations (Account creation, deposits, withdrawals, fund transfers, interest calculation, and transaction history) using Java OOP.
- **Tools Used**: Java 17 (JDK), VS Code, Terminal / PowerShell, Git.
- **Deliverables**: Class-based Java system with persistent in-memory transaction logs and comprehensive interview prep guide.

---

## 💡 Key OOP Concepts Demonstrated

| OOP Concept | Implementation in Code | Description |
| :--- | :--- | :--- |
| **Classes & Objects** | [`Account.java`](src/Account.java), [`SavingsAccount.java`](src/SavingsAccount.java), [`CurrentAccount.java`](src/CurrentAccount.java) | `Account` serves as the blueprint; instances represent actual customer accounts in heap memory. |
| **Encapsulation** | Private fields (`balance`, `accountNumber`), public getters/setters, validation checks | Internal account state cannot be mutated illegally (e.g. negative deposits are rejected). |
| **Inheritance** | `SavingsAccount extends Account`, `CurrentAccount extends Account` | Subclasses inherit properties and methods (`balance`, `accountNumber`, `deposit`, `transfer`) promoting reuse. |
| **Method Overriding** | `@Override public boolean withdraw(double amount, String desc)` | `SavingsAccount` enforces minimum balance; `CurrentAccount` permits overdraft beyond zero. |
| **Method Overloading** | `deposit(amount)` vs `deposit(amount, desc)` | Multiple methods with identical names but differing parameter signatures in the same class. |
| **Runtime Polymorphism** | `Account acc = new SavingsAccount(...)`; `acc.withdraw(...)` | Dynamic Method Dispatch resolves the actual subclass implementation at runtime. |
| **`this` Keyword** | `this.balance = balance;` and `this(accNum, holder, 0.0);` | Resolves shadowed instance variables and performs constructor chaining. |
| **Constructor Overloading** | Multiple constructors in `Account`, `SavingsAccount`, and `CurrentAccount` | Provides flexible initialization options depending on available data. |
| **Static vs Instance** | `public static String getBankName()` | Static method belongs to the class itself, demonstrating compile-time binding (Method Hiding). |

---

## 📁 Repository Structure
```
Task 5/
├── src/
│   ├── TransactionType.java    # Enum defining transaction types (Deposit, Withdrawal, Transfer, Interest)
│   ├── Transaction.java        # Immutable data model representing an individual financial record
│   ├── Account.java            # Abstract base class (encapsulation, overloaded methods, base contracts)
│   ├── SavingsAccount.java     # Inherits Account; enforces minimum balance & interest calculation
│   ├── CurrentAccount.java     # Inherits Account; implements overdraft privileges for businesses
│   ├── Bank.java               # Service layer managing accounts, transfers, and batch interest
│   └── Main.java               # Interactive CLI terminal interface & automated simulation runner
├── bin/                        # Compiled Java bytecode (.class files)
├── .gitignore                  # Git ignore rules for compiled binaries and temporary artifacts
└── README.md                   # Comprehensive documentation and Interview Q&A
```

---

## ⚙️ Features & Capabilities

1. **Savings Account**:
   - Protected with a **Minimum Balance** requirement (e.g., Rs. 500 or Rs. 1,000).
   - Generates interest based on custom or default annual interest rate (e.g., 4.5% p.a.).
   - Disallows withdrawals that drop the account balance below the minimum threshold.

2. **Current (Business) Account**:
   - Designed for commercial entities with **Overdraft Facility** (e.g., Rs. 5,000 or Rs. 10,000 credit cushion).
   - Allows withdrawals beyond zero balance up to `(balance + overdraftLimit)`.
   - Clear notifications when overdraft credit is utilized.

3. **Atomic Fund Transfers**:
   - Safely debits the source account and credits the recipient account with dual transaction logging.

4. **Detailed Transaction Statement Ledger**:
   - Every transaction is recorded with a unique ID (`TXN-APEX-xxxx-xxxx`), ISO timestamp, type, amount, balance after, and description.

5. **Automated Test Simulation**:
   - Built-in automated simulation runner executes all banking scenarios end-to-end with zero manual input required.

---

## 🚀 How to Compile and Run

### Prerequisites
- Java Development Kit (JDK 8 or higher, JDK 17 recommended).
- Git.

### Step 1: Open Terminal in Project Directory
```powershell
cd "c:\Users\Asus\OneDrive\Documents\Desktop\Task 5"
```

### Step 2: Compile Java Source Files
```powershell
javac -d bin src/*.java
```

### Step 3: Run the Application

#### Option A: Interactive Menu
```powershell
java -cp bin Main
```

#### Option B: Automated Headless Simulation Demo
```powershell
java -cp bin Main --demo
```

---

## 📊 Automated Real-World Simulation Output

```text
==========================================================================================
               EXECUTING AUTOMATED REAL-WORLD BANKING SIMULATION                          
==========================================================================================

[SCENARIO 1: Object Instantiation & Constructor Overloading]
Opening Savings Account for 'Rohit Verma' with initial balance Rs.10,000, 4.5% interest, Rs.1,000 min balance...
--------------------------------------------------
Account Number : APEX-1001
Account Type   : Savings Account
Account Holder : Rohit Verma
Current Balance: Rs.10,000.00
Interest Rate  : 4.50%
Minimum Balance: Rs.1,000.00

Opening Current Account for 'NexGen Traders' with Rs.5,000 balance and Rs.10,000 overdraft limit...
--------------------------------------------------
Account Number : APEX-1002
Account Type   : Current Account
Account Holder : NexGen Traders
Current Balance: Rs.5,000.00
Overdraft Limit: Rs.10,000.00
Total Spending : Rs.15,000.00

[SCENARIO 2: Method Overloading (deposit)]
1. Rohit deposits Rs.3,000 (default description variant):
[SUCCESS] Deposit Successful! Rs.3000.00 credited to [APEX-1001]. Updated Balance: Rs.13000.00
2. Rohit deposits Rs.5,000 with custom remark (overloaded variant):
[SUCCESS] Deposit Successful! Rs.5000.00 credited to [APEX-1001]. Updated Balance: Rs.18000.00

[SCENARIO 3: Encapsulation & Validation]
Attempting negative deposit of Rs.-500 (Encapsulated guard):
[ERROR] Deposit Error: Amount must be greater than zero. Provided: Rs.-500.0

[SCENARIO 4: Method Overriding & Runtime Polymorphism (Savings Account Withdraw)]
Rohit has balance: Rs.18000.0 with min balance: Rs.1000.0
a) Rohit attempts valid withdrawal of Rs.6,000:
[SUCCESS] Withdrawal Successful: Rs.6,000.00 deducted from Savings Account [APEX-1001]. Updated Balance: Rs.12,000.00

b) Rohit attempts excessive withdrawal of Rs.11,500 that violates minimum balance (Rs.1,000):
[ERROR] Withdrawal Rejected: Insufficient Funds!
   Current Balance : Rs.12,000.00
   Requested Amount: Rs.11,500.00
   Required Minimum: Rs.1,000.00
   Shortfall       : Rs.500.00

[SCENARIO 5: Method Overriding in Current Account (Overdraft Facility)]
NexGen Traders has balance: Rs.5000.0 and overdraft limit: Rs.10000.0
a) NexGen withdraws Rs.8,000 (exceeds balance of Rs.5,000, utilizing Rs.3,000 overdraft credit):
[SUCCESS] Withdrawal Successful: Rs.8,000.00 deducted from Current Account [APEX-1002]. Updated Balance: Rs.-3,000.00
   [NOTE] Notice: Account is in Overdraft by Rs.3,000.00 (Remaining Overdraft Cushion: Rs.7,000.00)

b) NexGen attempts to withdraw additional Rs.8,000 (exceeds remaining overdraft cushion):
[ERROR] Withdrawal Rejected: Overdraft Limit Exceeded!
   Current Balance       : Rs.-3,000.00
   Overdraft Limit       : Rs.10,000.00
   Total Available Funds : Rs.7,000.00
   Requested Amount      : Rs.8,000.00
   Exceeded By           : Rs.1,000.00

[SCENARIO 6: Inter-Account Funds Transfer & Polymorphic Transaction Recording]
Rohit transfers Rs.4,000 to NexGen Traders:
[SUCCESS] Withdrawal Successful: Rs.4,000.00 deducted from Savings Account [APEX-1001]. Updated Balance: Rs.8,000.00
[SUCCESS] Transfer Completed Successfully!
   Sent Rs.4000.00 from [APEX-1001] to [APEX-1002].

[SCENARIO 7: Interest Calculation & Batch Application]
--- [BATCH] Interest Application Process ---
[INTEREST] Interest Credited: Rs.360.00 added to [APEX-1001] @ 4.50%. New Balance: Rs.8,360.00
--- Completed: Interest credited to 1 savings account(s) ---

[SCENARIO 8: Complete Transaction History & Audit Ledger]

==========================================================================================
                      ACCOUNT STATEMENT - APEX NATIONAL BANK
==========================================================================================
 Account Number: APEX-1001 | Type: Savings Account
 Holder Name   : Rohit Verma
 Current Balance: Rs.8,360.00
------------------------------------------------------------------------------------------
Txn ID       | Date & Time         | Type            | Amount         | Balance After   | Description
------------------------------------------------------------------------------------------
TXN-APEX-1001-0001 | 2026-10-15 22:19:56 | Deposit         | Rs.10000.00   | Rs.10000.00     | Initial Account Opening Deposit
TXN-APEX-1001-0002 | 2026-10-15 22:19:56 | Deposit         | Rs.3000.00    | Rs.13000.00     | Standard Deposit
TXN-APEX-1001-0003 | 2026-10-15 22:19:56 | Deposit         | Rs.5000.00    | Rs.18000.00     | Freelance Client Payment
TXN-APEX-1001-0004 | 2026-10-15 22:19:56 | Withdrawal      | Rs.6000.00    | Rs.12000.00     | Laptop EMI Payment
TXN-APEX-1001-0005 | 2026-10-15 22:19:56 | Withdrawal      | Rs.4000.00    | Rs.8000.00      | Transfer to APEX-1002 - B2B Web Services Invoice
TXN-APEX-1001-0006 | 2026-10-15 22:19:56 | Interest Credit | Rs.360.00     | Rs.8360.00      | Annual Interest Credit @ 4.50% p.a.
==========================================================================================
```

---

## 🎯 Comprehensive Interview Questions & Answers

### 1. What is inheritance in Java?
**Answer**:
Inheritance is an Object-Oriented mechanism where a new class (subclass/child) derives fields and methods from an existing class (superclass/parent) using the `extends` keyword.
- It establishes an **"IS-A"** relationship (e.g., `SavingsAccount IS-A Account`).
- **Benefits**: Eliminates code redundancy, simplifies maintenance, and enables runtime polymorphism.
```java
// Parent class
public abstract class Account {
    protected double balance;
    public void deposit(double amount) { this.balance += amount; }
}

// Child class inherits balance and deposit()
public class SavingsAccount extends Account {
    private double interestRate;
}
```

---

### 2. Why use the `this` keyword?
**Answer**:
`this` is a reference variable in Java that points directly to the **current invoking instance** of a class.
**Primary Use Cases**:
1. **Disambiguate Shadowed Variables**: Differentiate instance variables from constructor/method parameters having identical names:
   ```java
   public Account(String accountNumber, double balance) {
       this.accountNumber = accountNumber; // 'this.accountNumber' is field; 'accountNumber' is parameter
       this.balance = balance;
   }
   ```
2. **Constructor Chaining**: Call another constructor in the same class via `this(...)`:
   ```java
   public Account(String accountNumber, String name) {
       this(accountNumber, name, 0.0); // Delegates to the 3-arg constructor
   }
   ```
3. **Passing the Current Instance**: Pass `this` as an argument to other methods or event listeners.

---

### 3. What is method overriding vs overloading?
**Answer**:

| Feature | Method Overloading | Method Overriding |
| :--- | :--- | :--- |
| **Polymorphism Type** | Compile-time (Static) Polymorphism | Runtime (Dynamic) Polymorphism |
| **Scope** | Within the **same class** | Between **parent and child class** (`extends`) |
| **Method Signature** | Must have **different parameters** (count/type/order) | Must have **identical signature** and compatible return type |
| **Return Type** | Can be different or same | Must be same or covariant |
| **`@Override` Annotation** | Not applicable | Recommended (`@Override`) |
| **Example in Project** | `deposit(double amount)` and `deposit(double amount, String desc)` | `SavingsAccount.withdraw()` overriding `Account.withdraw()` |

---

### 4. What is object instantiation?
**Answer**:
Object instantiation is the process of creating a concrete, physical instance of a class in the JVM **heap memory**.
It consists of three stages:
1. **Declaration**: `Account myAccount;` (Declares reference variable in stack memory).
2. **Instantiation**: Using the `new` keyword: `new SavingsAccount(...)` (Allocates memory block in heap).
3. **Initialization**: The constructor executes to initialize the object's instance fields.
```java
Account acc = new SavingsAccount("APEX-1001", "Sumanth", 5000.0);
```

---

### 5. Explain single vs multiple inheritance.
**Answer**:
- **Single Inheritance**: A class inherits from exactly **one superclass**.
  ```java
  class SavingsAccount extends Account { } // Valid in Java
  ```
- **Multiple Inheritance**: A class inherits directly from **more than one parent class**.
  - **Java DOES NOT support multiple inheritance with classes** to prevent ambiguity known as the **Diamond Problem** (if Class B and Class C override method `foo()` from Class A, and Class D extends both B and C, which `foo()` does D inherit?).
  - **Solution in Java**: Java achieves multiple inheritance through **Interfaces** (`implements InterfaceA, InterfaceB`), because interfaces specify contracts without duplicate state.

---

### 6. What is encapsulation?
**Answer**:
Encapsulation is the principle of bundling the data (fields) and the code that operates on the data (methods) together into a single unit (class), while **restricting direct unauthorized access** from the outside world (data hiding).
- **Implementation**:
  1. Declare instance fields as `private`.
  2. Expose controlled access via `public` getter and setter methods containing validation rules.
- **Example in Bank Account**:
  ```java
  public class Account {
      private double balance; // Private: cannot be altered externally like acc.balance = -99999;

      public double getBalance() {
          return balance; // Read-only access
      }

      public boolean deposit(double amount) {
          if (amount <= 0) return false; // Guard logic
          this.balance += amount;
          return true;
      }
  }
  ```

---

### 7. What is constructor overloading?
**Answer**:
Constructor overloading allows a class to have **more than one constructor**, each having a different parameter list (differing by parameter type, parameter count, or sequence).
- It provides flexibility to instantiate objects when different subsets of initialization data are available.
```java
public class CurrentAccount extends Account {
    // Constructor 1: Full specification
    public CurrentAccount(String accNum, String name, double initialBal, double overdraft) {
        super(accNum, name, initialBal);
        this.overdraftLimit = overdraft;
    }

    // Constructor 2: Default overdraft limit
    public CurrentAccount(String accNum, String name, double initialBal) {
        this(accNum, name, initialBal, 5000.0);
    }

    // Constructor 3: Zero initial balance
    public CurrentAccount(String accNum, String name) {
        this(accNum, name, 0.0, 5000.0);
    }
}
```

---

### 8. Can we override static methods?
**Answer**:
**NO, static methods CANNOT be overridden in Java.**
- **Why?**: Method overriding relies on **dynamic binding at runtime** based on the actual object in heap memory. In contrast, `static` methods belong to the **class itself**, not to object instances. The JVM binds static method calls at **compile-time** based on the reference type.
- If a subclass declares a static method with the exact same signature as a static method in its superclass, this is called **Method Hiding**, NOT method overriding.
```java
class Account {
    public static void displayRules() { System.out.println("Bank Rules"); }
}

class SavingsAccount extends Account {
    // Hides Account.displayRules(), does NOT override it
    public static void displayRules() { System.out.println("Savings Rules"); }
}
```

---

### 9. What is runtime polymorphism?
**Answer**:
Runtime Polymorphism (also known as **Dynamic Method Dispatch**) is the mechanism by which a call to an overridden method is resolved at **runtime** rather than compile-time.
- **How it works**: A parent class reference refers to a child class object. When the overridden method is invoked, Java checks the actual object type in heap memory and calls that class's version.
```java
// Reference type: Account (Parent), Object type: SavingsAccount (Child)
Account acc1 = new SavingsAccount("APEX-1001", "Rohit", 5000.0, 4.0, 1000.0);

// Reference type: Account (Parent), Object type: CurrentAccount (Child)
Account acc2 = new CurrentAccount("APEX-1002", "Company", 5000.0, 10000.0);

// Dynamic Method Dispatch:
acc1.withdraw(4500.0); // Executes SavingsAccount.withdraw() -> REJECTED (Minimum balance violated)
acc2.withdraw(6000.0); // Executes CurrentAccount.withdraw() -> APPROVED (Overdraft utilized)
```

---

### 10. Difference between class and object?
**Answer**:

| Aspect | Class | Object |
| :--- | :--- | :--- |
| **Definition** | A blueprint, template, or prototype defining variables and methods. | A real-world concrete instance of a class. |
| **Physical Existence** | Logical construct; does not take up physical heap memory. | Physical entity; occupies heap memory during execution. |
| **Creation** | Created once during coding using the `class` keyword. | Created multiple times using `new ClassName()`. |
| **Analogy** | Architectural blueprint for a bank account. | An actual bank account created for customer Sumanth with balance Rs. 15,000. |

---

## 📤 GitHub Submission Guide

Follow these steps to push this project to your GitHub account:

### 1. Initialize & Verify Git
```powershell
git status
```

### 2. Stage and Commit All Files
```powershell
git add .
git commit -m "Complete Task 5: Bank Account Simulation using Java OOP"
```

### 3. Create a New Repository on GitHub
1. Log in to [GitHub](https://github.com).
2. Click **New Repository**.
3. Name it: `BankAccountSimulation` (or `Task-5-Bank-Account-Simulation`).
4. Keep it **Public** and do **NOT** initialize with README or .gitignore (already present).

### 4. Link Remote and Push
```powershell
git remote add origin https://github.com/sumanthKateboina/BankAccountSimulation.git
git branch -M main
git push -u origin main
```

### 5. Submit Your Task
Paste your GitHub repository link into the submission portal:
`https://github.com/sumanthKateboina/BankAccountSimulation`
