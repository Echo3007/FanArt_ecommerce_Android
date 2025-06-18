**E-Commerce Application for Digital Art Prints

This project is an e-commerce application developed for purchasing printed copies of digital art, primarily focused on fan art and fan fiction chapters. The platform enables artists to upload and sell their art while retaining ownership rights. All art images were generously donated by Twitter digital artists @gingerlizzard and @spoonfulofrum. The logo on the login page was designed by @gingerlizzard, and vector images were sourced from Android Studio and VeryIcon. Firebase was chosen and implemented as the backend database for the application.

**Features**
User Authentication and Profile Management
Login/Registration: The application opens with a login/registration page, accessible only to registered users, fostering a sense of community.
Registration Page: Users provide personal details, address, and payment information. Input validation is implemented to ensure data integrity.
Password Retrieval: Users can reset their password via a link sent to their email.
User Profile: Users can view and edit their profile information, except for the email and username due to Firebase constraints.
Shopping Experience
Home Page: After logging in, users are greeted with a welcome message and a display of available products categorized into FanArt and FanFiction.
Product Details: Users can view detailed information about each product, including a larger image, artist name, and stock availability.
Basket: Users can add items to their basket, view total costs, and proceed to checkout. However, functionalities to increase or decrease item quantities directly from the basket are not implemented.
Checkout and Order Confirmation
Checkout: Users select their payment method and delivery options. An additional fee is added for expedited delivery.
Order Confirmation: After placing an order, users see a summary of their order details, and the basket is cleared.
Additional Features
Settings Page: Includes a FAQ section and a logout option.
Technical Details
Wireframing and UI Design
Tools Used: Figma for wireframing and UI design.
Design Philosophy: Aimed for a clean and simple design to enhance user experience.
Backend Development
Firebase Integration: Used for user authentication and database management.
Code Challenges: Implementing Firebase proved challenging, particularly in debugging and database structuring. Issues such as incorrect stock updates and bugs in the checkout process were encountered.
ChatGPT Assistance: Utilized extensively for debugging, code structuring, and resolving syntax issues.
Reflection and Future Improvements
Reflection: The development process was more challenging than anticipated. Better initial research and database structuring could have improved the development experience.
Future Improvements: Plans to add an order history section, more product categories, and card validation for payment methods.

**The e-commerce application meets its functional requirements, enabling user registration, profile management, and product purchases. Despite some challenges and existing bugs, the project successfully integrates Firebase for user authentication and database management. Future iterations will focus on improving database structure, fixing bugs, and adding new features to enhance user experience.
