# Online Order Management System

## Description:
Develop an online order management system for restaurants that enables customers to place food orders over the internet. The system will feature a RESTful API for frontend communication and will be built on microservices, with each one responsible for specific functionalities.

## Installation

### Clone the repository:
$ git clone https://github.com/Guilherme-Beckman/order-manager.git

## Technologies Used:

- Java
- Spring Boot
- Spring Security
- Swagger for API documentation
- MongoDB for data storage
- Amazon AWS for hosting and deployment
- JUnit and Mockito for unit testing and mocking objects
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
- Restaurants receive notifications of new orders and can accept, reject, or update the status.
- Implement a queue system with RabbitMQ to process orders asynchronously and scalably.

### Integration with AWS:
- Utilize AWS services such as EC2 for hosting applications, S3 for static file storage (product images), and RDS for relational database.

### Automated Testing:
- Write unit and integration tests to ensure code quality using JUnit and Mockito.
- Utilize API testing tools to test the integrity and functionality of the RESTful API.

### API Documentation:
- Generate automatic API documentation using Swagger to facilitate integration with the frontend and provide a clear reference for developers.

## Final Considerations:
This project can be expanded and customized according to your needs and interests. You can add additional features such as online payment support, integration with delivery systems, sales data analysis, among others. The use of modern technologies like microservices, Docker containers, and asynchronous communication with RabbitMQ will make the system robust, scalable, and easy to maintain.
