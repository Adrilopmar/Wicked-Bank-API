# Wicked-Bank-API

Wicked-Bank-API is a modern, secure, and easy-to-use RESTful API for a fictional bank that provides various endpoints for managing accounts, transactions, and user profiles. This API has been designed to enable developers to quickly build financial applications that require a reliable and secure backend system.

## Features

- User authentication and authorization using JWT (JSON Web Tokens)
- Create, retrieve, update, and delete accounts
- Deposit and withdraw funds from accounts
- Transfer funds between accounts
- View transaction history for accounts
- Create and manage user profiles

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Tokens)
- Maven
- IntelliJ IDEA
- Testing:
  - JUnit: unit testing framework for Java project

## Installation and Usage

1. Clone the repository to your local machine using `git clone`.
2. Open the project in your preferred IDE.
3. Configure your MySQL database credentials in the `application.properties` file.
4. Run the project and access the API endpoints.

## API Routes

To see the list of available API routes and their documentation, please refer to the `API_DOCUMENTATION.md` file.

## Contributing

If you would like to contribute to this project, please follow these steps:

1. Fork the project.
2. Create a new branch with your changes: `git checkout -b my-new-feature`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push the branch to your fork: `git push origin my-new-feature`.
5. Submit a pull request.

## API Routes

- ## GET method for logged users
- `/accounts/my-account/{accountId}` - retrieves a specific  balance by account ID
    - accountId: Account ID.
- `/users//my-account/{accountId}/balance` - retrieves the balance of an especific account
    - accountId: Account ID.
    
- ## Get method for logged admins    
- `/admin/accounts/all` - retrieves a list of all accounts
- `/admin/accounts/{id}` - retrieves a specific account By ID
    - accountId: Account ID.
- `/admin/users/all` - retrieves a list of all users
- `/admin/users/{id}` - retrieves a specific user By ID

- ### POST method for logged in users
- `/users/{senderId}/transaction/{receiverId}` - for transaction from a user to an account 
    - senderId: user ID who wants to send money.
    - receiverId: Account ID where money is sent.
   - #### Param requiered
    - Primary Owner or secondary owner name of sending account
   - #### Body requiered
    - Sender account ID
    - Amount
- `/users//third-party/{senderId}/transaction/{receiverAccountId}` - for transaction from a third party user to an account 
    - senderId: user ID who wants to send money.
    - receiverAccountId: Account ID where money is sent.
   - #### Param requiered
    - Secret key
   - #### Body requiered
    - Sender account ID
    - Amount
   - #### Header requiered
    - Third Party hash key
    
- ## POST method for not logged admins      
- `/admin/new/admin` - creates a new admin account 
   - #### Body requiered
    - Name
    - Password
    - Username
    
- ## POST method for logged admins  
- `/admin/new/saving` - creates a new saving account
   - #### Body requiered
    - Secret Key
    - Owner ID
- `/admin/new/checking` - creates a new checking account
   - #### Body requiered
    - Secret Key
    - Owner ID  
- `/admin/new/credit-card` - creates a new credit-card account
   - #### Body requiered
    - Secret Key
    - Owner ID    
- `/admin/new/account-holder` - creates a new account-holder account
   - #### Body requiered
    - Name
    - Password
    - Username 
- `/admin/new/third-party` - creates a new third-party account
   - #### Body requiered
    - Name
    - Password
    - Username    
    - Hashed key

## Database Diagram

Here's a diagram of the database schema used in the Wicked-Bank-API project:

![Wicked-Bank-API Database Diagram](https://res.cloudinary.com/djqzi4hgo/image/upload/v1676882389/Wicked-Bank/wicked_bank_diagram_yfxku6.png)

This diagram shows the tables in the database and the relationships between them. Use it as a reference when working with the database.

## License

All right reserved to me =)
