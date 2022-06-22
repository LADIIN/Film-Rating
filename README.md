# Film-Rating
The administrator creates (manages) a list of movies, series. User rates (once) the movie and can leave a review. His status is automatically raised (lowered) if after a certain number of ratings of other Users, if his rating is close (far) from the overall rating. Administrator manages users: raises, lowers status, puts bans.

Requirements:
- The application should be implemented using Servlet and JSP technologies.
- The architecture of the application must follow the Layered architecture and MVC patterns. Controller can only be of two types: role controller or application controller.
- Information about the subject area should be stored in the database:
- If the data in the database is stored in Cyrillic, it is recommended to use utf-8 encoding
- Only JDBC database access technology
- To work with the database, the application must implement thread-safe connection pooling; using synchronized and volatile is prohibited.
- When designing a database, it is recommended to use no more than 6-8 tables
- You can work with data in the application by means of DAO template.

Basic requirements for the application
- The application's interface must be localized; the choice of languages: EN|BE|RU etc.
- The application must correctly handle the occurrence of exceptional situations, including their logging. Use Log4J2/SLF4J as a logger.
- The classes and other entities of the application must be competently structured into packages and have a name reflecting their functionality.
- When implementing the business logic of the application, design patterns should be used when necessary (for example, GoF patterns: Factory Method, Command, Builder, Strategy, State, Observer, Singleton, Proxy etc.).
- Use a session to store user information between requests.
- Use filters to intercept and correct request and response objects.
- JSP pages must use JSTL tags, it is forbidden to use scriptlets.
- It is allowed to use any front-end development technologies (JavaScript, AJAX) in user interface implementation.
- Implement protection against re-executing requests by pressing F5.
- Implement your own tags.
- Preferably organize viewing of "long lists" in page mode.
- Validation of the input data to produce on the client and the server.
- Documentation for the project must be designed according to the requirements of javadoc.
- Code formatting must comply with the Java Code Convention.
- When deploying the application it is allowed to use Maven technology.
- The application must contain TestNG, JUnit, Mockito or EasyMock tests.
 
Minimum requirements for the application functionality
- Authorization (sign in) and sign out (sign out) to/from the system.
- Registering the user and/or adding an artifact of the system's subject area.
- Viewing information (e.g.: viewing all bets of the totalizator, order statistics, invoices, etc.)
- Deleting information (e.g.: cancelling an order, deleting an entity, etc.)
- Adding and modifying information (e.g.: create and edit a product, create and edit an order, etc.)
- In all projects where there are financial relations it is possible to use payment by credit
