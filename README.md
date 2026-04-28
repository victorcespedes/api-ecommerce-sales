# api-ecommerce-sales
Gestion de ventas y clientes propios, consumiendo un API externo como catalogo y notificando eventos del negocio.

## Tecnologías
Java 21, Quarkus 3.34.5, Panache, H2 (local), Kafka y REST Client

## Endpoints

**Customers**
- POST /customers
- GET /customers
- GET /customers/{id}
- PUT /customers/{id}
- DELETE /customers/{id}

**Catalogo of Products**
- GET /products

**Sales**
- POST /sales

## Integraciones

**API externa**: https://fakestoreapi.com/products  
## Integraciones

**API externa**: https://fakestoreapi.com/products  
**Kafka**: http://localhost:8081

**ANEXO**
- Los ambientes de kafka son del proyecto compartido: **kafka-infra.zip**