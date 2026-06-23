# Food Store – Sistema de Gestión de Pedidos (Consola – Programación 2)
**UTN – Tecnicatura Universitaria en Programación**  
**Trabajo Práctico Integrador – 2026**

---

## 📌 Descripción del Proyecto

**Food Store** es una aplicación de consola desarrollada en **Java 21**, basada en Programación Orientada a Objetos, que permite gestionar:

- Categorías  
- Productos  
- Usuarios  
- Pedidos y Detalles  

El sistema implementa **CRUD completo**, **baja lógica**, **validaciones**, **manejo de excepciones propias**, y almacenamiento **en memoria mediante colecciones**.

El diseño sigue el UML provisto por la cátedra y respeta las reglas de negocio definidas en las historias de usuario.

---

## 🧱 Arquitectura del Proyecto

El proyecto está organizado en capas:

```
src/
 └── Prog2/
      ├── main/        → Menús y Main (interfaz de consola)
      ├── entities/    → Clases del modelo (UML)
      ├── enums/       → Enumeraciones (Estado, Rol, FormaPago)
      ├── exception/   → Excepciones propias
      ├── service/     → Lógica de negocio y validaciones
      └── utils/       → Utilidades (Validaciones)
```

### ✔ Responsabilidades por capa

- **Main/Menu:** interacción con el usuario, lectura por Scanner, navegación por opciones.  
- **Service:** reglas de negocio, validaciones, CRUD, búsquedas, unicidad, baja lógica.  
- **Entities:** modelo del dominio, herencia desde Base, composición Pedido–DetallePedido.  
- **Exceptions:** errores controlados para entradas inválidas o reglas de negocio.  

---

## 🧩 Tecnologías Utilizadas

- **Java 21**
- **POO:** herencia, composición, encapsulamiento, polimorfismo  
- **Colecciones:** `ArrayList` para almacenamiento en memoria  
- **Excepciones propias:**  
  - `EntidadNoEncontradaException`  
  - `DatoInvalidoException`  
  - `StockInsuficienteException`  
  - `OperacionNoPermitidaException`  
- **Interfaces:** `Calculable` para el cálculo del total del pedido  

---

## ▶️ Cómo Ejecutar el Proyecto

1. Clonar el repositorio:
   ```
   git clone https://github.com/tu-repo/food-store.git
   ```
2. Abrir el proyecto en **NetBeans**, **IntelliJ** o **VS Code** con soporte para Java.
3. Ejecutar la clase:
   ```
   Prog2.main.Main
   ```
4. Navegar por el menú principal y sus submenús.

---

## 📋 Funcionalidades Implementadas

### ✔ Categorías
- Listar  
- Crear  
- Editar  
- Eliminar (baja lógica + validación de productos asociados)

### ✔ Productos
- Listar  
- Listar por categoría  
- Crear (precio ≥ 0, stock ≥ 0, categoría válida)  
- Editar (precio, stock, categoría)  
- Eliminar (baja lógica)

### ✔ Usuarios
- Listar  
- Crear (mail único)  
- Editar (validación de unicidad de mail)  
- Eliminar (baja lógica)

### ✔ Pedidos
- Listar  
- Crear (usuario válido, detalles, stock, subtotal, total)  
- Actualizar estado / forma de pago  
- Eliminar (baja lógica)

---

## 🎥 Video Demostración

🔗 **Enlace al video:**  
*https://drive.google.com/file/d/1G1CzWkIbKz-VPOABPk4OQT07X0PaUPH1/view*

---

## 📄 Documento PDF

🔗 **En este repositorio - Link al video:**
*https://github.com/magaegea1/IntegradorProg2/blob/master/TPI_Programaci%C3%B3n2%20Grupo%2028.pdf*


---

## 👩‍💻 Integrantes del Equipo

- **Magaly Egea Ruiz**   
- **María Luz Domenicale Doré**
- **Inés Ferrario**

---

## ⭐ Estado del Proyecto

✔ Completado  
✔ Cumple con todas las historias de usuario  
✔ Cumple con las reglas de negocio  
✔ Cumple con la arquitectura recomendada  
✔ Manejo robusto de errores  
✔ CRUD completo en todas las entidades  

---
