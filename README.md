# Online Order Management System
🌍 Languages: [English](README.md) | [Português](README.pt-br.md)


![System Design](/Order%20Manger%20System%20Design.png)

## Description:
Develop an order management system for restaurants that enables customers to place products. The system will feature a RESTful API with microservices, with each one responsible for specific functionalities.

### Installation
Clone the repository:

```bash
$ git clone https://github.com/Guilherme-Beckman/order-manager.git
```
## Technologies Used:

- Java
- Spring Boot
- Spring Security
- Postman for API documentation
- MongoDB for data storage
- Docker for containerization of applications
- RabbitMQ for communication between microservices

## System Features:

### Authentication and Authorization:
- Implement authentication using Spring Security with JWT tokens.
- Define different user roles (customer, restaurant, administrator) and control API access based on these roles.

### User Management:
- Allow users to register and log in to the system.
- Customers can view their profile, update information, and view order history.
- Restaurants can manage their menus, product availability, and order status.

### Order Management:
- Customers can browse menus, add items to the cart, and place orders.
- Restaurants receive new orders and can accept, reject, or update the status.
- Implement a queue system with RabbitMQ to process orders asynchronously and scalably.

### API Documentation:
- Generate automatic API documentation using Postman to facilitate integration with the frontend and provide a clear reference for developers.
  
### Database
The project uses MongoDB as the database. MongoDB is a NoSQL database that provides flexibility and scalability for storing and managing data.

## Final Considerations:
This project can be expanded and customized according to your needs and interests. You can add additional features such as online payment support, integration with delivery systems, sales data analysis, among others. The use of modern technologies like microservices, Docker containers, and asynchronous communication with RabbitMQ will make the system robust, scalable, and easy to maintain.
